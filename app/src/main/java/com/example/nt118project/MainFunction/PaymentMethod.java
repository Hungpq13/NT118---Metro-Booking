package com.example.nt118project.MainFunction;

public class PaymentMethod {
    private String name;
    private int iconResId;

    public PaymentMethod(String name, int iconResId) {
        this.name = name;
        this.iconResId = iconResId;
    }

    public String getName() {
        return name;
    }

    public int getIconResId() {
        return iconResId;
    }
}
