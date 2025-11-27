package com.smartpark.api.dto;

import com.smartpark.api.model.VehicleType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class VehicleRequest {
    
    @NotBlank
    @Size(max = 20)
    @Pattern(regexp = "^[A-Za-z0-9-]+$")
    private String licensePlate;

    private VehicleType type;

    @Pattern(regexp = "^[A-Za-z ]+$")
    private String ownerName;

    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

    public VehicleType getType() { return type; }
    public void setType(VehicleType type) { this.type = type; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
}