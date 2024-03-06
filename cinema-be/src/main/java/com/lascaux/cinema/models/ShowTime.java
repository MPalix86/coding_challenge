package com.lascaux.cinema.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ShowTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    // Usa LocalTime invece di Date
    private LocalTime startTime;

    private LocalTime endTime;

    private int occupiedSeats;

    // Rimuovi il costruttore che prende Date

    // Aggiungi un costruttore che prende LocalTime
    public ShowTime(LocalTime startTime, LocalTime endTime, int occupiedSeats) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.occupiedSeats = occupiedSeats;
    }
}