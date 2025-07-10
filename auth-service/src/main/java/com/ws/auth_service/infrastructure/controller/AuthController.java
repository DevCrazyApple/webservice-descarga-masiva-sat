package com.ws.auth_service.infrastructure.controller;

import com.ws.auth_service.domain.model.AuthModel;
import com.ws.auth_service.domain.port.outbound.TokenGeneratorOut;
import com.ws.auth_service.infrastructure.dto.AuthTokenResponse;
import com.ws.auth_service.infrastructure.dto.ErrorResponse;
import com.ws.auth_service.infrastructure.dto.GetTokenRequest;
import com.ws.auth_service.infrastructure.dto.TokenGenerateRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/auth")
@Validated
public class AuthController {

    private final TokenGeneratorOut tokenGeneratorOut;

    public AuthController(TokenGeneratorOut tokenGeneratorOut) {
        this.tokenGeneratorOut = tokenGeneratorOut;
    }

    @PostMapping
    public ResponseEntity<?> generateToken(@Valid @RequestBody TokenGenerateRequest tokenGenerateRequest) throws Exception {
        AuthModel token_model = this.tokenGeneratorOut.generateToken(
            tokenGenerateRequest.getCertificate(),
            tokenGenerateRequest.getPrivateKey()
        );

        if ( token_model.getId() != null ) {
            return ResponseEntity.ok(
                new AuthTokenResponse(
                    "success",
                    token_model.getToken(),
                    token_model.getRfc(),
                    token_model.getTimestamp()
                )
            );
        } else {
            var error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Token Not Found",
                String.format("No token found with rfc %s", token_model.getRfc()),
                null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @GetMapping
    public ResponseEntity<?> getToken(@Valid @RequestBody GetTokenRequest getTokenRequest) {
        String record = this.tokenGeneratorOut.getToken(getTokenRequest.getRfc());

        return ResponseEntity.ok(
            Map.of(
                "message", "Activo",
                "status", "success",
                "token", record,
                "timeStamp", System.currentTimeMillis()
            )
        );

    }
}
