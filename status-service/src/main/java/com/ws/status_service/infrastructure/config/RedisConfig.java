package com.ws.status_service.infrastructure.config;

import com.ws.status_service.infrastructure.redis.MessagePublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, ?> redisTemplate(RedisConnectionFactory factory) {

//        System.out.println("**** bean redis");
        var template = new RedisTemplate<String, Object>();

        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());

        return template;
    }

    @Bean
    public RedisTemplate<String, ?> pubRedisTemplate(RedisConnectionFactory factory) {
        var template = new RedisTemplate<String, Object>();

        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

    @Bean
    public ChannelTopic topic(@Value("${redis.channel.pubsub}") String channel) {
        return new ChannelTopic(channel);
    }

    @Bean
    public MessagePublisher messagePublisher(RedisTemplate<String, Object> pubRedisTemplate, ChannelTopic topic) {
        return new MessagePublisher(pubRedisTemplate, topic);
    }
}
