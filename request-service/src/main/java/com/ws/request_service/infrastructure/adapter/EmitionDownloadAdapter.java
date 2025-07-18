package com.ws.request_service.infrastructure.adapter;

import com.ws.request_service.domain.model.RequestModel;
import com.ws.request_service.domain.port.outbound.EmitionDownloadOut;
import com.ws.request_service.domain.port.outbound.TokenDonwloadOut;
import com.ws.request_service.infrastructure.client.SoapClient;
import com.ws.request_service.infrastructure.client.SoapClientProvider;
import com.ws.request_service.infrastructure.client.builder.XmlBuilder;
import com.ws.request_service.infrastructure.client.parser.ResponseParser;
import com.ws.request_service.infrastructure.redis.TokenCacheAdapter;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class emitionDownloadAdapter implements EmitionDownloadOut, TokenDonwloadOut {

    private final TokenCacheAdapter tokenCacheAdapter;
    private final XmlBuilder builder;
    private final ResponseParser parser;
    private final SoapClient client;
    private final SoapClientProvider provider;

    public emitionDownloadAdapter(TokenCacheAdapter tokenCacheAdapter, XmlBuilder builder, ResponseParser parser, SoapClient client, SoapClientProvider provider) {
        this.tokenCacheAdapter = tokenCacheAdapter;
        this.builder = builder;
        this.parser = parser;
        this.client = provider.forEmition();
        this.provider = provider;
    }

    @Override
    public String getToken(String rfc) {
        return this.tokenCacheAdapter.getToken(rfc)
            .orElseThrow(() -> new IllegalStateException("Token no encontrado en Redis para RFC: " + rfc));
    }

    @Override
    public RequestModel requestDownload(RequestModel requestModel) throws Exception {

        String request = this.builder.buildEmition(requestModel);
        log.info("**** request:\n{}", request);
        return null;
    }
}
