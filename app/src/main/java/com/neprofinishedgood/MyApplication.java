package com.neprofinishedgood;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class MyApplication extends Application {
    public static SharedPreferences sharedPreferences;
    static String SHARED_PREF = "SHARED_PREF";
    public static SharedPreferences.Editor editor;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
}
