package com.ws.download_service.infrastructure.redis;

import com.ws.download_service.domain.model.PackageModel;
import com.ws.download_service.domain.port.outbound.DownloadRequestOut;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
@Service
public class StatusStreamConsumer {
    private final RedisTemplate<String, Object> streamRedisTemplate;
    private final String streamName;
    private final ExecutorService executor = Executors.newFixedThreadPool(4);
    private final DownloadRequestOut downloadRequestOut;

    // Identificador único del consumidor para permitir múltiples instancias
    private final String consumerName;

    private static final String GROUP_NAME = "status-group";

    // Tiempo máximo que un mensaje puede estar sin ser procesado antes de reclamarlo (ms)
    private static final long IDLE_TIME_MILLIS = 10_000;


    public StatusStreamConsumer(
            @Qualifier("streamRedisTemplate") RedisTemplate<String, Object> streamRedisTemplate,
            @Qualifier("statusStreamName") String streamName, DownloadRequestOut downloadRequestOut,
            @Value("${redis.consumer.name:}") String configuredConsumerName) {
        this.streamRedisTemplate = streamRedisTemplate;
        this.streamName = streamName;
        this.downloadRequestOut = downloadRequestOut;
        if (configuredConsumerName == null || configuredConsumerName.isBlank()) {
            this.consumerName = "processor-" + java.util.UUID.randomUUID();
        } else {
            this.consumerName = configuredConsumerName;
        }
    }

    @PostConstruct
    public void init() {
        log.info("🔵 Redis Stream Consumer name: {}", consumerName);
        reclaimPendingMessages();
    }


    /**
     * Periodicamente consume mensajes nuevos
     */
    @Scheduled(fixedDelay = 4000)
    public void consume() {
        log.info("**** consume");
        readNewMessages();
        reclaimPendingMessages();
    }

    private void readNewMessages() {
        log.info("**** readNewMessages");
        List<MapRecord<String, Object, Object>> records = streamRedisTemplate
                .opsForStream()
                .read(
                    Consumer.from(GROUP_NAME, consumerName),
                    StreamReadOptions.empty()
                        .count(10)
                        .block(Duration.ofMillis(500)),
                    StreamOffset.create(streamName, ReadOffset.lastConsumed())
                );

        if (records != null && !records.isEmpty()) {
            for (MapRecord<String, Object, Object> record : records) {
                executor.submit(() -> {
                    try {
                        handleMessage(record);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    }


    /**
     * Reclama mensajes pendientes con idle > IDLE_TIME_MILLIS para procesarlos
     */
    private void reclaimPendingMessages() {
        log.info("**** reclaimPendingMessages");
        PendingMessages pending = streamRedisTemplate.opsForStream()
                .pending(streamName, GROUP_NAME, Range.unbounded(), 10);

        if (!pending.isEmpty()) {
            for (PendingMessage pm : pending) {

                if (pm.getElapsedTimeSinceLastDelivery().toMillis() >= IDLE_TIME_MILLIS) {
                    List<MapRecord<String, Object, Object>> claimed = streamRedisTemplate.opsForStream()
                        .claim(
                            streamName,
                            GROUP_NAME,
                            consumerName,
                            Duration.ofMillis(IDLE_TIME_MILLIS),
                            pm.getId()
                        );

                    if (!claimed.isEmpty()) {
                        for (MapRecord<String, Object, Object> record : claimed) {
                            executor.submit(() -> {
                                try {
                                    handleMessage(record);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        }
                    }
                }
            }
        }
    }


    private void handleMessage(MapRecord<String, Object, Object> record) throws Exception {
        Map<Object, Object> data = record.getValue();

        String rfcSolicitante = (String) data.get("rfcSolicitante");
        String idRequest = (String) data.get("idRequest");

//        System.out.printf("🛠️ Procesando mensaje - RFC: %s, ID: %s%n", rfc, id);
        log.info("Procesando mensaje - RFC: {}, ID: {}", rfcSolicitante, idRequest);


        // preparamos el paquete y enviamos a descargar
        PackageModel model = new PackageModel(
            rfcSolicitante,
            idRequest
        );
        this.downloadRequestOut.getDownload(model);

        // sleep
        Thread.sleep(500);

        // Confirmar y borrar mensaje procesado
        streamRedisTemplate.opsForStream().acknowledge(streamName, GROUP_NAME, record.getId());
        streamRedisTemplate.opsForStream().delete(streamName, record.getId());
    }
}
