package com.ws.request_service.application.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestDownloadCommand {
    @NotBlank
    @Pattern(regexp = "^[A-ZÑ&]{3,4}\\d{6}[A-Z0-9]{3}$", message = "RFC emisor inválido")
    private String rfcEmisor;
    @Pattern(regexp = "^[A-ZÑ&]{3,4}\\d{6}[A-Z0-9]{3}$", message = "RFC receptor inválido")
    private String rfcReceptor;
    @NotBlank
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$", message = "Fecha de inicio, con formato AAAA-MM-DDThh:mm:ss")
    private String fechaInicial;
    @NotBlank
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$", message = "Fecha de fin del rango, con formato AAAA-MM-DDThh:mm:ss")
    private String fechaFinal;
    @NotBlank
    private String tipoComprobante;
    @Pattern(regexp = "CFDI|Metadata", message = "Estado de comprobante inválido. Solo se permite CFDI o Metadata")
    private String estadoComprobante;
}
