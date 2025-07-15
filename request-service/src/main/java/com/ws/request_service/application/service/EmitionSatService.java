package com.ws.request_service.application.service;

import com.ws.request_service.application.command.RequestDownloadCommand;
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

    public EmitionSatService(@Qualifier(value = "soapClientEmition") SoapClient client, XmlBuilder builder, ResponseParser parser, TokenDonwloadOut tokenDonwloadOut, EmitionDownloadOut emitionDownloadOut) {
        this.client = client;
        this.builder = builder;
        this.parser = parser;
        this.tokenDonwloadOut = tokenDonwloadOut;
        this.emitionDownloadOut = emitionDownloadOut;
    }


    @Override
    public RequestModel requestDownload(RequestDownloadCommand requestDownloadCommand) {
        return this.emitionDownloadOut.requestDownload(requestDownloadCommand);
    }

    @Override
    public String getToken(String rfc) {
        return this.tokenDonwloadOut.getToken(rfc);
    }
}
