package com.ws.status_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VerifyModel {
    private String codeSolicitud;
    private String codeVerificar;
    private String statusVerificar;
    private String packagesIds;
    private String rfcSolicitante;
    private String idRequest;

    public VerifyModel(String codeSolicitud, String codeVerificar, String statusVerificar) {
        this.codeSolicitud = codeSolicitud;
        this.codeVerificar = codeVerificar;
        this.statusVerificar = statusVerificar;
    }
}
