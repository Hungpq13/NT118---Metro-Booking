package com.example.nt118project.bottomnav;

public class Notification {
    private int iconResId;
    private String title;
    private String message;

    public Notification(int iconResId, String title, String message) {
        this.iconResId = iconResId;
        this.title = title;
        this.message = message;
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
}
