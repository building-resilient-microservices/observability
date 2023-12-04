package com.example.messaging.config;

import com.example.messaging.model.FactDTO;
import io.micrometer.common.KeyValues;
import org.apache.kafka.clients.admin.NewTopic;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.micrometer.KafkaRecordSenderContext;
import org.springframework.kafka.support.micrometer.KafkaTemplateObservationConvention;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public NewTopic factTopic() {
        return TopicBuilder.name("topic_0")
            .partitions(0)
            .replicas(0)
            .build();
    }

    @Bean
    public KafkaTemplate<Integer, FactDTO> kafkaTemplate(ProducerFactory<Integer, FactDTO> producerFactory) {
        var t = new KafkaTemplate<>(producerFactory);
        t.setObservationEnabled(true);
        t.setObservationConvention(new KafkaTemplateObservationConvention() {
            @NotNull
            @Override
            public KeyValues getLowCardinalityKeyValues(@NotNull KafkaRecordSenderContext context) {
                return KeyValues.of("topic", context.getDestination(),
                    "id", String.valueOf(context.getRecord().key()));
            }
        });
        return t;
    }

}
