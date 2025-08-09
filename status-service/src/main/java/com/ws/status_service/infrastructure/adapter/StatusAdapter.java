package com.ws.status_service.infrastructure.adapter;

import com.ws.status_service.domain.exception.TokenNotFoundException;
import com.ws.status_service.domain.model.PfxModel;
import com.ws.status_service.domain.model.PubModel;
import com.ws.status_service.domain.model.StatusModel;
import com.ws.status_service.domain.model.VerifyModel;
import com.ws.status_service.domain.port.outbound.VerifyRequestOut;
import com.ws.status_service.infrastructure.client.SoapClient;
import com.ws.status_service.infrastructure.client.builder.XmlBuilder;
import com.ws.status_service.infrastructure.client.parser.ResponseParser;
import com.ws.status_service.infrastructure.client.parser.enums.VerificarStatus;
import com.ws.status_service.infrastructure.redis.MessagePublisher;
import com.ws.status_service.infrastructure.redis.StatusStreamPublisher;
import com.ws.status_service.infrastructure.redis.TokenCacheAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;


@Slf4j
@Component
public class StatusAdapter implements VerifyRequestOut {

    private final String API_URL_AUTH = "http://auth-service:8080/api/auth/pfx/{rfc}";

    private final TokenCacheAdapter tokenCacheAdapter;
    private final RestTemplate restTemplate;
    private final XmlBuilder builder;
    private final ResponseParser parser;
    private final SoapClient client;
    private final StatusStreamPublisher statusStreamPublisher;

    public StatusAdapter(TokenCacheAdapter tokenCacheAdapter, RestTemplate restTemplate, XmlBuilder builder, ResponseParser parser, SoapClient client, StatusStreamPublisher statusStreamPublisher) {
        this.tokenCacheAdapter = tokenCacheAdapter;
        this.restTemplate = restTemplate;
        this.builder = builder;
        this.parser = parser;
        this.client = client;
        this.statusStreamPublisher = statusStreamPublisher;
    }

    @Override
    public VerifyModel getPackages(StatusModel statusModel) throws Exception {

        String request = this.builder.build(statusModel);
        log.info("**** request:\n{}", request);

        String response = this.client.send(request, statusModel.getToken());
        log.info("**** response:\n{}", response);

        VerifyModel verifyStatus = this.parser.getResult(response);
        verifyStatus.setRfcSolicitante(statusModel.getRfcSolicitante());
        verifyStatus.setIdRequest(statusModel.getIdRequest());

//        // mandar al pub redis para que inicie la descarga en parallelo
//        if (verifyStatus.getStatusVerificar().equalsIgnoreCase(VerificarStatus.Finished.getMessage())) {
//
//            PubModel sendRedis = new PubModel(
//                verifyStatus.getRfcSolicitante(),
//                verifyStatus.getIdRequest(),
//                statusModel.getToken(),
//                Base64.getEncoder().encodeToString(statusModel.getPrivateKey().getEncoded()),
//                Base64.getEncoder().encodeToString(statusModel.getCertificate().getEncoded())
//            );
//            this.statusStreamPublisher.publish(sendRedis);
//        }

        PubModel sendRedis = new PubModel(
            verifyStatus.getRfcSolicitante(),
            verifyStatus.getIdRequest(),
            statusModel.getToken(),
            Base64.getEncoder().encodeToString(statusModel.getPrivateKey().getEncoded()),
            Base64.getEncoder().encodeToString(statusModel.getCertificate().getEncoded())
        );
        this.statusStreamPublisher.publish(sendRedis);

        return verifyStatus;
    }

    @Override
    public String getToken(String rfc) {
        return this.tokenCacheAdapter.getToken(rfc)
            .orElseThrow(() -> new TokenNotFoundException(rfc));
    }

    @Override
    public PfxModel getPfx(String rfc) {
        return this.restTemplate.getForObject(API_URL_AUTH, PfxModel.class, rfc);
    }
}
