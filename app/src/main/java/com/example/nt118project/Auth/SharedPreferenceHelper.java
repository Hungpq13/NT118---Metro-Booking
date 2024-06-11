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

    public void setRoleID(int roleID) {
        mEditor.putInt("roleID", roleID);
        mEditor.commit();
    }

    public int getRoleID() {
        return mPref.getInt("roleID", 0);
    }

    public void clear() {
        mEditor.clear();
        mEditor.commit();
    }
}
