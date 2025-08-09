package com.ws.download_service.infrastructure.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisStreamGroupInitializer {

    private final RedisTemplate<String, Object> streamRedisTemplate;

    @Value("${redis.stream.status}")
    private String streamName;

    private static final String GROUP_NAME = "status-group";

    public RedisStreamGroupInitializer(@Qualifier("streamRedisTemplate") RedisTemplate<String, Object> streamRedisTemplate) {
        this.streamRedisTemplate = streamRedisTemplate;
    }

    @PostConstruct
    public void createStreamGroup() {
        try {
            streamRedisTemplate.opsForStream().createGroup(
                streamName,
                ReadOffset.latest(),
                GROUP_NAME
            );
            log.info("✅ Grupo '{}' creado en stream: {}", GROUP_NAME, streamName);
        } catch (Exception e) {
            log.info("ℹ️ Grupo '{}' ya existe en stream: {}", GROUP_NAME, streamName);
        }
    }
}
