package com.lascaux.cinema.api.v1.controllers;

import com.lascaux.cinema.models.ShowTime;
import com.lascaux.cinema.services.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/showTime")
@CrossOrigin(origins = "http://localhost:4200")
public class ShowTimeController {

    @Autowired
    ShowTimeService showTimeService;


    @GetMapping("/{id}")
    public ResponseEntity<ShowTime> getScheduleById(@PathVariable Long id) {
        System.out.println(id);
        ShowTime showTime = showTimeService.getById(id);
        return new ResponseEntity<>(showTime, HttpStatus.OK);
    }


}
