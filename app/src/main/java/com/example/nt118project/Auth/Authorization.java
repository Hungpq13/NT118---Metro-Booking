package com.example.nt118project.Auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.nt118project.AdminSystem.AdminActivity;
import com.example.nt118project.bottomnav.MenuActivity;

public class Authorization {
    static public void signInWithRole(Integer roleID, Context context) {
        Intent intent;
        switch (roleID) {
            case 1:
                intent = new Intent(context, AdminActivity.class);
                break;
            default:
                intent = new Intent(context, MenuActivity.class);
                break;
        }
        // Add FLAG_ACTIVITY_NEW_TASK flag
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
