package com.ws.request_service.infrastructure.controller;

import com.ws.request_service.application.command.RequestDownloadCommand;
import com.ws.request_service.application.dto.ErrorResponse;
import com.ws.request_service.domain.model.PfxModel;
import com.ws.request_service.domain.model.RequestModel;
import com.ws.request_service.domain.port.inbound.EmitionRequestIn;
import com.ws.request_service.domain.port.inbound.ReceptionRequestIn;
import com.ws.request_service.domain.port.outbound.EmitionRequestOut;
import com.ws.request_service.domain.port.outbound.ReceptionRequestOut;
import com.ws.request_service.domain.port.outbound.TokenRequestOut;
import com.ws.request_service.infrastructure.entity.RequestEntity;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Request;
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

import static com.ws.request_service.infrastructure.client.util.CryptoUtils.generateCertificateFromDER;
import static com.ws.request_service.infrastructure.client.util.CryptoUtils.generatePrivateKeyFromDER;

@Slf4j
@RestController
@RequestMapping(value = "/api/request")
@Validated
public class RequestController {

    private final EmitionRequestIn emitionRequestIn;
    private final EmitionRequestOut emitionRequestOut;

    private final ReceptionRequestIn receptionRequestIn;
    private final ReceptionRequestOut receptionRequestOut;

    private final TokenRequestOut tokenRequestOut;

    public RequestController(EmitionRequestIn emitionRequestIn, EmitionRequestOut emitionRequestOut, ReceptionRequestIn receptionRequestIn, ReceptionRequestOut receptionRequestOut, TokenRequestOut tokenRequestOut) {
        this.emitionRequestIn = emitionRequestIn;
        this.emitionRequestOut = emitionRequestOut;
        this.receptionRequestIn = receptionRequestIn;
        this.receptionRequestOut = receptionRequestOut;
        this.tokenRequestOut = tokenRequestOut;
    }


    @PostMapping(value = "/emition")
    public ResponseEntity<?> emitionGenerateRequest(@Valid @RequestBody RequestDownloadCommand requestDownloadCommand) throws Exception {

        // con el token obtenido en redis lo agregamos con el request para complementar la info
        String token = this.tokenRequestOut.getToken(requestDownloadCommand.getRfcSolicitante());
        requestDownloadCommand.setToken(token);
        log.info("**** token: {}", token);

        // map content
        RequestModel mapmodel = this.emitionRequestIn.toModel(requestDownloadCommand);

        // obtenemos el pfx haciendo una llamada al microservicio de auth
        PfxModel outPfx = this.tokenRequestOut.getPfx(mapmodel.getRfcSolicitante());

        byte[] keyBytes = Base64.getDecoder().decode(outPfx.getKey());
        RSAPrivateKey privateKey = generatePrivateKeyFromDER(keyBytes);
        mapmodel.setPrivateKey(privateKey);

        byte[] certBytes = Base64.getDecoder().decode(outPfx.getCert());
        X509Certificate certificate = generateCertificateFromDER(certBytes);
        mapmodel.setCertificate(certificate);

        String idrequest = this.emitionRequestOut.requestDownload(mapmodel);

        if ( idrequest.isEmpty() ) {
            var error = new ErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    "Request Not Found",
                    String.format("No request found with rfc %s", mapmodel.getRfcSolicitante()),
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        return ResponseEntity.ok(
            Map.of(
                "message", "Se obtuvo el id de la solicitud con éxito",
                "status", "success",
                "idrequest", idrequest,
                "rfc", mapmodel.getRfcSolicitante(),
                "timeStamp", System.currentTimeMillis()
            )
        );
    }

    @PostMapping(value = "/reception")
    public ResponseEntity<?> receptionGenerateRequest(@Valid @RequestBody RequestDownloadCommand requestDownloadCommand) throws Exception {

        // con el token obtenido en redis lo agregamos con el request para complementar la info
        String token = this.tokenRequestOut.getToken(requestDownloadCommand.getRfcSolicitante());
        requestDownloadCommand.setToken(token);
        log.info("**** token: {}", token);

        // map content
        RequestModel mapmodel = this.receptionRequestIn.toModel(requestDownloadCommand);

        // obtenemos el pfx haciendo una llamada al microservicio de auth
        PfxModel outPfx = this.tokenRequestOut.getPfx(mapmodel.getRfcSolicitante());

        byte[] keyBytes = Base64.getDecoder().decode(outPfx.getKey());
        RSAPrivateKey privateKey = generatePrivateKeyFromDER(keyBytes);
        mapmodel.setPrivateKey(privateKey);

        byte[] certBytes = Base64.getDecoder().decode(outPfx.getCert());
        X509Certificate certificate = generateCertificateFromDER(certBytes);
        mapmodel.setCertificate(certificate);

        String idrequest = this.receptionRequestOut.requestDownload(mapmodel);

        if ( idrequest.isEmpty() ) {
            var error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Request Not Found",
                String.format("No request found with rfc %s", mapmodel.getRfcSolicitante()),
                null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        return ResponseEntity.ok(
            Map.of(
                "message", "Se obtuvo el id de la solicitud con éxito",
                "status", "success",
                "idrequest", idrequest,
                "rfc", mapmodel.getRfcSolicitante(),
                "timeStamp", System.currentTimeMillis()
            )
        );
    }
}
