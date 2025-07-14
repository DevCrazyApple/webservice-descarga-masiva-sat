package com.ws.auth_service.infrastructure.config;

import com.ws.auth_service.infrastructure.client.SoapClient;
import com.ws.auth_service.infrastructure.client.builder.XmlBuilder;
import com.ws.auth_service.infrastructure.client.parser.ResponseParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SoapClientConfig {

    @Value("${sat.url.autentica}")
    private String endpoint;

    @Value("${sat.url.autentica.action}")
    private String soapAction;

    @Bean
    public SoapClient soapClient() {
        return new SoapClient(endpoint, soapAction);
    }

    @Bean
    public XmlBuilder authXmlBuilder() {
        return new XmlBuilder();
    }

    @Bean
    public ResponseParser authResponseParser() {
        return new ResponseParser();
    }
}
