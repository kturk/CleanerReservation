package com.justlife.casestudy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Vehicle extends BaseEntity{

    @Column(unique = true)
    @NotBlank
    private String licensePlate;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "vehicle")
    private Set<Cleaner> cleaners;
}
