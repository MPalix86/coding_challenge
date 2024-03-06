package com.lascaux.cinema.services;

import com.lascaux.cinema.models.Theater;
import com.lascaux.cinema.repositories.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheaterService {

    @Autowired
    private TheaterRepository theaterRepository;

    public List<Theater> getAllTheaters() {
        return theaterRepository.findAll();
    }

    public Theater getTheaterById(Long id) {
        return theaterRepository.findById(id).orElse(null);
    }

    public Theater createTheater(Theater theater) {
        return theaterRepository.save(theater);
    }

    public Theater updateTheater(Long id, Theater theater) {
        if (theaterRepository.existsById(id)) {
            return theaterRepository.save(theater);
        }
        return null;
    }

    public void deleteTheater(Long id) {
        theaterRepository.deleteById(id);
    }
}
