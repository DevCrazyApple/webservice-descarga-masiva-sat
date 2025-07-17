package com.ws.request_service.application.command;

import com.ws.model.VoucherType;
import com.ws.validation.ValueOfEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestDownloadCommand {
    @NotBlank(message = "Rfc Emisor es requerido")
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

    @NotBlank(message = "Tipo Solicitud es requerido")
    @Pattern(regexp = "CFDI|Metadata", message = "Estado de comprobante inválido. Solo se permite CFDI o Metadata")
    private String tipoSolicitud;

    @NotBlank(message = "Tipo Comprobante es requerido")
    @ValueOfEnum(enumClass = VoucherType.class, message = "Tipo Comprobante incorrecto")
    private String tipoComprobante;

    @NotBlank(message = "Estado de Comprobante es requerido")
    @Pattern(regexp = "Cancelado|Vigente", message = "Solo se permite Cancelado o Vigente")
    private String estadoComprobante;
}
