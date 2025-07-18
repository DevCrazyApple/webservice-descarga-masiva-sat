package com.ws.auth_service.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthTokenResponse {
    private String status;
    private String token;
    private String rfc;
    private LocalDateTime timestamp;
}
