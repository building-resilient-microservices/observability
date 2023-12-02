package com.example.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log
@Component
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
