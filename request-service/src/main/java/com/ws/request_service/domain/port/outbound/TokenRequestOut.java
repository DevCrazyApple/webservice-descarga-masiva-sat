package com.ws.request_service.domain.port.outbound;

import com.ws.request_service.domain.model.PfxModel;


public interface TokenRequestOut {
    String getToken(String rfc);
    PfxModel getPfx(String rfc);
}
