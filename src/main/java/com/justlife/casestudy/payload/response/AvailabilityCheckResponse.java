package com.justlife.casestudy.payload.response;

import com.justlife.casestudy.model.Cleaner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityCheckResponse {

    private String name;

    private Long vehicleId;

    public AvailabilityCheckResponse(Cleaner cleaner) {
        this.name = cleaner.getName();
        this.vehicleId = cleaner.getVehicle().getId();
    }
}
