package com.ws.status_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StatusModel {
    private String rfcSolicitante;
    private String idRequest;
    private String token;
    private RSAPrivateKey privateKey;
    private X509Certificate certificate;

    public StatusModel(String rfcSolicitante, String idRequest) {
        this.rfcSolicitante = rfcSolicitante;
        this.idRequest = idRequest;
    }
}
