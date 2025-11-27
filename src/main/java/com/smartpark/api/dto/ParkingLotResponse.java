package com.smartpark.api.dto;

public class ParkingLotResponse {
    private String lotId;
    private String location;
    private int capacity;
    private int occupied;
    private int available;

    public ParkingLotResponse(String lotId, String location, int capacity, int occupied) {
        this.lotId = lotId;
        this.location = location;
        this.capacity = capacity;
        this.occupied = occupied;
        this.available = Math.max(0, capacity - occupied);
    }

    public String getLotId() { return lotId; }
    public String getLocation() { return location; }
    public int getCapacity() { return capacity; }
    public int getOccupied() { return occupied; }
    public int getAvailable() { return available; }
}
