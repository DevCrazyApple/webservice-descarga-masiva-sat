package com.ws.status_service.infrastructure.controller;

import com.ws.status_service.application.command.ReqStatusCommand;
import com.ws.status_service.application.dto.ErrorResponse;
import com.ws.status_service.domain.model.PfxModel;
import com.ws.status_service.domain.model.StatusModel;
import com.ws.status_service.domain.model.VerifyModel;
import com.ws.status_service.domain.port.inbound.VerifyRequestIn;
import com.ws.status_service.domain.port.outbound.VerifyRequestOut;
import jakarta.validation.Valid;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.util.Base64;
import java.util.Map;

import static com.ws.status_service.infrastructure.client.util.CryptoUtils.generateCertificateFromDER;
import static com.ws.status_service.infrastructure.client.util.CryptoUtils.generatePrivateKeyFromDER;

@Slf4j
@RestController
@RequestMapping(value = "/api/status")
@Validated
public class StatusController {

    private final VerifyRequestIn verifyRequestIn;
    private final VerifyRequestOut verifyRequestOut;

    public StatusController(VerifyRequestIn verifyRequestIn, VerifyRequestOut verifyRequestOut) {
        this.verifyRequestIn = verifyRequestIn;
        this.verifyRequestOut = verifyRequestOut;
    }

    @PostMapping
    public ResponseEntity<?> getStatus(@Valid @RequestBody ReqStatusCommand cmd) throws Exception {

        // map content
        StatusModel mapmodel = this.verifyRequestIn.toModel(cmd);

        // obtenemos el pfx haciendo una llamada al microservicio de auth
        String token = this.verifyRequestOut.getToken(mapmodel.getRfcSolicitante());
        mapmodel.setToken(token);

        // obtenemos el pfx haciendo una llamada al microservicio de auth
        PfxModel outPfx = this.verifyRequestOut.getPfx(mapmodel.getRfcSolicitante());

        byte[] keyBytes = Base64.getDecoder().decode(outPfx.getKey());
        RSAPrivateKey privateKey = generatePrivateKeyFromDER(keyBytes);
        mapmodel.setPrivateKey(privateKey);

        byte[] certBytes = Base64.getDecoder().decode(outPfx.getCert());
        X509Certificate certificate = generateCertificateFromDER(certBytes);
        mapmodel.setCertificate(certificate);

        VerifyModel verifyStatus = this.verifyRequestOut.getPackages(mapmodel);

        return ResponseEntity.ok(
            verifyStatus
        );

//        if (idPackages == null) {
//            var error = new ErrorResponse(
//                HttpStatus.NOT_FOUND.value(),
//                "Request Not Found",
//                String.format("La solicitud sigue en proceso: %s", mapmodel.getIdRequest()),
//                null
//            );
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
//        }
    }
}
