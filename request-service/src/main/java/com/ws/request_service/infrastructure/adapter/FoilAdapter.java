package com.ws.request_service.infrastructure.adapter;

import com.ws.request_service.domain.model.FoilModel;
import com.ws.request_service.domain.port.outbound.FoilOut;
import com.ws.request_service.infrastructure.client.SoapClient;
import com.ws.request_service.infrastructure.client.SoapClientProvider;
import com.ws.request_service.infrastructure.client.builder.XmlBuilder;
import com.ws.request_service.infrastructure.client.parser.ResponseParser;
import com.ws.request_service.infrastructure.entity.FoilEntity;
import com.ws.request_service.infrastructure.mapper.MapToEntity;
import com.ws.request_service.infrastructure.repository.FoilRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.ws.request_service.infrastructure.client.util.CryptoUtils.decodeValue;

@Slf4j
@Component
public class FoilAdapter implements FoilOut {

    private final XmlBuilder builder;
    private final ResponseParser parser;
    private final SoapClient client;
    private final MapToEntity mapper;
    private final FoilRepo foilRepo;

    public FoilAdapter(XmlBuilder builder, ResponseParser parser, SoapClientProvider provider, MapToEntity mapper, FoilRepo foilRepo) {
        this.builder = builder;
        this.parser = parser;
        this.client = provider.forFolio();
        this.mapper = mapper;
        this.foilRepo = foilRepo;
    }

    @Override
    public String requestDownload(FoilModel foilModel) throws Exception {
        String request = this.builder.buildFoil(foilModel);
        log.info("**** request:\n{}", request);

        String response = this.client.send(request, foilModel.getToken());
        log.info("**** response:\n{}", response);

        // obtenemos el id de la petición
        String idrequest = decodeValue(this.parser.foilGetResult(response));
        foilModel.setIdRequest(idrequest);

        // map to entity
        FoilEntity entity = this.mapper.toEntityFoil(foilModel);

        // save mondongo
        this.foilRepo.saveInfo(entity);

        return idrequest;
    }
}
