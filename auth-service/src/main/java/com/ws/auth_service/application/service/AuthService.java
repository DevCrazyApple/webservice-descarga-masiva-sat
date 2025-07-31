package com.ws.auth_service.application.service;

import com.ws.auth_service.domain.model.AuthModel;
import com.ws.auth_service.domain.model.PfxModel;
import com.ws.auth_service.domain.port.inbound.AutenticateIn;
import com.ws.auth_service.domain.port.inbound.TokenGeneratorIn;
import com.ws.auth_service.domain.port.outbound.AutenticateOut;
import com.ws.auth_service.domain.port.outbound.TokenGeneratorOut;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public class AuthService implements TokenGeneratorIn, AutenticateIn {

    private final TokenGeneratorOut tokenGeneratorOut;
    private final AutenticateOut autenticateOut;

    public AuthService(TokenGeneratorOut tokenGeneratorOut, AutenticateOut autenticateOut) {
        this.tokenGeneratorOut = tokenGeneratorOut;
        this.autenticateOut = autenticateOut;
    }

    @Override
    public AuthModel generateToken(String certificate, String privateKey) throws Exception {
        return this.tokenGeneratorOut.generateToken(certificate, privateKey);
    }

    @Override
    public PfxModel getPfx(String rfc) {
        return this.tokenGeneratorOut.getPfx(rfc);
    }

    @Override
    public String authenticate(X509Certificate cert, PrivateKey key) throws Exception {
        return this.autenticateOut.authenticate(cert, key);
    }
}
