package com.justlife.casestudy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "reservation")
    private List<Cleaner> cleaners;

}