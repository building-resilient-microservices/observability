package com.example.messaging.service;

import com.example.messaging.model.constant.EventName;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StarterService {

    private final ObservationRegistry observationRegistry;

    @EventListener(ApplicationReadyEvent.class)
    public void startMeUp() {

        //Observation events can be sent as metrics
        //These events are stored as time series with the value 1 in Prometheus
        Observation.start("application.events", observationRegistry)
            .event(Observation.Event.of(EventName.APPLICATION_READY_EVENT.toString()))
            .stop();

    }

}
