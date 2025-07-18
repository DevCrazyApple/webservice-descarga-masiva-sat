package com.ws.request_service.infrastructure.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SoapClientProvider {

    private final SoapClient emitionClient;
    private final SoapClient receptionClient;
    private final SoapClient folioClient;

    public SoapClientProvider(
        @Qualifier("soapClientEmition") SoapClient emitionClient,
        @Qualifier("soapClientReception") SoapClient receptionClient,
        @Qualifier("soapClientFolio") SoapClient folioClient
    ) {
        this.emitionClient = emitionClient;
        this.receptionClient = receptionClient;
        this.folioClient = folioClient;
    }

    public SoapClient forEmition() {
        return emitionClient;
    }

    public SoapClient forReception() {
        return receptionClient;
    }

    public SoapClient forFolio() {
        return folioClient;
    }
}
