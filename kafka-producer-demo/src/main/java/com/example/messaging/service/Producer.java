package com.example.messaging.service;

import com.example.messaging.model.FactDTO;
import com.github.javafaker.Faker;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
@Observed
@RequiredArgsConstructor
public class Producer {

    private final KafkaTemplate<Integer, FactDTO> template;
    private final NewTopic factTopic;

    @Scheduled(fixedRate = 1_000)
    public void produce() {

        var faker = Faker.instance();
        var fact = new FactDTO(faker.random().nextInt(1, 1_000_000), faker.chuckNorris().fact());
        var timestamp = Instant.now().getEpochSecond();
        var record = new ProducerRecord<>(factTopic.name(), factTopic.numPartitions(), timestamp, fact.id(), fact);

        var futureResult = template.send(record);
        futureResult.whenComplete((sr, ex) ->
            log.info("Sent at {} timestamp: {}", timestamp, sr.getProducerRecord().value()));

    }

}
