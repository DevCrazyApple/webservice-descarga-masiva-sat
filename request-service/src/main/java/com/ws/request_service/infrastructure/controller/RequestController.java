package com.ws.request_service.infrastructure.controller;

import com.ws.request_service.application.command.RequestDownloadCommand;
import com.ws.request_service.domain.port.inbound.EmitionDownloadIn;
import com.ws.request_service.domain.port.outbound.EmitionDownloadOut;
import com.ws.request_service.domain.port.outbound.TokenDonwloadOut;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/api/request")
@Validated
public class RequestController {

    private final EmitionDownloadIn emitionDownloadIn;
    private final EmitionDownloadOut emitionDownloadOut;
    private final TokenDonwloadOut tokenDonwloadOut;

    public RequestController(EmitionDownloadIn emitionDownloadIn, EmitionDownloadOut emitionDownloadOut, TokenDonwloadOut tokenDonwloadOut) {
        this.emitionDownloadIn = emitionDownloadIn;
        this.emitionDownloadOut = emitionDownloadOut;
        this.tokenDonwloadOut = tokenDonwloadOut;
    }

    @PostMapping(value = "/emition")
    public ResponseEntity<?> generateRequest(@Valid @RequestBody RequestDownloadCommand requestDownloadCommand) throws Exception {

        // con el token obtenido en redis lo agregamos con el request para complementar la info
        var token = this.tokenDonwloadOut.getToken(requestDownloadCommand.getRfcEmisor());
        requestDownloadCommand.setToken(token);
        log.info("**** token: {}", token);

        // map content
        var mapmodel = this.emitionDownloadIn.toModel(requestDownloadCommand);

        var idrequest = this.emitionDownloadOut.requestDownload(mapmodel);


        return ResponseEntity.ok(
            Map.of(
                "message", "Activo",
                "status", "success",
                "idrequest", idrequest,
                "timeStamp", System.currentTimeMillis()
            )
        );


    }
}
