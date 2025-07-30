package com.ws.request_service.application.service;

import com.ws.request_service.application.command.RequestDownloadCommand;
import com.ws.request_service.application.mapper.CommandToModel;
import com.ws.request_service.domain.model.PfxModel;
import com.ws.request_service.domain.model.RequestModel;
import com.ws.request_service.domain.port.inbound.EmitionRequestIn;
import com.ws.request_service.domain.port.inbound.TokenRequestIn;
import com.ws.request_service.domain.port.outbound.EmitionRequestOut;
import com.ws.request_service.domain.port.outbound.TokenRequestOut;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class EmitionSatService implements TokenRequestIn, EmitionRequestIn {

    private final TokenRequestOut tokenRequestOut;
    private final EmitionRequestOut emitionRequestOut;
    private final CommandToModel mapper;

    public EmitionSatService(TokenRequestOut tokenRequestOut, EmitionRequestOut emitionRequestOut, CommandToModel mapper) {
        this.tokenRequestOut = tokenRequestOut;
        this.emitionRequestOut = emitionRequestOut;
        this.mapper = mapper;
    }


    @Override
    public String requestDownload(RequestModel requestModel) throws Exception {
        return this.emitionRequestOut.requestDownload(requestModel);
    }

    @Override
    public RequestModel toModel(RequestDownloadCommand requestDownloadCommand) {
        return this.mapper.toModel(requestDownloadCommand);
    }

    @Override
    public String getToken(String rfc) {
        return this.tokenRequestOut.getToken(rfc);
    }

    @Override
    public PfxModel getPfx(String rfc) {
        return this.tokenRequestOut.getPfx(rfc);
    }
}
