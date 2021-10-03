package com.justlife.casestudy.controller;

import com.justlife.casestudy.model.Cleaner;
import com.justlife.casestudy.payload.request.NewReservationRequest;
import com.justlife.casestudy.payload.request.UpdateReservationRequest;
import com.justlife.casestudy.payload.response.AvailabilityCheckResponse;
import com.justlife.casestudy.payload.response.AvailabilityCheckResponseForDay;
import com.justlife.casestudy.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservation/")
@AllArgsConstructor
public class ReservationController{

    private final ReservationService reservationService;

    @GetMapping("/availability-check-for-day")
    public List<AvailabilityCheckResponseForDay> checkAvailabilityForOnlyDate(@RequestParam(name = "dateTime")
                                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                  LocalDateTime day) {
        return reservationService.getAvailableCleaners(day);
    }

    @GetMapping("/availability-check")
    public List<AvailabilityCheckResponse> checkAvailabilityForAll(@RequestParam(name = "dateTime")
                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                             LocalDateTime dateTime,
                                                 @RequestParam(name = "duration") int duration) {
        List<Cleaner> availableCleaners = reservationService.getAvailableCleaners(dateTime, dateTime.plusHours(duration));
        return availableCleaners.stream().map(AvailabilityCheckResponse::new).collect(Collectors.toList());
    }

    @PostMapping("/new-reservation")
    public String createReservation(@RequestBody NewReservationRequest request) {
        return reservationService.createReservation(request);
    }

    @PutMapping("/update-reservation/{id}")
    public String updateReservation(@PathVariable(name = "id") Long reservationId,
                                    @RequestBody UpdateReservationRequest request) {
        return reservationService.updateReservation(reservationId, request);
    }
}
