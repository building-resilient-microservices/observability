package com.example.messaging.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "fact")
@Value
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = PROTECTED)
public class Fact {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "fact", nullable = false)
    private String fact;

    public static Fact of(FactDTO dto) {
        return new Fact(dto.id(), dto.fact());
    }

}
