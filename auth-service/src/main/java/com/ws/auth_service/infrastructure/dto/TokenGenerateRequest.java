package com.ws.auth_service.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenGenerateRequest {

    @NotBlank(message = "certificate in base64 is required")
    private String certificate;

    @NotBlank(message = "privateKey in base64 is required")
    private String privateKey;
}
