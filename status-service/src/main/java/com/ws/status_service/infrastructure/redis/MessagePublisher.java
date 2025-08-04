package com.ws.status_service.infrastructure.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

public class MessagePublisher {

    private final RedisTemplate<String, Object> pubRedisTemplate;
    private final ChannelTopic topic;

    public MessagePublisher(RedisTemplate<String, Object> pubRedisTemplate, ChannelTopic topic) {
        this.pubRedisTemplate = pubRedisTemplate;
        this.topic = topic;
    }

    public void publish(Object message) {
        this.pubRedisTemplate.convertAndSend(this.topic.getTopic(), message);
    }
}
