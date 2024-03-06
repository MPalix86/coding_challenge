package com.lascaux.cinema.services;

import com.lascaux.cinema.models.Schedule;
import com.lascaux.cinema.models.ShowTime;
import com.lascaux.cinema.repositories.ScheduleRepository;
import com.lascaux.cinema.repositories.ShowTimeRepository;
import com.lascaux.cinema.repositories.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ScheduleService {

    
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private TheaterRepository theaterRepository;
    @Autowired
    private ShowTimeRepository showTimeRepository;


    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Schedule getScheduleById(Long id) {
        return scheduleRepository.findById(id).orElse(null);
    }

    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }


    private List<Schedule> addShowTimes(List<Schedule> schedules) {
        for (Schedule schedule : schedules) {
            List<ShowTime> showTimes = showTimeRepository.findByScheduleId(schedule.getId());
            showTimes.forEach(showTime -> {
                showTime.setSchedule(null);
            });
            schedule.setShowTimes(showTimes);

        }
        return schedules;
    }

    public List<Schedule> filterSchedules(Date startDate) {
        List<Schedule> schedules;

        // Entrambi i parametri sono presenti
        schedules = scheduleRepository.findByStartDateTimeAfter(startDate);


        schedules = addShowTimes(schedules);
        return schedules;
    }


    public List<Schedule> getCurrentSchedules() {

        List<Schedule> schedules;
        Calendar today = Calendar.getInstance();
        schedules = scheduleRepository.findSchedulesStartingFromNow(today);

        schedules = addShowTimes(schedules);
        return schedules;

    }


}



