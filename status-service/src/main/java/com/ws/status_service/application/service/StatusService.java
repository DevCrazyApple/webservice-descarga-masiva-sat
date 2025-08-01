package com.ws.status_service.application.service;

import com.ws.status_service.domain.model.StatusModel;
import com.ws.status_service.domain.port.inbound.VerifyRequestIn;
import com.ws.status_service.domain.port.outbound.VerifyRequestOut;

public class StatusService implements VerifyRequestIn {

    private final VerifyRequestOut verifyRequestOut;

    public StatusService(VerifyRequestOut verifyRequestOut) {
        this.verifyRequestOut = verifyRequestOut;
    }

    @Override
    public String getPackages(StatusModel statusModel) {
        return this.verifyRequestOut.getPackages(statusModel);
    }
}
