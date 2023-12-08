package com.example.messaging.listener;

import com.example.messaging.model.FactDTO;
import com.example.messaging.model.constant.MetricName;
import com.example.messaging.service.FactService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    // It is your responsibility to hold a strong reference to the state object that you are measuring (item 10.3)
    // https://micrometer.io/docs/concepts#_manually_incrementing_or_decrementing_a_gauge
    private static final Map<String, AtomicLong> gauges = new ConcurrentHashMap<>();
    private final FactService factService;
    private final Tracer tracer;
    private final ObservationRegistry observationRegistry;

    @KafkaListener(groupId = "group_0", topics = "topic_0")
    public void onMessage(@Payload FactDTO fact,
                          @Header(KafkaHeaders.RECEIVED_KEY) Integer id,
                          @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {
        Observation.createNotStarted("on-message", this.observationRegistry).observe(() -> consume(fact, timestamp));
    }

    private void consume(FactDTO fact, Long timestamp) {

        factService.create(fact);
        var traceId = Objects.requireNonNull(tracer.currentSpan()).context().traceId();
        log.info("Consumer received message with traceId={} and timestamp={}, record={})", traceId, timestamp, fact);

        var syncTime = new AtomicLong((Instant.now().toEpochMilli()) - timestamp);
        gauges.put(MetricName.SYNC_TIME.toString(), syncTime);

        Gauge.builder(MetricName.SYNC_TIME.toString(), () -> gauges.get(MetricName.SYNC_TIME.toString())).register(Metrics.globalRegistry);
        if (log.isTraceEnabled()) {
            log.trace("Gauge value in globalRegistry is {}", Metrics.globalRegistry.get(MetricName.SYNC_TIME.toString()).gauge().value());
        }

    }

}
