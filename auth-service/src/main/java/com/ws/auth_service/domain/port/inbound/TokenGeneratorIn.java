package com.ws.auth_service.domain.port.inbound;

import com.ws.auth_service.domain.model.AuthModel;


public interface TokenGeneratorIn {
    AuthModel generateToken(String certificate, String privateKey) throws Exception;

    String getToken(String rfc);
}
