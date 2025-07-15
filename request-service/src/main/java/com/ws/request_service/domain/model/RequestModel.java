package com.ws.request_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RequestModel {
    private String id;
    private String requestId;
    private String rfcEmisor;
    private String rfcReceptor;
    private String requestType;
    private String initDate;
    private String finishDate;
}
