package com.ws.download_service.application.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReqStatusCommand {
    @NotBlank(message = "Rfc Solicitante es requerido")
    @Pattern(regexp = "^[A-ZÑ&]{3,4}\\d{6}[A-Z0-9]{3}$", message = "RFC Solicitante inválido")
    private String rfcSolicitante;

    @NotBlank(message = "id request es requerido")
    private String idRequest;
}
