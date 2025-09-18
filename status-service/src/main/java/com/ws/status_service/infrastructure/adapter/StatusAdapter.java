package com.ws.status_service.infrastructure.adapter;

import com.ws.status_service.domain.exception.TokenNotFoundException;
import com.ws.status_service.domain.model.*;
import com.ws.status_service.domain.port.outbound.VerifyRequestOut;
import com.ws.status_service.infrastructure.client.SoapClient;
import com.ws.status_service.infrastructure.client.builder.XmlBuilder;
import com.ws.status_service.infrastructure.client.parser.ResponseParser;
import com.ws.status_service.infrastructure.client.parser.enums.VerificarStatus;
import com.ws.status_service.infrastructure.entity.StatusEntity;
import com.ws.status_service.infrastructure.mapper.MapToEntity;
import com.ws.status_service.infrastructure.redis.StatusStreamPublisher;
import com.ws.status_service.infrastructure.redis.TokenCacheAdapter;
import com.ws.status_service.infrastructure.repository.StatusRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


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
    private final MapToEntity mapper;
    private final StatusRepo statusRepo;

    public StatusAdapter(TokenCacheAdapter tokenCacheAdapter, RestTemplate restTemplate, XmlBuilder builder, ResponseParser parser, SoapClient client, StatusStreamPublisher statusStreamPublisher, MapToEntity mapper, StatusRepo statusRepo) {
        this.tokenCacheAdapter = tokenCacheAdapter;
        this.restTemplate = restTemplate;
        this.builder = builder;
        this.parser = parser;
        this.client = client;
        this.statusStreamPublisher = statusStreamPublisher;
        this.mapper = mapper;
        this.statusRepo = statusRepo;
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

        // mapeamos el contenido para el repo y guardamos
        StatusEntity entity = this.mapper.toEntity(verifyStatus);

        // mandar al pub redis para que inicie la descarga en parallelo
        if (verifyStatus.getStatusVerificar().equalsIgnoreCase(VerificarStatus.Finished.getMessage())) {

            PubModel sendRedis = new PubModel(
                verifyStatus.getRfcSolicitante(),
                verifyStatus.getIdRequest()
            );
            this.statusStreamPublisher.publish(sendRedis);

            // cambiamos a true porque se manda en parallelo la info
            entity.setProcessed(true);
        }

        this.statusRepo.saveOrUpdate(entity);
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

    /**
     * find info
     * @param idrequest
     * @return
     */
    @Override
    public PackageModel getPackage(String idrequest) {

        StatusEntity exists = this.statusRepo.findByIdRequest(idrequest);

        return new PackageModel(
            exists.getPackagesIds(),
            exists.getRfcSolicitante(),
            exists.getIdRequest()
        );
    }
}
