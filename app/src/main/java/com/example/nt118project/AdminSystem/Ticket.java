package com.example.nt118project.AdminSystem;

public class Ticket {
    private String buyerName;
    private String purchaseTime;
    private String ticketType;
    private String ticketCode;

    public Ticket(String buyerName, String purchaseTime, String ticketType, String ticketCode) {
        this.buyerName = buyerName;
        this.purchaseTime = purchaseTime;
        this.ticketType = ticketType;
        this.ticketCode = ticketCode;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }
}
