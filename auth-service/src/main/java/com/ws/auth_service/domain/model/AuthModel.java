package com.ws.auth_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthModel {
    private String id;
    private String token;
    private String rfc;
    private LocalDateTime timestamp;
}
