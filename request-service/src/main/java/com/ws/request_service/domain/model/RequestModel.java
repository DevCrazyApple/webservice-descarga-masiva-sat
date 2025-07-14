package com.ws.request_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestModel {
    private String id;
    private String requestId;
    private String rfc;
    private String requestType;
    private LocalDateTime timestamp;
}
