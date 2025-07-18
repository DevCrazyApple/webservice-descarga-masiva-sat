package com.ws.request_service.application.service;

import com.ws.request_service.application.command.RequestDownloadCommand;
import com.ws.request_service.application.mapper.CommandToModel;
import com.ws.request_service.domain.model.RequestModel;
import com.ws.request_service.domain.port.inbound.EmitionDownloadIn;
import com.ws.request_service.domain.port.inbound.TokenDownloadIn;
import com.ws.request_service.domain.port.outbound.EmitionDownloadOut;
import com.ws.request_service.domain.port.outbound.TokenDonwloadOut;
import com.ws.request_service.infrastructure.client.SoapClient;
import com.ws.request_service.infrastructure.client.builder.XmlBuilder;
import com.ws.request_service.infrastructure.client.parser.ResponseParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmitionSatService implements TokenDownloadIn, EmitionDownloadIn {

    private final SoapClient client;
    private final XmlBuilder builder;
    private final ResponseParser parser;
    private final TokenDonwloadOut tokenDonwloadOut;
    private final EmitionDownloadOut emitionDownloadOut;
    private final CommandToModel mapper;

    public EmitionSatService(@Qualifier(value = "soapClientEmition") SoapClient client, XmlBuilder builder, ResponseParser parser, TokenDonwloadOut tokenDonwloadOut, EmitionDownloadOut emitionDownloadOut, CommandToModel mapper) {
        this.client = client;
        this.builder = builder;
        this.parser = parser;
        this.tokenDonwloadOut = tokenDonwloadOut;
        this.emitionDownloadOut = emitionDownloadOut;
        this.mapper = mapper;
    }


    @Override
    public RequestModel requestDownload(RequestModel requestModel) throws Exception {
        return this.emitionDownloadOut.requestDownload(requestModel);
    }

    @Override
    public RequestModel toModel(RequestDownloadCommand requestDownloadCommand) {
        return this.mapper.toModel(requestDownloadCommand);
    }

    @Override
    public String getToken(String rfc) {
        return this.tokenDonwloadOut.getToken(rfc);
    }
}
