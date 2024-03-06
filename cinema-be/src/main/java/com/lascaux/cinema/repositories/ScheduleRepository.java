package com.lascaux.cinema.repositories;

import com.lascaux.cinema.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> getSchedulesByTheaterId(Long id);

    List<Schedule> findByStartDateTimeAfter(Date startDate);

    List<Schedule> findByEndDateTimeBefore(Date endDate);


    @Query("SELECT s FROM Schedule s WHERE s.startDateTime >= :startDate")
    List<Schedule> findSchedulesStartingFromNow(@Param("startDate") Calendar startDate);
}