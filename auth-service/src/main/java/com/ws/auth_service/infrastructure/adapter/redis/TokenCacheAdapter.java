package com.ws.auth_service.infrastructure.adapter.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Component
public class TokenCacheAdapter {

    private final RedisTemplate<String, String> redisTemplate;
    private static final long TTL_MINUTES = 5;

    public TokenCacheAdapter(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveToken(String rfc, String token) {
        redisTemplate.opsForValue().set(rfc, token, Duration.ofMinutes(TTL_MINUTES));
    }

    public Optional<String> getToken(String rfc) {
        String token = redisTemplate.opsForValue().get(rfc);
        return Optional.ofNullable(token);
    }
}
