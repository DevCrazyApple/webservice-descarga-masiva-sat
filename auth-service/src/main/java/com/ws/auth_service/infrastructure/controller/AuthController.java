package com.ws.auth_service.infrastructure.controller;

import com.ws.auth_service.domain.model.AuthModel;
import com.ws.auth_service.domain.model.PfxModel;
import com.ws.auth_service.domain.port.outbound.TokenGeneratorOut;
import com.ws.auth_service.application.dto.AuthTokenResponse;
import com.ws.auth_service.application.dto.ErrorResponse;
import com.ws.auth_service.application.command.TokenGenerateRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
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

    @GetMapping(value = "/pfx/{rfc}")
    public ResponseEntity<?> getPfx(@PathVariable @NotBlank String rfc) {

        PfxModel exists = this.tokenGeneratorOut.getPfx(rfc);
        return ResponseEntity.ok(
            Map.of(
                "cert", exists.getCert(),
                "key", exists.getKey()
            )
        );
    }
}
