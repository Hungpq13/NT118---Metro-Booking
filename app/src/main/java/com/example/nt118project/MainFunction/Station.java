package com.example.nt118project.MainFunction;

public class Station {
    private String StationName;
    private String StationID;
    private String StationDocumentID;
    public Station(String StationName, String StationID, String StationDocumentID) {
        this.StationName = StationName;
        this.StationID = StationID;
        this.StationDocumentID = StationDocumentID;
    }
    public String getStationName() {
        return StationName;
    }
    public String getStationID() {return StationID;}
    public String getStationDocumentID() {
        return StationDocumentID;
    }
}
