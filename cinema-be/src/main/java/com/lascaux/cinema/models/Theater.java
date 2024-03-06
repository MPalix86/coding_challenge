package com.lascaux.cinema.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @Enumerated(EnumType.ORDINAL)
    private Technology technology;

    @NotNull
    @Min(50)
    @Max(250)
    private int capacity;

    @NotNull
    private float price;

    public Theater(String name, int capacity, Technology technology, float price) {
        this.name = name;
        this.capacity = capacity;
        this.technology = technology;
        this.price = price;
    }
}
