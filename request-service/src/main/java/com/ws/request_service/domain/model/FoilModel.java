package com.ws.request_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FoilModel {
    private String rfcSolicitante;
    private String folio;
    private String token;
    private X509Certificate certificate;
    private RSAPrivateKey privateKey;
    private String idRequest;

    public FoilModel(String rfcSolicitante, String folio) {
        this.rfcSolicitante = rfcSolicitante;
        this.folio = folio;
    }
}
