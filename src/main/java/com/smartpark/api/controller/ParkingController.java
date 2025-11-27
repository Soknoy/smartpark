package com.smartpark.api.controller;

import com.smartpark.api.dto.*;
import com.smartpark.api.service.ParkingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/parking")
@Validated
@Tag(name = "SmartPark (ArrayList) API")
public class ParkingController {

    private final ParkingService service;

    public ParkingController(ParkingService service) {
        this.service = service;
    }

    @Operation(summary = "Register parking lot")
    @PostMapping("/lot")
    public ResponseEntity<ParkingLotResponse> createLot(@Valid @RequestBody ParkingLotRequest req) {
        return ResponseEntity.ok(service.registerLot(new com.smartpark.api.model.ParkingLot(req.getLotId(), req.getLocation(), req.getCapacity())));
    }

    @Operation(summary = "Register vehicle")
    @PostMapping("/vehicle")
    public ResponseEntity<VehicleResponse> createVehicle(@Valid @RequestBody VehicleRequest req) {
        com.smartpark.api.model.Vehicle v = new com.smartpark.api.model.Vehicle(req.getLicensePlate(), req.getType(), req.getOwnerName());
        return ResponseEntity.ok(service.registerVehicle(v));
    }

    @Operation(summary = "Check-in vehicle to lot")
    @PostMapping("/checkin/{lotId}/{licensePlate}")
    public ResponseEntity<String> checkIn(@PathVariable String lotId, @PathVariable String licensePlate) {
        service.checkIn(licensePlate, lotId);
        return ResponseEntity.ok("Checked in");
    }

    @Operation(summary = "Check-out vehicle")
    @PostMapping("/checkout/{licensePlate}")
    public ResponseEntity<String> checkOut(@PathVariable String licensePlate) {
        service.checkOut(licensePlate);
        return ResponseEntity.ok("Checked out");
    }

    @Operation(summary = "View occupancy of a lot")
    @GetMapping("/occupancy/{lotId}")
    public ResponseEntity<ParkingLotResponse> occupancy(@PathVariable String lotId) {
        return ResponseEntity.ok(service.getLot(lotId));
    }

    @Operation(summary = "List vehicles currently parked in a lot")
    @GetMapping("/vehicles/{lotId}")
    public ResponseEntity<List<VehicleResponse>> vehicles(@PathVariable String lotId) {
        return ResponseEntity.ok(service.vehiclesInLot(lotId));
    }

    @Operation(summary = "List all lots")
    @GetMapping("/lots")
    public ResponseEntity<List<ParkingLotResponse>> lots() {
        return ResponseEntity.ok(service.listLots());
    }

    @Operation(summary = "List all vehicles")
    @GetMapping("/vehicles")
    public ResponseEntity<List<VehicleResponse>> allVehicles() {
        return ResponseEntity.ok(service.listVehicles());
    }
}
