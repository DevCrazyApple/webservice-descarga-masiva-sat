package com.ws.request_service.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "request_service")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestEntity {
    @Id
    private String id;
    private String rfc;
    private String idrequest;
}
