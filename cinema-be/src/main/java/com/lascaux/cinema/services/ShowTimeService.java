package com.lascaux.cinema.services;

import com.lascaux.cinema.models.ShowTime;
import com.lascaux.cinema.repositories.ShowTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShowTimeService {

    @Autowired
    ShowTimeRepository showTimeRepository;

    public ShowTime getById(Long id) {
        return showTimeRepository.findById(id).orElse(null);
    }
}
