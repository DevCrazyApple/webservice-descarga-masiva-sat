package com.ws.auth_service.infrastructure.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Document(collection = "auth_service")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthEntity implements Serializable {

    @Id
    private String id;

    @Indexed(unique = true)
    private String rfc;

    private String token;
    private LocalDateTime timestamp;

    public AuthEntity(String rfc, String token) {
        this.rfc = rfc;
        this.token = token;
        this.timestamp = LocalDateTime.now();
    }
}
