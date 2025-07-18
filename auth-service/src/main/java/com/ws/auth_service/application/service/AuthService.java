package com.ws.auth_service.application.service;

import com.ws.auth_service.domain.model.AuthModel;
import com.ws.auth_service.domain.port.inbound.AuthAutenticateIn;
import com.ws.auth_service.domain.port.inbound.TokenGeneratorIn;
import com.ws.auth_service.domain.port.outbound.AuthAutenticateOut;
import com.ws.auth_service.domain.port.outbound.TokenGeneratorOut;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public class AuthService implements TokenGeneratorIn, AuthAutenticateIn {

    private final TokenGeneratorOut tokenGeneratorOut;
    private final AuthAutenticateOut authAutenticateOut;

    public AuthService(TokenGeneratorOut tokenGeneratorOut, AuthAutenticateOut authAutenticateOut) {
        this.tokenGeneratorOut = tokenGeneratorOut;
        this.authAutenticateOut = authAutenticateOut;
    }

    @Override
    public AuthModel generateToken(String certificate, String privateKey) throws Exception {
        return this.tokenGeneratorOut.generateToken(certificate, privateKey);
    }

    @Override
    public String authenticate(X509Certificate cert, PrivateKey key) throws Exception {
        return this.authAutenticateOut.authenticate(cert, key);
    }
}
