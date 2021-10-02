package com.justlife.casestudy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class CleaningInterval extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "cleaner_id", nullable = false)
    @NotNull
    private Cleaner cleaner;

    @NotNull(message = "Start time cannot be null!")
    @Column
    private Long startTime;

    @NotNull(message = "End time cannot be null!")
    @Column
    private Long endTime;
}
