package com.ws.request_service.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetTokenRequest {

    @NotBlank(message = "rfc is require")
    @Pattern(regexp = "^[A-ZÑ&]{3,4}\\d{6}[A-Z0-9]{3}$", message = "RFC inválido")
    private String rfc;
}
