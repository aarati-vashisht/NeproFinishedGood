package com.neprofinishedgood.utils;

import com.neprofinishedgood.MyApplication;

import static com.neprofinishedgood.MyApplication.editor;

public class SharedPref {
    static String LOGIN_DATA = "LOGIN_DATA";
    private static String MASTER_DATA = "MASTER_DATA";

    public static void saveLoginUSer(String loginData) {
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

    public static void saveMasterData(String jsonData) {
        editor.putString(MASTER_DATA, jsonData);
        editor.apply();
    }

    public static String getMasterData() {
        String data = MyApplication.sharedPreferences.getString(MASTER_DATA, "");
        return data;
    }
}
