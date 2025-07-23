package com.ws.request_service.domain.port.inbound;

import com.ws.request_service.domain.model.PfxModel;


public interface TokenRequestIn {
    String getToken(String rfc);
    PfxModel getPfx(String rfc);
}
