package com.justlife.casestudy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation extends BaseEntity {

    @NotNull(message = "Start time cannot be null!")
    @Column
    private DateTime startTime;

    @NotNull(message = "Duration cannot be null!")
    @Column
    private Duration duration;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "reservation")
    private Set<Cleaner> cleaners;

}