package com.ws.download_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PackageModel {
    private String packagesIds;
    private String rfcSolicitante;
    private String idRequest;

    public PackageModel(String rfcSolicitante, String idRequest) {
        this.rfcSolicitante = rfcSolicitante;
        this.idRequest = idRequest;
    }

}
