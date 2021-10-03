package com.justlife.casestudy.repository;

import com.justlife.casestudy.model.Cleaner;
import com.justlife.casestudy.model.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends BaseRepository<Reservation>{
//
//    @Query(value = "Select * From Cleaner\n" +
//            "Where id Not In (Select j.cleaner_id From reservations_cleaners j\n" +
//            "                    Where j.reservation_id In (Select r.id From Reservation r\n" +
//            "                                                Where (r.start_date_time Between ?1 AND ?2)\n" +
//            "                                                Or (r.end_date_time Between ?1 AND ?2)))",
//            nativeQuery = true)
    @Query("Select c From Cleaner c Left Join c.reservation r\n" +
            "Where r.id is NULL or Not ((r.startDateTime Between ?1 AND ?2)\n" +
            "      Or (r.endDateTime Between ?1 AND ?2))")
    List<Cleaner> findAvailableCleaners(LocalDateTime start, LocalDateTime end);

//    a = Select id From Reservations
//    Where (StartDateTime Between ?1 AND ?2)
//    Or (EndDateTime Between ?1 AND ?2)
//
//    Select * From Cleaners
//    Where id not in a;
}
