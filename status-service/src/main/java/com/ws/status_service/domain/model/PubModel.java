package com.ws.status_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class PubModel {
    private String rfcSolicitante;
    private String idRequest;
    private String token;
    private String privateKey;
    private String certificate;
}
