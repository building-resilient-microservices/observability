package com.example.messaging.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FactDTO(

    @NotNull(message = "You must provide a valid fact ID")
    @Min(value = 1, message = "You must provide at least one 1")
    @Max(value = 999999, message = "You must noy provide more than 999999")
    Integer id,

    @NotBlank(message = "You must provide a fact")
    String fact

) {
    public static FactDTO of(Fact fact) {
        return new FactDTO(fact.getId(), fact.getFact());
    }
}
