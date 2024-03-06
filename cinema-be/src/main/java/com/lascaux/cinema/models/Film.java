package com.lascaux.cinema.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 1000)
    private String description;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    @NotNull
    @Min(60)
    private int minutes;

    private String imageLink;

    public Film(String name, String description, Date releaseDate, int minutes) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.minutes = minutes;
    }
}
