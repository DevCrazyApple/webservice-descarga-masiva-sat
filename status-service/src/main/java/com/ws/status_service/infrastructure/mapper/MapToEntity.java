package com.ws.status_service.infrastructure.mapper;

import com.ws.status_service.domain.model.VerifyModel;
import com.ws.status_service.infrastructure.entity.StatusEntity;
import org.springframework.stereotype.Component;

@Component
public class MapToEntity {

    public StatusEntity toEntity(VerifyModel model) {
        return new StatusEntity(
                null,
                model.getCodeSolicitud(),
                model.getCodeVerificar(),
                model.getStatusVerificar(),
                model.getPackagesIds(),
                model.getRfcSolicitante(),
                model.getIdRequest(),
                false
        );
    }

}
