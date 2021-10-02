package com.justlife.casestudy.controller;

import com.justlife.casestudy.model.Cleaner;
import com.justlife.casestudy.payload.request.NewReservationRequest;
import com.justlife.casestudy.payload.request.UpdateReservationRequest;
import com.justlife.casestudy.service.ReservationService;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation/")
@AllArgsConstructor
public class ReservationController{

    private final ReservationService reservationService;

    @GetMapping("/availability-check-for-date")
    public List<Cleaner> checkAvailabilityForOnlyDate(@RequestParam(name = "day") DateTime day) {

        return reservationService.findCleaners(day);
    }

    @GetMapping("/availability-check")
    public List<Cleaner> checkAvailabilityForAll(@RequestParam(name = "dateTime") DateTime dateTime,
                                                 @RequestParam(name = "duration") int duration) {
    // TODO change signature
        return reservationService.findCleaners(dateTime, duration);
    }

    @PostMapping("/new-reservation")
    public List<Cleaner> createReservation(@RequestBody NewReservationRequest request) {

        return reservationService.createReservation(request);
    }

    @PutMapping("/{id}")
    public String updateReservation(@PathVariable(name = "id") Long reservationId,
                                    @RequestBody UpdateReservationRequest request) {

        return reservationService.updateReservation(reservationId, request.getDateTime());
    }
}
