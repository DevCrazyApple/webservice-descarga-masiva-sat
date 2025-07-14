package com.ws.request_service.application.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestDownloadCommand {
    private String rfcEmisor;
    private String rfcReceptor;
    private String fechaInicial;
    private String fechaFinal;
    private String tipoComprobante;
    private String estadoComprobante;
}
