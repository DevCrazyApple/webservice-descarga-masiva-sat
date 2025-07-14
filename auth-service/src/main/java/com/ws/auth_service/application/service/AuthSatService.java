package com.ws.auth_service.application.service;

import com.ws.auth_service.domain.port.inbound.AuthAutenticateIn;
import com.ws.auth_service.domain.port.outbound.AuthAutenticateOut;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;


@Slf4j
@Service
public class AuthSatService implements AuthAutenticateIn {

    private final AuthAutenticateOut authAutenticateOut;

    public AuthSatService(AuthAutenticateOut authAutenticateOut) {
        this.authAutenticateOut = authAutenticateOut;
    }

    public String authenticate(X509Certificate cert, PrivateKey key) throws Exception {
        return this.authAutenticateOut.authenticate(cert, key);
    }

    public String extractRfc(X509Certificate certificate) {
        return this.authAutenticateOut.extractRfc(certificate);
    }

}
