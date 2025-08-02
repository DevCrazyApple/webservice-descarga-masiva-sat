package com.ws.status_service.domain.port.inbound;

import com.ws.status_service.application.command.ReqStatusCommand;
import com.ws.status_service.domain.model.PfxModel;
import com.ws.status_service.domain.model.StatusModel;

public interface VerifyRequestIn {
    String getPackages(StatusModel statusModel);
    String getToken(String rfc);
    PfxModel getPfx(String rfc);
    StatusModel toModel(ReqStatusCommand cmd);
}
