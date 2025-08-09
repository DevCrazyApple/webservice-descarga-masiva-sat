package com.ws.status_service.infrastructure.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessagePublisher {

    private final RedisTemplate<String, Object> pubRedisTemplate;
    private final ChannelTopic topic;

    public MessagePublisher(RedisTemplate<String, Object> pubRedisTemplate, ChannelTopic topic) {
        this.pubRedisTemplate = pubRedisTemplate;
        this.topic = topic;
    }

    public void publish(Object message) {
        log.info("**** enviando mensaje al canal");
        this.pubRedisTemplate.convertAndSend(this.topic.getTopic(), message);
    }
}
