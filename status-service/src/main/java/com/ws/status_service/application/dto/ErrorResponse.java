package com.ws.status_service.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ErrorResponse {
    private int status;
    private String error;
    private String message;
    private LocalDateTime timestamp;
    private Map<String, String> details;

    public ErrorResponse(int status, String error, String message, Map<String, String> details) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.details = details;
    }
}
