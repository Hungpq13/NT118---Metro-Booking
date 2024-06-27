package com.example.nt118project.AdminSystem;

public class MetroTrip {
    private String tripName;
    private String status; // "Di chuyển" hoặc "Tạm hoãn"

    public MetroTrip(String tripName, String status) {
        this.tripName = tripName;
        this.status = status;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

