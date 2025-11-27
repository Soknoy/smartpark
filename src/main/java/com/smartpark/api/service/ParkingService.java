package com.smartpark.api.service;

import com.smartpark.api.dto.*;
import com.smartpark.api.exception.BadRequestException;
import com.smartpark.api.exception.NotFoundException;
import com.smartpark.api.model.ParkingLot;
import com.smartpark.api.model.Vehicle;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * Singleton-style service that stores data in ArrayLists (in-memory).
 * Vehicle.parkedLotId acts as the business foreign key.
 */
@Service
public class ParkingService {

    private static ParkingService instance;

    private final List<ParkingLot> parkingLots = new ArrayList<>();
    private final List<Vehicle> vehicles = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();

    public ParkingService() {
        instance = this;
    }

    // static accessor used by preloader (simple approach)
    public static ParkingService getInstance() {
        return instance;
    }

    // LOT operations
    public ParkingLotResponse registerLot(ParkingLot lot) {
        if (lot.getLotId() == null || lot.getLotId().trim().isEmpty()) {
            throw new BadRequestException("lotId required");
        }
        synchronized (parkingLots) {
            Optional<ParkingLot> existing = parkingLots.stream()
                    .filter(l -> l.getLotId().equals(lot.getLotId()))
                    .findFirst();
            if (existing.isPresent()) {
                throw new BadRequestException("Lot already exists: " + lot.getLotId());
            }
            lot.setOccupied(0);
            parkingLots.add(lot);
        }
        return new ParkingLotResponse(lot.getLotId(), lot.getLocation(), lot.getCapacity(), lot.getOccupied());
    }

    public List<ParkingLotResponse> listLots() {
        synchronized (parkingLots) {
            return parkingLots.stream()
                    .map(l -> new ParkingLotResponse(l.getLotId(), l.getLocation(), l.getCapacity(), l.getOccupied()))
                    .collect(Collectors.toList());
        }
    }

    public ParkingLotResponse getLot(String lotId) {
        ParkingLot lot = findLotEntity(lotId);
        return new ParkingLotResponse(lot.getLotId(), lot.getLocation(), lot.getCapacity(), lot.getOccupied());
    }

    // VEHICLE operations
    public VehicleResponse registerVehicle(Vehicle v) {
        if (v.getLicensePlate() == null || v.getLicensePlate().trim().isEmpty()) {
            throw new BadRequestException("licensePlate required");
        }
        synchronized (vehicles) {
            Optional<Vehicle> existing = vehicles.stream()
                    .filter(x -> x.getLicensePlate().equals(v.getLicensePlate()))
                    .findFirst();
            if (existing.isPresent()) {
                throw new BadRequestException("Vehicle already registered: " + v.getLicensePlate());
            }
            v.setParkedLotId(null);
            vehicles.add(v);
        }
        return new VehicleResponse(v.getLicensePlate(), v.getType().name(), v.getOwnerName(), null);
    }

    public List<VehicleResponse> listVehicles() {
        synchronized (vehicles) {
            return vehicles.stream()
                    .map(v -> new VehicleResponse(v.getLicensePlate(), v.getType().name(), v.getOwnerName(), v.getParkedLotId()))
                    .collect(Collectors.toList());
        }
    }

    // Business operations: check-in / check-out using vehicle.parkedLotId as the FK
    public void checkIn(String licensePlate, String lotId) {
        lock.lock();
        try {
            Vehicle v = findVehicleEntity(licensePlate);
            if (v.isParked()) {
                throw new BadRequestException("Vehicle already parked in lot: " + v.getParkedLotId());
            }
            ParkingLot lot = findLotEntity(lotId);
            if (lot.getOccupied() >= lot.getCapacity()) {
                throw new BadRequestException("Parking lot is full: " + lotId);
            }
            // set business FK on vehicle then update lot
            v.setParkedLotId(lotId);
            lot.setOccupied(lot.getOccupied() + 1);
        } finally {
            lock.unlock();
        }
    }

    public void checkOut(String licensePlate) {
        lock.lock();
        try {
            Vehicle v = findVehicleEntity(licensePlate);
            if (!v.isParked()) {
                throw new BadRequestException("Vehicle is not parked: " + licensePlate);
            }
            String parkedLotId = v.getParkedLotId();
            ParkingLot lot = findLotEntity(parkedLotId);
            // clear vehicle FK then decrement
            v.setParkedLotId(null);
            lot.setOccupied(Math.max(0, lot.getOccupied() - 1));
        } finally {
            lock.unlock();
        }
    }

    public List<VehicleResponse> vehiclesInLot(String lotId) {
        synchronized (vehicles) {
            return vehicles.stream()
                    .filter(v -> lotId.equals(v.getParkedLotId()))
                    .map(v -> new VehicleResponse(v.getLicensePlate(), v.getType().name(), v.getOwnerName(), v.getParkedLotId()))
                    .collect(Collectors.toList());
        }
    }

    // helpers
    private ParkingLot findLotEntity(String lotId) {
        synchronized (parkingLots) {
            return parkingLots.stream()
                    .filter(l -> l.getLotId().equals(lotId))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("Parking lot not found: " + lotId));
        }
    }

    private Vehicle findVehicleEntity(String licensePlate) {
        synchronized (vehicles) {
            return vehicles.stream()
                    .filter(v -> v.getLicensePlate().equals(licensePlate))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("Vehicle not found: " + licensePlate));
        }
    }
}
