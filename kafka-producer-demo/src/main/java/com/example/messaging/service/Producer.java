package com.example.messaging.service;

import com.example.messaging.model.FactDTO;
import com.github.javafaker.Faker;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class Producer {

    private final KafkaTemplate<Integer, FactDTO> kafkaTemplate;
    private final NewTopic factTopic;
    private final Tracer tracer;
    private final ObservationRegistry observationRegistry;

    @SneakyThrows
    @Scheduled(fixedRate = 10_000)
    public void produce() {

        Observation.createNotStarted("produce", observationRegistry).observeChecked(() -> {
            var faker = Faker.instance();
            var fact = new FactDTO(faker.random().nextInt(1, 1_000_000), faker.chuckNorris().fact());
            var timestamp = Instant.now().getEpochSecond() * 1_000;
            var message = new ProducerRecord<>(factTopic.name(), factTopic.numPartitions(), timestamp, fact.id(), fact);
            var traceId = Objects.requireNonNull(tracer.currentSpan()).context().traceId();
            return kafkaTemplate.send(message).whenComplete((sr, ex) ->
                log.info("Producer sent message with traceId={} and timestamp={}, record={})",
                    traceId, timestamp, sr.getProducerRecord().value())).get();
        });

    }

}
