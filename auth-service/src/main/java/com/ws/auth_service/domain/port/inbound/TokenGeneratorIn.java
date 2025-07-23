package com.ws.auth_service.domain.port.inbound;

import com.ws.auth_service.domain.model.AuthModel;
import com.ws.auth_service.domain.model.PfxModel;


public interface TokenGeneratorIn {
    AuthModel generateToken(String certificate, String privateKey) throws Exception;
    PfxModel getPfx(String rfc);
}
