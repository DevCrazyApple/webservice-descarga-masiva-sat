package com.ws.request_service.application.mapper;

import com.ws.request_service.application.command.RequestDownloadCommand;
import com.ws.request_service.domain.model.RequestModel;
import org.springframework.stereotype.Component;

@Component
public class CommandToModel {
    public RequestModel toModel(RequestDownloadCommand command) {
        return new RequestModel(
            command.getRfcEmisor(),
            command.getRfcReceptor() != null ? command.getRfcReceptor() : "",
            command.getFechaInicial(),
            command.getFechaFinal(),
            command.getTipoSolicitud(),
            command.getTipoComprobante(),
            command.getEstadoComprobante(),
            command.getToken()
        );
    }
}
