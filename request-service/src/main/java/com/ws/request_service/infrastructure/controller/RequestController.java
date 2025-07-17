package com.ws.request_service.infrastructure.controller;

import com.ws.request_service.application.command.RequestDownloadCommand;
import com.ws.request_service.application.dto.ErrorResponse;
import com.ws.request_service.domain.port.outbound.EmitionDownloadOut;
import com.ws.request_service.domain.port.outbound.TokenDonwloadOut;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/request")
@Validated
public class RequestController {

    private final EmitionDownloadOut emitionDownloadOut;
    private final TokenDonwloadOut tokenDonwloadOut;

    public RequestController(EmitionDownloadOut emitionDownloadOut, TokenDonwloadOut tokenDonwloadOut) {
        this.emitionDownloadOut = emitionDownloadOut;
        this.tokenDonwloadOut = tokenDonwloadOut;
    }

    @PostMapping(value = "/emition")
    public ResponseEntity<?> generateRequest(@Valid @RequestBody RequestDownloadCommand requestDownloadCommand) {

        var token = this.tokenDonwloadOut.getToken(requestDownloadCommand.getRfcEmisor());



        return ResponseEntity.ok(
            Map.of(
                "message", "Activo",
                "status", "success",
                "token", requestDownloadCommand,
                "timeStamp", System.currentTimeMillis()
            )
        );


    }
}
