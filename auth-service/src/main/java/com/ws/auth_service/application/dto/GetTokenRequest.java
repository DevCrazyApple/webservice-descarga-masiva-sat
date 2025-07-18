package com.ws.auth_service.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetTokenRequest {

    @NotBlank(message = "rfc is require")
    private String rfc;
}
