package com.ws.auth_service.infrastructure.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "auth_service")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PfxEntity {
    @Id
    private String id;
    private String rfc;
    private String cert;
    private String key;
}
