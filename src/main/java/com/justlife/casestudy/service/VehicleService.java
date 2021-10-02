package com.justlife.casestudy.service;

import com.justlife.casestudy.model.Vehicle;
import com.justlife.casestudy.repository.VehicleRepository;
import org.springframework.stereotype.Service;

@Service
public class VehicleService extends BaseService<Vehicle> {

    private VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository repository) {
        super("Vehicle", repository);
        this.vehicleRepository = repository;
    }
}
