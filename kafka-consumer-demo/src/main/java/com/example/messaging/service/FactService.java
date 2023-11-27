package com.example.messaging.service;


import com.example.messaging.exception.FactNotFoundException;
import com.example.messaging.model.Fact;
import com.example.messaging.model.FactDTO;
import com.example.messaging.repository.FactRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Observed
@RequiredArgsConstructor
public class FactService {

    private final FactRepository factRepository;

    public FactDTO create(FactDTO factDTO) {
        return FactDTO.of(factRepository.save(Fact.of(factDTO)));
    }

    public FactDTO read(Integer factId) {
        return FactDTO.of(factRepository.findById(factId)
                .orElseThrow(() -> new FactNotFoundException(factId.toString())));
    }

    public void delete(Integer factId) {
        factRepository.deleteById(factId);
    }

}
