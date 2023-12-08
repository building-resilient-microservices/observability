package com.example.messaging.config;

import com.example.messaging.model.FactDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@RequiredArgsConstructor
@EnableKafka
public class KafkaProducerConfig {

    private final KafkaTemplate<Integer, FactDTO> kafkaTemplate;

    @Bean
    public NewTopic factTopic() {
        return TopicBuilder.name("topic_0")
            .partitions(0)
            .replicas(0)
            .build();
    }

    @PostConstruct
    void setup() {
        this.kafkaTemplate.setObservationEnabled(true);
    }

}
