package com.example.nt118project.BookingHistory;

import java.sql.Time;

public class Item {
    int image;
    String name;
    String position_from;
    String position_to;
    String time;
    String date;

    public Item(int image, String name, String position_from, String position_to, String time, String date) {
        this.image = image;
        this.name = name;
        this.position_from = position_from;
        this.position_to = position_to;
        this.time = time;
        this.date = date;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition_from() {
        return position_from;
    }

    public void setPosition_from(String position_from) {
        this.position_from = position_from;
    }

    public String getPosition_to() {
        return position_to;
    }

    public void setPosition_to(String position_to) {
        this.position_to = position_to;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
