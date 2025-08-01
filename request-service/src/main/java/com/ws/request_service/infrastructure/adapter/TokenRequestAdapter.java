package com.ws.request_service.infrastructure.adapter;

import com.ws.request_service.domain.model.PfxModel;
import com.ws.request_service.domain.port.outbound.TokenRequestOut;
import com.ws.request_service.infrastructure.redis.TokenCacheAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TokenRequestAdapter implements TokenRequestOut {

    private final String API_URL_AUTH = "http://auth-service:8080/api/auth/pfx/{rfc}";

    private final RestTemplate restTemplate;
    private final TokenCacheAdapter tokenCacheAdapter;

    public TokenRequestAdapter(RestTemplate restTemplate, TokenCacheAdapter tokenCacheAdapter) {
        this.restTemplate = restTemplate;
        this.tokenCacheAdapter = tokenCacheAdapter;
    }

    @Override
    public String getToken(String rfc) {
        return this.tokenCacheAdapter.getToken(rfc)
            .orElseThrow(() -> new IllegalStateException("Token no encontrado en Redis para RFC: " + rfc));
    }

    @Override
    public PfxModel getPfx(String rfc) {
        return this.restTemplate.getForObject(API_URL_AUTH, PfxModel.class, rfc);
    }
}
