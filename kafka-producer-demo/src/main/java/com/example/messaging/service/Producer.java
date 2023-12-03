package com.example.messaging.service;

import com.example.messaging.service.FakeMessage;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Observed
@RequiredArgsConstructor
public class Producer {

    private final KafkaTemplate<Integer, String> template;

    @Scheduled(fixedRate = 1_000)
    public void produce() {
        var message = FakeMessage.getFakeRecord();
        template.send(message);
        log.info(message.toString());
    }

}
