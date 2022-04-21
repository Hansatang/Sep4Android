package com.example.sep4android;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    static final String PREF_ID= "Id";
    static final String PREF_USER_NAME= "Username";
    static final String PREF_LoggedIn= "Status";

    public SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public  void setUser(Context ctx, String userName,String id, boolean status)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_ID, id);
        editor.putString(PREF_USER_NAME, userName);
        editor.putBoolean(PREF_LoggedIn, status);
        editor.commit();
    }

    public  String getUserName(Context ctx)
    {

        return (getSharedPreferences(ctx).getString(PREF_USER_NAME, "")+" "
                +getSharedPreferences(ctx).getString(PREF_ID, "") + " "+
                getSharedPreferences(ctx).getBoolean(PREF_LoggedIn, false ));
    }

    public  void logOutUser(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_ID, null);
        editor.putString(PREF_USER_NAME, null);
        editor.putBoolean(PREF_USER_NAME, false);
        editor.commit();
    }

}
