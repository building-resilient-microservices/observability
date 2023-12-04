package com.example.messaging.config;

import com.example.messaging.model.FactDTO;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Bean
    public NewTopic factTopic() {
        return TopicBuilder.name("topic_0")
            .partitions(0)
            .replicas(0)
            .build();
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Integer, FactDTO> listenerFactory(ConsumerFactory<Integer, FactDTO> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Integer, FactDTO> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.getContainerProperties().setObservationEnabled(true);
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

}
