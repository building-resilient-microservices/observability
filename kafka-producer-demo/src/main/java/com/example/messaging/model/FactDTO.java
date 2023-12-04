package com.example.messaging.model;

import jakarta.validation.constraints.*;

public record FactDTO (
    @NotNull(message = "You must provide a valid fact ID")
    @Min(value = 1, message = "You must provide at least one 1")
    @Max(value = 999999, message = "You must noy provide more than 999999")
    Integer id,

    @NotBlank(message = "You must provide a fact")
    String fact

) {}