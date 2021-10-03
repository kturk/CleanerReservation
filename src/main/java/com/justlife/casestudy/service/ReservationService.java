package com.justlife.casestudy.service;

import com.justlife.casestudy.exceptions.*;
import com.justlife.casestudy.model.Cleaner;
import com.justlife.casestudy.model.Reservation;
import com.justlife.casestudy.model.Vehicle;
import com.justlife.casestudy.payload.request.BaseRequest;
import com.justlife.casestudy.payload.request.NewReservationRequest;
import com.justlife.casestudy.payload.request.UpdateReservationRequest;
import com.justlife.casestudy.payload.response.AvailabilityCheckResponseForDay;
import com.justlife.casestudy.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReservationService extends BaseService<Reservation> {

    private final ReservationRepository reservationRepository;
    private final VehicleService vehicleService;
    private final CleanerService cleanerService;

    public ReservationService(ReservationRepository repository,
                              VehicleService vehicleService,
                              CleanerService cleanerService) {

        super("Reservation", repository);
        this.reservationRepository = repository;
        this.vehicleService = vehicleService;
        this.cleanerService = cleanerService;
    }
    public List<AvailabilityCheckResponseForDay> getAvailableCleaners(LocalDateTime day) {
        if(isFriday(day))
            throw new FridayWorkNotAllowedException();

        List<Cleaner> cleaners = cleanerService.findAll();
        List<AvailabilityCheckResponseForDay> availabilityResponse = new ArrayList<>();
        for (Cleaner cleaner : cleaners) {
            availabilityResponse.add(findCleanerAvailableSchedule(cleaner, day));
        }
        return availabilityResponse;
    }

    private boolean isFriday(LocalDateTime day) {
        return day.getDayOfWeek().equals(DayOfWeek.FRIDAY);

    }

    private AvailabilityCheckResponseForDay findCleanerAvailableSchedule(Cleaner cleaner, LocalDateTime day) {
        List<Reservation> reservationsOfCleaner = cleaner.getReservation();
        if (reservationsOfCleaner.isEmpty()) {
            return new AvailabilityCheckResponseForDay(cleaner.getName(), "08:00-22:00");
        }
        else {
            String availableTimesOfCleaner = "";
            LocalTime intervalStart = LocalTime.parse("08:00");
            for (Reservation reservation: reservationsOfCleaner) {
                if (isDateSame(day, reservation)) {
                    LocalTime timeOfReservation = getTimeFromLocalDateTime(reservation.getStartDateTime());
                    if (isDurationAvailable(timeOfReservation, intervalStart)) {
                        availableTimesOfCleaner += getAvailableTimeAsString(intervalStart, timeOfReservation);
                        intervalStart = add30MinuteBreak(reservation);
                    }
                }
            if (isDurationAvailable(LocalTime.parse("22:00"), intervalStart))
                availableTimesOfCleaner += getAvailableTimeAsString(intervalStart, LocalTime.parse("22:00"));
            }
            if (isAllReservationsInDifferentDays(availableTimesOfCleaner))
                return new AvailabilityCheckResponseForDay(cleaner.getName(), "08:00-22:00");
            return new AvailabilityCheckResponseForDay(cleaner.getName(), availableTimesOfCleaner);
        }
    }

    private boolean isAllReservationsInDifferentDays(String availableTimesOfCleaner) {
        return availableTimesOfCleaner.equals("");
    }

    private LocalTime add30MinuteBreak(Reservation reservation) {
        return getTimeFromLocalDateTime(reservation.getEndDateTime()).plusMinutes(30);
    }

    private boolean isDurationAvailable(LocalTime timeOfReservation, LocalTime intervalStart) {
        return Duration.between(intervalStart, timeOfReservation).toMinutes() > 120;
    }

    private String getAvailableTimeAsString(LocalTime intervalStart, LocalTime timeOfReservation) {
        return intervalStart.toString() + "-" + timeOfReservation.toString() + ", ";
    }

    private LocalTime getTimeFromLocalDateTime(LocalDateTime time) {
        return LocalTime.parse(time.toString().split("T")[1]);
    }

    private boolean isDateSame(LocalDateTime day, Reservation reservation) {
        return day.toString().split("T")[0].equals(reservation.getStartDateTime().toString().split("T")[0]);
    }

    public List<Cleaner> getAvailableCleaners(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return reservationRepository.findAvailableCleaners(startDateTime, endDateTime);
    }

    public String createReservation(NewReservationRequest request) {
        checkIntegrityForCreate(request);
        List<Cleaner> availableCleaners = getAvailableCleaners(request.getStartDateTime(), request.getEndDateTime());
        Map<Vehicle, List<Cleaner>> cleanersByVehicle =
                availableCleaners.stream().collect(Collectors.groupingBy(Cleaner::getVehicle));

        for (var vehicleCleaners : cleanersByVehicle.entrySet()) {
            List<Cleaner> cleaners = vehicleCleaners.getValue();
            if (isEnoughCleaners(request, cleaners)) {
                List<Cleaner> selectedCleaners = selectRandomCleaners(cleaners, request.getCleanerCount());
                reservationRepository.save(new Reservation(
                        request.getStartDateTime(),
                        request.getDuration(),
                        selectedCleaners));
                return "Reservation successfully created!";
            }
        }
        return "Reservation is not available!";
    }

    private void checkIntegrity(BaseRequest request) {
        if (getTimeFromLocalDateTime(request.getStartDateTime()).isBefore(LocalTime.parse("08:00")))
            throw new CleaningShiftBefore8Exception();
        else if (getTimeFromLocalDateTime(request.getEndDateTime()).isAfter(LocalTime.parse("22:00")))
            throw new CleaningShiftAfter22Exception();
        else if (isFriday(request.getStartDateTime()))
            throw new FridayWorkNotAllowedException();
    }

    private void checkIntegrityForCreate(NewReservationRequest request) {
        checkIntegrity(request);
        if (request.getDuration() != 2 && request.getDuration() != 4)
            throw new IllegalCleaningDurationException();
        else if (request.getCleanerCount() < 1 || request.getCleanerCount() > 3)
            throw new IllegalNumberOfCleanersException();
    }

    private boolean isEnoughCleaners(NewReservationRequest request, List<Cleaner> availableCleaners) {
        return availableCleaners.size() >= request.getCleanerCount();
    }

    private List<Cleaner> selectRandomCleaners(List<Cleaner> availableCleaners, int requiredCleanerCount) {
        Collections.shuffle(availableCleaners);
        return availableCleaners.subList(0, requiredCleanerCount-1);
    }

    public String updateReservation(Long reservationId, UpdateReservationRequest request) {
        checkIntegrity(request);
        try {
            Reservation reservation = reservationRepository.getById(reservationId);
            reservation.setStartDateTime(request.getStartDateTime());
            reservation.setEndDateTime(request.getEndDateTime());
            reservationRepository.save(reservation);
            return "Reservation successfully updated!";
        }
        catch (EntityNotFoundException e){
            return "There is no such reservation!";
        }
    }
}
