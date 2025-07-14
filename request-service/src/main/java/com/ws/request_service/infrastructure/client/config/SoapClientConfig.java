package com.ws.request_service.infrastructure.client.config;

import com.ws.request_service.infrastructure.client.SoapClient;
import com.ws.request_service.infrastructure.client.builder.XmlBuilder;
import com.ws.request_service.infrastructure.client.parser.ResponseParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SoapClientConfig {

    @Value("${sat.url.request}")
    private String endpoint;

    @Value("${sat.url.request.actionEmition}")
    private String soapActionEmition;

    @Value("${sat.url.request.actionReception}")
    private String soapActionReception;

    @Value("${sat.url.request.actionFolio}")
    private String soapActionFolio;

    @Bean(value = "soapClientEmition")
    public SoapClient soapClientEmition() {
        return new SoapClient(endpoint, soapActionEmition);
    }

    @Bean(value = "soapClientReception")
    public SoapClient soapClientReception() {
        return new SoapClient(endpoint, soapActionReception);
    }

    @Bean(value = "soapClientFolio")
    public SoapClient soapClientFolio() {
        return new SoapClient(endpoint, soapActionFolio);
    }

    @Bean
    public XmlBuilder xmlBuilder() {
        return new XmlBuilder();
    }

    @Bean
    public ResponseParser responseParser() {
        return new ResponseParser();
    }
}
