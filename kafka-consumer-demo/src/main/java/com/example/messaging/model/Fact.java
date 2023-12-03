package com.example.messaging.model;

import jakarta.persistence.*;
import lombok.*;

import static lombok.AccessLevel.*;

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

    public static Fact of(FactDTO dto){
        return new Fact(dto.id(), dto.fact());
    }

}
