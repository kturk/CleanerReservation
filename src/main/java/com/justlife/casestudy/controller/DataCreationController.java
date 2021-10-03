package com.justlife.casestudy.controller;

import com.justlife.casestudy.model.Cleaner;
import com.justlife.casestudy.model.Reservation;
import com.justlife.casestudy.model.Vehicle;
import com.justlife.casestudy.repository.CleanerRepository;
import com.justlife.casestudy.repository.ReservationRepository;
import com.justlife.casestudy.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/data/")
@AllArgsConstructor
public class DataCreationController {

    @Resource
    private VehicleRepository vehicleRepository;
    @Resource
    private CleanerRepository cleanerRepository;
    @Resource
    private ReservationRepository reservationRepository;

    @GetMapping("/create")
    public void createData(){
        Vehicle vehicle1 = new Vehicle("0001", new ArrayList<>());
        vehicleRepository.save(vehicle1);

        Cleaner cleaner1 = new Cleaner("kemal", new ArrayList<>(), vehicle1);
        cleanerRepository.save(cleaner1);
        Cleaner cleaner2 = new Cleaner("serhan", new ArrayList<>(), vehicle1);
        cleanerRepository.save(cleaner2);
        Reservation res1 = new Reservation(
                LocalDateTime.parse("2020-12-31T15:00:00.00"),
                2,
                List.of(cleaner1, cleaner2));
        reservationRepository.save(res1);

    }
}
