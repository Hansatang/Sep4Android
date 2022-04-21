package com.example.sep4android;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    static final String PREF_ID = "Id";
    static final String PREF_USER_NAME = "Username";
    static final String PREF_LoggedIn = "Status";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return ctx.getSharedPreferences("SavedUser", Context.MODE_PRIVATE);
    }

    public static void setUser(Context ctx, String userName, int id, boolean status) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt(PREF_ID, id);
        editor.putString(PREF_USER_NAME, userName);
        editor.putBoolean(PREF_LoggedIn, status);
        editor.commit();
    }

    public static String getUser(Context ctx) {
        return (getSharedPreferences(ctx).getString(PREF_USER_NAME, "") + " "
                + getSharedPreferences(ctx).getInt(PREF_ID, 0) + " " +
                getSharedPreferences(ctx).getBoolean(PREF_LoggedIn, false));
    }

    public static void logOutUser(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt(PREF_ID, 0);
        editor.putString(PREF_USER_NAME, "");
        editor.putBoolean(PREF_LoggedIn, false);
        editor.commit();
    }

    public static boolean getStatus(Context ctx) {
        return getSharedPreferences(ctx).getBoolean(PREF_LoggedIn, false);
    }

}
