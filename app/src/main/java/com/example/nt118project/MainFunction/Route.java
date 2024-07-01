package com.example.nt118project.MainFunction;

public class Route {
    private String StationNameStarting;
    private String StationNameDestination;
    private String time;
    private String StationStatus;
    private boolean istrue;
    private String delayReason;

    public Route(String StationNameStarting, String StationNameDestination, String time, String StationStatus, boolean istrue, String delayReason) {
        this.StationNameStarting = StationNameStarting;
        this.StationNameDestination = StationNameDestination;
        this.time = time;
        this.StationStatus = StationStatus;
        this.istrue = istrue;
        this.delayReason = delayReason;
    }

    public String getStationNameStarting() {
        return StationNameStarting;
    }

    public String getStationNameDestination() {
        return StationNameDestination;
    }

    public String getTime() {
        return time;
    }

    public String getStationStatus() {
        return StationStatus;
    }

    public boolean Istrue() {
        return istrue;
    }

    // Add getter for delayReason
    public String getDelayReason() {
        return delayReason;
    }
}
