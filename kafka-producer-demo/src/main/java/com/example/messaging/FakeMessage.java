package com.example.messaging;

import com.github.javafaker.Faker;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.sql.Timestamp;
import java.time.Instant;

public class FakeMessage {
    public static ProducerRecord<Integer, String> getFakeRecord() {
        var faker = Faker.instance();
        var key = faker.random().nextInt(1, 1_000_000);
        var value = faker.chuckNorris().fact();
        var partition = 0;
        var topic = "topic_0";
        var timestamp = Instant.now().getEpochSecond();
        return new ProducerRecord<>(topic, partition, timestamp, key, value);
    }

}
