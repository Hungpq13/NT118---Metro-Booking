package com.example.nt118project.Personal;

public class Ticket {
    private String ticketid;
    private String turn;
    private String time;
    private String date;

    public Ticket(String ticketid, String turn, String time, String date) {
        this.ticketid = ticketid;
        this.turn = turn;
        this.time = time;
        this.date = date;
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
