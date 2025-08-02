package com.ws.request_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RequestModel {
    private String rfcSolicitante;
    private String rfcReceptor;
    private String fechaInicial;
    private String fechaFinal;
    private String tipoSolicitud;
    private String tipoComprobante;
    private String estadoComprobante;
    private String token;
    private X509Certificate certificate;
    private RSAPrivateKey privateKey;

    public RequestModel(String rfcSolicitante, String rfcReceptor, String fechaInicial, String fechaFinal, String tipoSolicitud, String tipoComprobante, String estadoComprobante, String token) {
        this.rfcSolicitante = rfcSolicitante;
        this.rfcReceptor = rfcReceptor;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
        this.tipoSolicitud = tipoSolicitud;
        this.tipoComprobante = tipoComprobante;
        this.estadoComprobante = estadoComprobante;
        this.token = token;
    }
}
