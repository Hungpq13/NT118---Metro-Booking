package com.example.nt118project.AdminSystem;

public class MetroTrip {
    private String tripName;
    private String status; // "Di chuyển" hoặc "Tạm hoãn"
    private String id;
    private String reason;

    public MetroTrip(String id,String tripName, String status, String reason) {
        this.tripName = tripName;
        this.status = status;
        this.id = id;
        this.reason = reason;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

