package com.smartpark.api.model;

public class Vehicle {
    private String licensePlate;
    private VehicleType type;
    private String ownerName;
    private String parkedLotId;

    public Vehicle() {}

    public Vehicle(String licensePlate, VehicleType type, String ownerName) {
        this.licensePlate = licensePlate;
        this.type = type;
        this.ownerName = ownerName;
        this.parkedLotId = null;
    }

    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

    public VehicleType getType() { return type; }
    public void setType(VehicleType type) { this.type = type; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public String getParkedLotId() { return parkedLotId; }
    public void setParkedLotId(String parkedLotId) { this.parkedLotId = parkedLotId; }

    public boolean isParked() { return parkedLotId != null && !parkedLotId.trim().isEmpty(); }
}
