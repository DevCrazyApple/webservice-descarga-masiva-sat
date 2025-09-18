package com.ws.status_service.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "status_service")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StatusEntity {
    @Id
    private String id;
    private String codeSolicitud;
    private String codeVerificar;
    private String statusVerificar;
    private String packagesIds;
    private String rfcSolicitante;
    private String idRequest;
    private Boolean processed;
}
