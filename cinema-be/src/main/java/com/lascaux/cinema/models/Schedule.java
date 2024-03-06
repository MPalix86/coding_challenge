package com.lascaux.cinema.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    @ManyToOne
    @JoinColumn(name = "theater_id", nullable = false)
    private Theater theater;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDateTime;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDateTime;

    @Transient
    private List<ShowTime> showTimes = new ArrayList<>();


    public Schedule(Film film, Theater theater, Date startDateTime, Date endDateTime) {
        this.film = film;
        this.theater = theater;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    @PrePersist
    @PreUpdate
    private void validateEndDateTime() {
        long THREE_WEEKS_IN_MILLISECONDS = 21 * 24 * 60 * 60 * 1000L; // 3 settimane in millisecondi
        long startDateTimeMillis = startDateTime.getTime();
        long endDateTimeMillis = endDateTime.getTime();

        // Calcolo la data minima di fine del film
        Calendar minEndDate = Calendar.getInstance();
        minEndDate.setTime(startDateTime);
        minEndDate.add(Calendar.WEEK_OF_YEAR, 1); // Aggiungo una settimana alla data di inizio

        // Calcolo la data massima di fine del film (3 settimane dopo la data di inizio)
        Calendar maxEndDate = Calendar.getInstance();
        maxEndDate.setTime(startDateTime);
        maxEndDate.add(Calendar.WEEK_OF_YEAR, 3); // Aggiungo tre settimane alla data di inizio

        // Confronto la data di fine calcolata con quella fornita
        if (endDateTimeMillis < minEndDate.getTimeInMillis() || endDateTimeMillis > maxEndDate.getTimeInMillis()) {
            throw new IllegalArgumentException("La data di fine non rispetta il vincolo di durata del film in sala.");
        }
    }
}