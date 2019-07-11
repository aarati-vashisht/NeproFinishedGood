package com.neprofinishedgood.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.neprofinishedgood.MyApplication;

import static com.neprofinishedgood.MyApplication.editor;

public class SharedPref {
    static SharedPreferences sharedPreferences;
    static String LOGIN_DATA = "LOGIN_DATA";

    public static void saveLoginUSer(Context context, String loginData) {
        editor.putString(LOGIN_DATA, loginData);
        editor.apply();
    }

    public static void clearPrefs() {
        if (editor != null) {
            editor.clear().apply();
        }
    }

    public static String getLoginUser() {
        String data = MyApplication.sharedPreferences.getString(LOGIN_DATA, "");
        return data;
    }
}
