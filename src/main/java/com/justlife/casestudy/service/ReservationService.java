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
                // Reservation day ile ayni gun mu
                if (isDateSame(day, reservation)) {
                    LocalTime timeOfReservation = getTimeFromLocalDateTime(reservation.getStartDateTime());
                    // intervalStartla reservationStartin arasindaki farki bulup (en az 2 saat) 08:00-10:00 gibi ekleyecegiz available time stringine responsetaki
                    // interval starti reservationEnd + 30 dk olarak guncelle
                    if (isDurationAvailable(timeOfReservation, intervalStart)) {
                        availableTimesOfCleaner += getAvailableTimeAsString(intervalStart, timeOfReservation);
                        intervalStart = getTimeFromLocalDateTime(reservation.getEndDateTime()).plusMinutes(30);
                    }
                }
                // listenin son reservationinin endtimei 22.00dan onceyse ve en az 2 saat bosluk varsa
            if (isDurationAvailable(LocalTime.parse("22:00"), intervalStart))
                availableTimesOfCleaner += getAvailableTimeAsString(intervalStart, LocalTime.parse("22:00"));
            }
            if (availableTimesOfCleaner.equals(""))
                return new AvailabilityCheckResponseForDay(cleaner.getName(), "08:00-22:00");
            return new AvailabilityCheckResponseForDay(cleaner.getName(), availableTimesOfCleaner);
        }
    }

    private boolean isDurationAvailable(LocalTime timeOfReservation, LocalTime intervalStart) {
        long minutes = Duration.between(intervalStart, timeOfReservation).toMinutes();
        return minutes > 120;
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
//        List<Vehicle> vehicles = vehicleService.findAll();
//        // Reservation tablosundan cakisan reservationlari getir
//        List<Cleaner> availableCleaners = new ArrayList<>();
//        for (Vehicle vehicle : vehicles){
//            List<Cleaner> cleaners = vehicle.getCleaners();
//            for (Cleaner cleaner : cleaners){
//                boolean isAvailableCleaner = true;
//                for (Reservation reservation : cleaner.getReservation()){
//                    if (!isAvailableInterval(startDateTime, endDateTime.plusMinutes(30), reservation)) {
//                        isAvailableCleaner = false;
//                        break;
//                    }
//                }
//                if (isAvailableCleaner)
//                    availableCleaners.add(cleaner);
////                if (isEnoughCleaners(request, availableCleaners))
////                    return availableCleaners;
//            }
////            availableCleaners.clear();
//        }
//        return availableCleaners;
        List<Cleaner> result = reservationRepository.findAvailableCleaners(startDateTime, endDateTime);
        System.out.println(result.size() == 1);
        System.out.println(result.get(0).getName().equals("can"));
        return result;
    }

    // 13.00 - 15.00 eski
    // 17.00 - 19.00 yeni
    // 14.00 - 16.00
    private boolean isAvailableInterval(LocalDateTime start, LocalDateTime end, Reservation reservation) {
//        return (start.isAfter(reservation.getStartDateTime()) && end.isAfter(reservation.getEndDateTime()))
//                ||
//                (start.isBefore(reservation.getStartDateTime()) && end.isBefore(reservation.getEndDateTime()));
        return (!start.isAfter(reservation.getStartDateTime()) && start.isBefore(reservation.getEndDateTime()));
    }

    public String createReservation(NewReservationRequest request) {
        checkIntegrity(request);
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
        // 08.00 or 22.00, Duration 2 or 4, CleanerCount 1,2 or 3, Friday
        if (getTimeFromLocalDateTime(request.getStartDateTime()).isBefore(LocalTime.parse("08:00")))
            throw new CleaningShiftBefore8Exception();
        else if (getTimeFromLocalDateTime(request.getEndDateTime()).isAfter(LocalTime.parse("22:00")))
            throw new CleaningShiftAfter22Exception();
        else if (isFriday(request.getStartDateTime()))
            throw new FridayWorkNotAllowedException();
    }

    private void checkIntegrityForCreate(NewReservationRequest request) {
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
        Reservation reservation = reservationRepository.getById(reservationId);
        if (!reservation.equals(null)) {
            reservation.setStartDateTime(request.getStartDateTime());
            reservation.setEndDateTime(request.getEndDateTime());
            reservationRepository.save(reservation);
            return "Reservation successfully updated!";
        }
        return "There is no such reservation!";
    }
}
