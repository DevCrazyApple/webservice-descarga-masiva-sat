package com.ws.status_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VerifyModel {
    private Integer codeSolicitud;
    private Integer codeVerificar;
    private Integer statusVerificar;
    private String packagesIds;

    public VerifyModel(Integer codeSolicitud, Integer codeVerificar, Integer statusVerificar) {
        this.codeSolicitud = codeSolicitud;
        this.codeVerificar = codeVerificar;
        this.statusVerificar = statusVerificar;
    }

}
