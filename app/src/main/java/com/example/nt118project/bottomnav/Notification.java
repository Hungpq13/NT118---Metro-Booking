package com.example.nt118project.bottomnav;

public class Notification {
    private int iconResId;
    private String title;
    private String message;
    private String date;
    private String time;

    public Notification(int iconResId, String title, String message, String date, String time) {
        this.iconResId = iconResId;
        this.title = title;
        this.message = message;
        this.date = date;
        this.time = time;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
