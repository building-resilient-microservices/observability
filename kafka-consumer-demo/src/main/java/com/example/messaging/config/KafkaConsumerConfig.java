package com.example.messaging.config;

import com.example.messaging.model.FactDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaConsumerConfig {


    private final ConcurrentKafkaListenerContainerFactory<Integer, FactDTO> concurrentKafkaListenerContainerFactory;

    @Bean
    public NewTopic factTopic() {
        return TopicBuilder.name("topic_0")
            .partitions(0)
            .replicas(0)
            .build();
    }

    @PostConstruct
    void setup() {
        this.concurrentKafkaListenerContainerFactory.getContainerProperties().setObservationEnabled(true);
    }

}
