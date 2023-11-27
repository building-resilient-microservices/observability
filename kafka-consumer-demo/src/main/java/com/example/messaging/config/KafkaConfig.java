package com.example.messaging.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public ConsumerFactory<Object, Object> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put("bootstrap.servers", "pkc-6ojv2.us-west4.gcp.confluent.cloud:9092");
        props.put("security.protocol", "SASL_SSL");
        props.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username='CQBL26VT2O55WDK3' password='6x5RxuX12lc6+xiOyLSBqwzRX859g7KvCvcoM2iT04vCpdvvSM1uy2sL3higjQv2';");
        props.put("sasl.mechanism", "PLAIN");
        props.put("key.deserializer", org.apache.kafka.common.serialization.IntegerDeserializer.class);
        props.put("value.deserializer", org.apache.kafka.common.serialization.StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

}
