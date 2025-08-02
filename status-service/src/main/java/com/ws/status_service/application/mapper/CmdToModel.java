package com.ws.status_service.application.mapper;

import com.ws.status_service.application.command.ReqStatusCommand;
import com.ws.status_service.domain.model.StatusModel;
import org.springframework.stereotype.Component;

@Component
public class CmdToModel {

    public StatusModel toModel(ReqStatusCommand cmd) {
        return new StatusModel(
            cmd.getRfcSolicitante(),
            cmd.getIdRequest()
        );
    }
}
