package com.ws.request_service.infrastructure.mapper;

import com.ws.request_service.domain.model.RequestModel;
import com.ws.request_service.infrastructure.entity.RequestEntity;
import org.springframework.stereotype.Component;

@Component
public class MapToEntity {

    public RequestEntity toEntityEmition(RequestModel model) {
        return new RequestEntity(
            null,
            model.getRfcEmisor(),
            model.getRfcReceptor(),
            model.getFechaInicial(),
            model.getFechaFinal(),
            model.getTipoSolicitud(),
            model.getTipoComprobante(),
            model.getEstadoComprobante(),
            null,
            "EmitionSatService"
        );
    }

    public RequestEntity toEntityReception(RequestModel model) {
        return new RequestEntity(
            null,
            model.getRfcEmisor(),
            model.getRfcReceptor(),
            model.getFechaInicial(),
            model.getFechaFinal(),
            model.getTipoSolicitud(),
            model.getTipoComprobante(),
            model.getEstadoComprobante(),
            null,
            "ReceptionSatService"
        );
    }
}
