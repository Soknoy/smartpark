package com.smartpark.api.dto;

public class VehicleResponse {
    private String licensePlate;
    private String type;
    private String ownerName;
    private String parkedLotId;

    public VehicleResponse(String licensePlate, String type, String ownerName, String parkedLotId) {
        this.licensePlate = licensePlate;
        this.type = type;
        this.ownerName = ownerName;
        this.parkedLotId = parkedLotId;
    }

    public String getLicensePlate() { return licensePlate; }
    public String getType() { return type; }
    public String getOwnerName() { return ownerName; }
    public String getParkedLotId() { return parkedLotId; }
}
