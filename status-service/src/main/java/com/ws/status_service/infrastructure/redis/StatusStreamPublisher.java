package com.ws.status_service.infrastructure.redis;

import com.ws.status_service.domain.model.PubModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class StatusStreamPublisher {
    private final StreamOperations<String, Object, Object> streamOps;
    private final String streamName;

    public StatusStreamPublisher(
            RedisTemplate<String, Object> streamRedisTemplate,
            @Qualifier("statusStreamName") String streamName) {
        this.streamOps = streamRedisTemplate.opsForStream();
        this.streamName = streamName;
    }

    public void publish(PubModel message) {
        // log.info("**** enviando mensaje al stream");
        try {
            streamOps.add(streamName, Map.of(
                "rfcSolicitante", message.getRfcSolicitante(),
                "idRequest", message.getIdRequest()
//                "packagesIds", message.getPackagesIds(),
//                "token", message.getToken(),
//                "privateKey", message.getPrivateKey(),
//                "certificate", message.getCertificate()
            ));
            log.info("Mensaje publicado exitosamente.");
        } catch (Exception e) {
            log.error("Error al publicar mensaje en el stream Redis", e);
        }
    }

}
