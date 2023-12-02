package com.example.messaging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public ProducerFactory<Object, Object> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", org.apache.kafka.common.serialization.IntegerSerializer.class);
        props.put("value.serializer", org.apache.kafka.common.serialization.StringSerializer.class);

        // for confluent cloud, you need to add the following properties:
        // props.put("security.protocol", "SASL_SSL");
        // props.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username='CQBL26VT2O55WDK3' password='6x5RxuX12lc6+xiOyLSBqwzRX859g7KvCvcoM2iT04vCpdvvSM1uy2sL3higjQv2';");
        // props.put("sasl.mechanism", "PLAIN");

        return new DefaultKafkaProducerFactory<>(props);
    }

}
