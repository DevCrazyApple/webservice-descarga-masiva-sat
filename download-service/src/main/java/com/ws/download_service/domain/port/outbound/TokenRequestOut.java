package com.ws.download_service.domain.port.outbound;

import com.ws.download_service.domain.model.PfxModel;


public interface TokenRequestOut {
    String getToken(String rfc);
    PfxModel getPfx(String rfc);
}
