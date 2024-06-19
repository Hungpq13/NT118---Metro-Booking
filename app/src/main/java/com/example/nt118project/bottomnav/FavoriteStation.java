package com.example.nt118project.bottomnav;

public class FavoriteStation {
    private String id;
    private String name;

    public FavoriteStation(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name; // Return the station name for display in Spinner
    }
}
