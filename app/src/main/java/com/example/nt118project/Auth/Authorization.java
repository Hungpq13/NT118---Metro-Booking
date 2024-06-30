package com.example.nt118project.Auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.nt118project.AdminSystem.AdminActivity;
import com.example.nt118project.bottomnav.MenuActivity;

public class Authorization {
    static public void signInWithRole(String roleID, Context context) {
        Intent intent;
        if (roleID.equals("1")) {
            intent = new Intent(context, AdminActivity.class);
        } else {
            intent = new Intent(context, MenuActivity.class);
        }
        // Add FLAG_ACTIVITY_NEW_TASK flag
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
