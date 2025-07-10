package com.ws.auth_service.domain.port.outbound;

import com.ws.auth_service.domain.model.AuthModel;

public interface TokenGeneratorOut {
    AuthModel generateToken(String certificate, String privateKey) throws Exception;

    String getToken(String rfc);

}
