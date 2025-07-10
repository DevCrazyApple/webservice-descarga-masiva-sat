package com.ws.auth_service.infrastructure.client;

import com.ws.auth_service.infrastructure.client.builder.AuthXmlBuilder;
import com.ws.auth_service.infrastructure.client.parser.AuthResponseParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import static com.ws.auth_service.infrastructure.client.util.CryptoUtils.decodeValue;

@Slf4j
@Service
public class SatAuthenticationService {

    private final SoapClient client;
    private final AuthXmlBuilder builder;
    private final AuthResponseParser parser;

    public SatAuthenticationService(SoapClient client, AuthXmlBuilder builder, AuthResponseParser parser) {
        this.client = client;
        this.builder = builder;
        this.parser = parser;
    }

    public String authenticate(X509Certificate cert, PrivateKey key) throws Exception {
        String request = this.builder.build(cert, key);
        log.info("**** request:\n{}", request);

        String response = this.client.send(request, null);
        log.info("**** response:\n{}", response);

        return decodeValue(this.parser.extractToken(response));
    }

    public String extractRfc(X509Certificate certificate) {
        return this.parser.extractRfc(certificate);
    }

}
