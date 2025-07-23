package com.ws.request_service.infrastructure.controller;

import com.ws.request_service.application.command.RequestDownloadCommand;
import com.ws.request_service.domain.model.PfxModel;
import com.ws.request_service.domain.model.RequestModel;
import com.ws.request_service.domain.port.inbound.EmitionRequestIn;
import com.ws.request_service.domain.port.outbound.EmitionRequestOut;
import com.ws.request_service.domain.port.outbound.TokenRequestOut;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
    private final TokenRequestOut tokenRequestOut;

    public RequestController(EmitionRequestIn emitionRequestIn, EmitionRequestOut emitionRequestOut, TokenRequestOut tokenRequestOut) {
        this.emitionRequestIn = emitionRequestIn;
        this.emitionRequestOut = emitionRequestOut;
        this.tokenRequestOut = tokenRequestOut;
    }

    @PostMapping(value = "/emition")
    public ResponseEntity<?> generateRequest(@Valid @RequestBody RequestDownloadCommand requestDownloadCommand) throws Exception {

        // con el token obtenido en redis lo agregamos con el request para complementar la info
        String token = this.tokenRequestOut.getToken(requestDownloadCommand.getRfcEmisor());
        requestDownloadCommand.setToken(token);
        log.info("**** token: {}", token);

        // map content
        RequestModel mapmodel = this.emitionRequestIn.toModel(requestDownloadCommand);

        // obtenemos el pfx haciendo una llamada al microservicio de auth
        PfxModel outPfx = this.tokenRequestOut.getPfx(mapmodel.getRfcEmisor());

        byte[] keyBytes = Base64.getDecoder().decode(outPfx.getKey());
        RSAPrivateKey privateKey = generatePrivateKeyFromDER(keyBytes);
        mapmodel.setPrivateKey(privateKey);

        byte[] certBytes = Base64.getDecoder().decode(outPfx.getCert());
        X509Certificate certificate = generateCertificateFromDER(certBytes);
        mapmodel.setCertificate(certificate);


        var idrequest = this.emitionRequestOut.requestDownload(mapmodel);


        return ResponseEntity.ok(
            Map.of(
                "message", "Activo",
                "status", "success",
                "idrequest", "113123131313",
                "timeStamp", System.currentTimeMillis()
            )
        );


    }
}
