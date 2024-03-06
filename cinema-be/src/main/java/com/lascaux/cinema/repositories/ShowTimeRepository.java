package com.lascaux.cinema.repositories;

import com.lascaux.cinema.models.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowTimeRepository extends JpaRepository<ShowTime, Long> {

    @Query("SELECT s FROM ShowTime s WHERE s.schedule.id = :scheduleId")
    List<ShowTime> findByScheduleId(Long scheduleId);


}
