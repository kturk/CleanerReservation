package com.justlife.casestudy.repository;

import com.justlife.casestudy.model.Cleaner;
import com.justlife.casestudy.model.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends BaseRepository<Reservation>{

    @Query("Select c From Cleaner c Left Join c.reservation r\n" +
            "Where r.id is NULL or Not ((r.startDateTime Between ?1 AND ?2)\n" +
            "      Or (r.endDateTime Between ?1 AND ?2))")
    List<Cleaner> findAvailableCleaners(LocalDateTime start, LocalDateTime end);
}
