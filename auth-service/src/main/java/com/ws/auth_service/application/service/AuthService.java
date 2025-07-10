package com.ws.auth_service.application.service;

import com.ws.auth_service.domain.model.AuthModel;
import com.ws.auth_service.domain.port.inbound.TokenGeneratorIn;
import com.ws.auth_service.domain.port.outbound.TokenGeneratorOut;

public class AuthService implements TokenGeneratorIn {

    private final TokenGeneratorOut tokenGeneratorOut;

    public AuthService(TokenGeneratorOut tokenGeneratorOut) {
        this.tokenGeneratorOut = tokenGeneratorOut;
    }

    @Override
    public AuthModel generateToken(String certificate, String privateKey) throws Exception {
        return this.tokenGeneratorOut.generateToken(certificate, privateKey);
    }

    @Override
    public String getToken(String rfc) {
        return this.tokenGeneratorOut.getToken(rfc);
    }
}
