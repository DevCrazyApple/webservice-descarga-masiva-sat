package com.ws.status_service.application.service;

import com.ws.status_service.application.command.ReqStatusCommand;
import com.ws.status_service.application.mapper.CmdToModel;
import com.ws.status_service.domain.model.PfxModel;
import com.ws.status_service.domain.model.StatusModel;
import com.ws.status_service.domain.port.inbound.VerifyRequestIn;
import com.ws.status_service.domain.port.outbound.VerifyRequestOut;
import org.springframework.stereotype.Service;

@Service
public class StatusService implements VerifyRequestIn {

    private final VerifyRequestOut verifyRequestOut;
    private final CmdToModel mapper;

    public StatusService(VerifyRequestOut verifyRequestOut, CmdToModel mapper) {
        this.verifyRequestOut = verifyRequestOut;
        this.mapper = mapper;
    }

    @Override
    public String getPackages(StatusModel statusModel) {
        return this.verifyRequestOut.getPackages(statusModel);
    }

    @Override
    public String getToken(String rfc) {
        return this.verifyRequestOut.getToken(rfc);
    }

    @Override
    public PfxModel getPfx(String rfc) {
        return this.verifyRequestOut.getPfx(rfc);
    }

    @Override
    public StatusModel toModel(ReqStatusCommand cmd) {
        return this.mapper.toModel(cmd);
    }
}
