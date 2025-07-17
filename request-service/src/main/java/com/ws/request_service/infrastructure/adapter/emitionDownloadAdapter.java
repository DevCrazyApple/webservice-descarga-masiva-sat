package com.ws.request_service.infrastructure.adapter;

import com.ws.request_service.application.command.RequestDownloadCommand;
import com.ws.request_service.domain.model.RequestModel;
import com.ws.request_service.domain.port.outbound.EmitionDownloadOut;
import com.ws.request_service.domain.port.outbound.TokenDonwloadOut;
import com.ws.request_service.infrastructure.redis.TokenCacheAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class emitionDownloadAdapter implements EmitionDownloadOut, TokenDonwloadOut {

    private final TokenCacheAdapter tokenCacheAdapter;

    public emitionDownloadAdapter(TokenCacheAdapter tokenCacheAdapter) {
        this.tokenCacheAdapter = tokenCacheAdapter;
    }

    @Override
    public String getToken(String rfc) {
        return this.tokenCacheAdapter.getToken(rfc)
            .orElseThrow(() -> new IllegalStateException("Token no encontrado en Redis para RFC: " + rfc));
    }

    @Override
    public RequestModel requestDownload(RequestDownloadCommand requestDownloadCommand) {
        return null;
    }
}
