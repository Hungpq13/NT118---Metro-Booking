package com.example.nt118project.BookingHistory;

import java.sql.Time;

public class Item {
    int image;
    String ticketid;
    String turn;
    String time;
    String date;

    public Item(int image, String ticketid, String turn , String time, String date) {
        this.image = image;
        this.ticketid = ticketid;
        this.turn = turn;
        this.time = time;
        this.date = date;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTicketid() {
        return ticketid;
    }

    public void setTicketid(String ticketid) {
        this.ticketid = ticketid;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
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

