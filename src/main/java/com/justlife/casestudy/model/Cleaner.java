package com.justlife.casestudy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Cleaner extends BaseEntity{

    @Column
    private String name;

//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cleaner")
//    @OrderBy("startTime asc")
//    private List<CleaningInterval> cleaningTimes;

//    @ManyToOne
//    @JoinColumn(name = "reservation_id", nullable = false)
    @ManyToMany(mappedBy = "cleaners", cascade = { CascadeType.ALL })
    @OrderBy("startDateTime asc")
    private List<Reservation> reservation;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    @NotNull
    private Vehicle vehicle;

}
