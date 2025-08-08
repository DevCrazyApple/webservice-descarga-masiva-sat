package com.ws.request_service.application.service;

import com.ws.request_service.domain.model.FoilModel;
import com.ws.request_service.domain.model.PfxModel;
import com.ws.request_service.domain.port.inbound.FoilIn;
import com.ws.request_service.domain.port.inbound.TokenRequestIn;
import com.ws.request_service.domain.port.outbound.FoilOut;
import com.ws.request_service.domain.port.outbound.TokenRequestOut;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class FolioSatService implements FoilIn, TokenRequestIn {

    private final FoilOut foilOut;
    private final TokenRequestOut tokenRequestOut;

    public FolioSatService(FoilOut foilOut, TokenRequestOut tokenRequestOut) {
        this.foilOut = foilOut;
        this.tokenRequestOut = tokenRequestOut;
    }

    @Override
    public String requestDownload(FoilModel foilModel) throws Exception {
        return this.foilOut.requestDownload(foilModel);
    }

    @Override
    public String getToken(String rfc) {
        return this.tokenRequestOut.getToken(rfc);
    }

    @Override
    public PfxModel getPfx(String rfc) {
        return this.tokenRequestOut.getPfx(rfc);
    }
}
