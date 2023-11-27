package com.example.messaging.controller;

import com.example.messaging.model.FactDTO;
import com.example.messaging.service.FactService;
import io.micrometer.observation.annotation.Observed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Observed
@RestController
@RequiredArgsConstructor
public class FactController {

    private final FactService factService;

    @PostMapping("/facts")
    public FactDTO createFact(@RequestBody @Valid FactDTO factDTO) {
        return factService.create(factDTO);
    }

    @GetMapping("/facts/{factId}")
    public FactDTO readFact(@PathVariable Integer factId) {
        return factService.read(factId);
    }

    @DeleteMapping("/facts/{factId}")
    public void deleteFact(@PathVariable Integer factId) {
        factService.delete(factId);
    }

    @GetMapping("/errors")
    public void simulateErrors() {
        throw new RuntimeException("Simulated server error. Don't panic ;)");
    }

}
