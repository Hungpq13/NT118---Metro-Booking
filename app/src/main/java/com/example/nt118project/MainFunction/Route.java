package com.example.nt118project.MainFunction;

public class Route {
    private String StationNameStarting;
    private String StationNameDestination;
    private String time;
    private String StationStatus;

    public Route(String StationNameStarting, String StationNameDestination, String time, String StationStatus) {
        this.StationNameStarting = StationNameStarting;
        this.StationNameDestination = StationNameDestination;
        this.time = time;
        this.StationStatus = StationStatus;
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
}
