package com.smartpark.api.model;

public class ParkingLot {
    private String lotId;
    private String location;
    private int capacity;
    private int occupied;

    public ParkingLot() {}

    public ParkingLot(String lotId, String location, int capacity) {
        this.lotId = lotId;
        this.location = location;
        this.capacity = capacity;
        this.occupied = 0;
    }

    public String getLotId() { return lotId; }
    public void setLotId(String lotId) { this.lotId = lotId; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getOccupied() { return occupied; }
    public void setOccupied(int occupied) { this.occupied = occupied; }

    public int getAvailable() { return Math.max(0, capacity - occupied); }
}
