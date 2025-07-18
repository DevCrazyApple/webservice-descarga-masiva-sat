package com.ws.request_service.application.service;

import com.ws.request_service.infrastructure.client.SoapClient;
import com.ws.request_service.infrastructure.client.builder.XmlBuilder;
import com.ws.request_service.infrastructure.client.parser.ResponseParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import static com.ws.request_service.infrastructure.client.util.CryptoUtils.decodeValue;

@Slf4j
@Service
public class FolioSatService {

    private final SoapClient client;
    private final XmlBuilder builder;
    private final ResponseParser parser;

    public FolioSatService(@Qualifier(value = "soapClientFolio") SoapClient client, XmlBuilder builder, ResponseParser parser) {
        this.client = client;
        this.builder = builder;
        this.parser = parser;
    }

}
