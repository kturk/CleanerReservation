package com.justlife.casestudy.service;

import com.justlife.casestudy.model.Cleaner;

import com.justlife.casestudy.model.Reservation;
import com.justlife.casestudy.model.Vehicle;
import com.justlife.casestudy.payload.request.NewReservationRequest;
import com.justlife.casestudy.repository.ReservationRepository;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService extends BaseService<Reservation> {

    private final ReservationRepository reservationRepository;
    private final VehicleService vehicleService;

    public ReservationService(ReservationRepository repository,
                              VehicleService vehicleService) {
        super("Reservation", repository);
        this.reservationRepository = repository;
        this.vehicleService = vehicleService;
    }
    public List<Cleaner> findCleaners(DateTime day) {
        return null;
    }

    public List<Cleaner> findCleaners(DateTime day, int duration) {
        return null;
    }

    public List<Cleaner> createReservation(NewReservationRequest request) {
        List<Vehicle> vehicles = vehicleService.findAll();

        List<Cleaner> availableCleaners = new ArrayList<>();
        for (Vehicle vehicle : vehicles){
            List<Cleaner> cleaners = vehicle.getCleaners();
            for (Cleaner cleaner : cleaners){
                boolean isAvailableCleaner = true;
                for (Reservation reservation : cleaner.getReservation()){
                    if (!isAvailableInterval(request, reservation)) {
                        isAvailableCleaner = false;
                        break;
                    }
                }
                if (isAvailableCleaner)
                    availableCleaners.add(cleaner);
                if (isEnoughCleaners(request, availableCleaners))
                    return availableCleaners;
            }
            availableCleaners.clear();
        }
//        return List.of(new Cleaner("kemal", null, null, null)); TODO
        return null;
    }

    private boolean isEnoughCleaners(NewReservationRequest request, List<Cleaner> availableCleaners) {
        return availableCleaners.size() == request.getCleanerCount();
    }

    private boolean isAvailableInterval(NewReservationRequest request, Reservation reservation) {
        return  (request.getStartDateTime().isAfter(reservation.getStartDateTime())
                && request.getEndDateTime().isAfter(reservation.getEndDateTime()))
                ||
                (request.getStartDateTime().isBefore(reservation.getStartDateTime())
                && request.getEndDateTime().isBefore(reservation.getEndDateTime()));
    }

    public String updateReservation(Long reservationId, LocalDateTime newDateTime) {
        return "";
    }
}
