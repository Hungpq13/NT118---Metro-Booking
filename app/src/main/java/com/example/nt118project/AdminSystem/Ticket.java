package com.example.nt118project.AdminSystem;

public class Ticket {
    private String buyerName;
    private String time;
    private String turn;
    private String ticketid;

    public Ticket(String buyerName, String time, String turn, String ticketid) {
        this.buyerName = buyerName;
        this.time = time;
        this.turn = turn;
        this.ticketid = ticketid;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String gettime() {
        return time;
    }

    public void settime(String time) {
        this.time = time;
    }

    public String getturn() {
        return turn;
    }

    public void setturn(String turn) {
        this.turn = turn;
    }

    public String getticketid() {
        return ticketid;
    }

    public void setticketid(String ticketid) {
        this.ticketid = ticketid;
    }
}
