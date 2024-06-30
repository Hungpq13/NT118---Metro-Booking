package com.example.nt118project.Auth;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceHelper {
    private static final String PREF_NAME = "isLogging";
    private static final String PREF_KEY_ROLE_ID = "roleID";
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private Context mContext;

    public SharedPreferenceHelper(Context context) {
        this.mContext = context;
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mPref.edit();
    }
    public void setLogging(boolean isLogging) {
        mEditor.putBoolean("isLogging", isLogging);
        mEditor.commit();
    }

    public boolean getLogging() {
        return mPref.getBoolean("isLogging", false);
    }

    public void setRoleID(String roleID) {
        mEditor.putString("roleID", roleID);
        mEditor.commit();
    }

    public String getRoleID() {
        return mPref.getString("roleID", "2");
    }

    public void setUserId(String userId) {
        mEditor.putString("userId", userId);
        mEditor.commit();
    }

    public void setUserName(String userName) {
        mEditor.putString("userName", userName);
        mEditor.commit();
    }

    public String getUserName() {
        return mPref.getString("userName", "");
    }

    public void setUserEmail(String userEmail) {
        mEditor.putString("userEmail", userEmail);
        mEditor.commit();
    }

    public String getUserEmail() {
        return mPref.getString("userEmail", "");
    }

    public void setUserPhone(String userPhone) {
        mEditor.putString("userPhone", userPhone);
        mEditor.commit();
    }

    public String getUserPhone() {
        return mPref.getString("userPhone", "");
    }

    public String getUserId() {return mPref.getString("userId", "");}

    public void clear() {
        mEditor.clear();
        mEditor.commit();
    }
}
