package com.example.messaging.listener;

import com.example.messaging.model.FactDTO;
import com.example.messaging.model.constant.MetricName;
import com.example.messaging.service.FactService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static java.time.Instant.*;

@Slf4j
@Component
@Observed
@RequiredArgsConstructor
public class KafkaConsumer {

    private final FactService factService;

    // It is your responsibility to hold a strong reference to the state object that you are measuring (item 10.3)
    // https://micrometer.io/docs/concepts#_manually_incrementing_or_decrementing_a_gauge
    private static final Map<String, AtomicLong> gauges = new ConcurrentHashMap<>();

    @KafkaListener(topics = "topic_0", groupId = "group_0")
    public void consume(@Payload String message,
                        @Header(KafkaHeaders.RECEIVED_KEY) Integer id,
                        @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {

        var fact = factService.create(new FactDTO(id, message));

        var syncTime = new AtomicLong((now().toEpochMilli() / 1_000) - timestamp);
        gauges.put(MetricName.SYNC_TIME.toString(), syncTime);

        //Since we're in an observation scope - this log line will contain tracing MDC entries ...
        log.info(MessageFormat.format("{0} (metadata: full sync after {1,number,#}ms)", fact, syncTime));

        Gauge.builder(MetricName.SYNC_TIME.toString(), () -> gauges.get(MetricName.SYNC_TIME.toString())).register(Metrics.globalRegistry);
        log.trace("Gauge value in globalRegistry is {}", Metrics.globalRegistry.get(MetricName.SYNC_TIME.toString()).gauge().value());

    }

}
