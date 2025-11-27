package com.smartpark.api.service;

import org.springframework.stereotype.Component;

import com.smartpark.api.model.ParkingLot;
import com.smartpark.api.model.Vehicle;
import com.smartpark.api.model.VehicleType;

import jakarta.annotation.PostConstruct;


/**
 * Preloads data into the in-memory lists at startup.
 */
@Component 
public class DataPreloader {

    @PostConstruct
    public void load() {
        ParkingService ps = ParkingService.getInstance();
        // only add if empty
        if (ps.listLots().isEmpty()) {
            ps.registerLot(new ParkingLot("LOT-A", "Makati", 5));
            ps.registerLot(new ParkingLot("LOT-B", "Taguig", 2));
        }
        if (ps.listVehicles().isEmpty()) {
            ps.registerVehicle(new Vehicle("ABC-123", VehicleType.CAR, "Peter Parker"));
            ps.registerVehicle(new Vehicle("MOTO-1", VehicleType.MOTORCYCLE, "Bruce Wayne"));
        }
    }
}
