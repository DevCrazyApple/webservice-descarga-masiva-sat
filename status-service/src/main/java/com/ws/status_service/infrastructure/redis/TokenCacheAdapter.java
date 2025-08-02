package com.ws.status_service.infrastructure.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TokenCacheAdapter {

    private final RedisTemplate<String, String> redisTemplate;

    public TokenCacheAdapter(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Optional<String> getToken(String rfc) {
        String token = redisTemplate.opsForValue().get(rfc);
        return Optional.ofNullable(token);
    }
}
