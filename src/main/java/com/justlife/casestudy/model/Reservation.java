package com.justlife.casestudy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation extends BaseEntity {

    @NotNull(message = "Start time cannot be null!")
    @Column
    private LocalDateTime startDateTime;

    @NotNull(message = "Duration cannot be null!")
    @Column
    private int duration;

    @Column
    private LocalDateTime endDateTime;

//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "reservation")
    @ManyToMany(cascade = {
            CascadeType.ALL
    })
    @JoinTable(
            name = "reservations_cleaners",
            joinColumns = {
                    @JoinColumn(name = "reservation_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "cleaner_id")
            }
    )
    private List<Cleaner> cleaners;

    public Reservation(LocalDateTime startDateTime, int duration, List<Cleaner> cleaners) {
        this.startDateTime = startDateTime;
        this.duration = duration;
        this.endDateTime = startDateTime.plusHours(duration);
        this.cleaners = cleaners;
    }
}