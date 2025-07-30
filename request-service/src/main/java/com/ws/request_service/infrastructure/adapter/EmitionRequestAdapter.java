package com.ws.request_service.infrastructure.adapter;

import com.ws.request_service.domain.model.PfxModel;
import com.ws.request_service.domain.model.RequestModel;
import com.ws.request_service.domain.port.outbound.EmitionRequestOut;
import com.ws.request_service.domain.port.outbound.TokenRequestOut;
import com.ws.request_service.infrastructure.client.SoapClient;
import com.ws.request_service.infrastructure.client.SoapClientProvider;
import com.ws.request_service.infrastructure.client.builder.XmlBuilder;
import com.ws.request_service.infrastructure.client.parser.ResponseParser;
import com.ws.request_service.infrastructure.entity.RequestEntity;
import com.ws.request_service.infrastructure.mapper.MapToEntity;
import com.ws.request_service.infrastructure.redis.TokenCacheAdapter;
import com.ws.request_service.infrastructure.repository.RequestRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.ws.request_service.infrastructure.client.util.CryptoUtils.decodeValue;

@Slf4j
@Component
public class EmitionRequestAdapter implements EmitionRequestOut, TokenRequestOut {

    private final String API_URL_AUTH = "http://auth-service:8080/api/auth/pfx/{rfc}";

    private final RestTemplate restTemplate;
    private final TokenCacheAdapter tokenCacheAdapter;
    private final XmlBuilder builder;
    private final ResponseParser parser;
    private final SoapClient client;
    private final MapToEntity mapper;
    private final RequestRepo requestRepo;

    public EmitionRequestAdapter(RestTemplate restTemplate, TokenCacheAdapter tokenCacheAdapter, XmlBuilder builder, ResponseParser parser, SoapClientProvider provider, MapToEntity mapper, RequestRepo requestRepo) {
        this.restTemplate = restTemplate;
        this.tokenCacheAdapter = tokenCacheAdapter;
        this.builder = builder;
        this.parser = parser;
        this.client = provider.forEmition();
        this.mapper = mapper;
        this.requestRepo = requestRepo;
    }

    @Override
    public String getToken(String rfc) {
        return this.tokenCacheAdapter.getToken(rfc)
            .orElseThrow(() -> new IllegalStateException("Token no encontrado en Redis para RFC: " + rfc));
    }

    @Override
    public PfxModel getPfx(String rfc) {
        return this.restTemplate.getForObject(API_URL_AUTH, PfxModel.class, rfc);
    }

    @Override
    public String requestDownload(RequestModel requestModel) throws Exception {

        String request = this.builder.buildEmition(requestModel);
        log.info("**** request:\n{}", request);

        String response = this.client.send(request, requestModel.getToken());
        log.info("**** response:{}", response);


        // mapeamos la info a una entidad
        RequestEntity entity = this.mapper.toEntityEmition(requestModel);

        // obtenemos el id de la petición
        String idrequest = decodeValue(this.parser.emitionGetResult(response));

        entity.setIdRequest(idrequest);

        // save mondongo
        this.requestRepo.saveInfo(entity);

        return idrequest;
    }
}
