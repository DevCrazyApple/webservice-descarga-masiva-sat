package com.ws.request_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RequestModel {
    private String rfcEmisor;
    private String rfcReceptor;
    private String fechaInicial;
    private String fechaFinal;
    private String tipoSolicitud;
    private String tipoComprobante;
    private String estadoComprobante;
    private String token;
}
