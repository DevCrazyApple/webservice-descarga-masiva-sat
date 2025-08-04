package com.ws.status_service.domain.port.inbound;

import com.ws.status_service.application.command.ReqStatusCommand;
import com.ws.status_service.domain.model.PfxModel;
import com.ws.status_service.domain.model.StatusModel;
import com.ws.status_service.domain.model.VerifyModel;

public interface VerifyRequestIn {
    VerifyModel getPackages(StatusModel statusModel) throws Exception;
    String getToken(String rfc);
    PfxModel getPfx(String rfc);
    StatusModel toModel(ReqStatusCommand cmd);
}
