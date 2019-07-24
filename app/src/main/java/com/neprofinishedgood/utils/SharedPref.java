package com.neprofinishedgood.utils;

import com.neprofinishedgood.MyApplication;

import static com.neprofinishedgood.MyApplication.editor;

public class SharedPref {
    static String LOGIN_DATA = "LOGIN_DATA";
    private static String MASTER_DATA = "MASTER_DATA";
    private static String MOVE_DATA = "MOVE_DATA";
    private static String RAF_DATA = "MOVE_DATA";
    private static String REJECT_DATA = "MOVE_DATA";
    private static String ASSIGNED_UNASSIGNED_DATA = "ASSIGNED_UNASSIGNED_DATA";
    private static String TRANSFER_DATA = "TRANSFER_DATA";
    private static String RECEIVE_DATA = "RECEIVE_DATA";


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

    public static void saveMoveData(String jsonData) {
        editor.putString(MOVE_DATA, jsonData);
        editor.apply();
    }

    public static String getMoveData() {
        String data = MyApplication.sharedPreferences.getString(MOVE_DATA, "");
        return data;
    }

    public static void saveRafData(String jsonData) {
        editor.putString(RAF_DATA, jsonData);
        editor.apply();
    }

    public static String getRafData() {
        String data = MyApplication.sharedPreferences.getString(RAF_DATA, "");
        return data;
    }

    public static void saveRejectData(String jsonData) {
        editor.putString(REJECT_DATA, jsonData);
        editor.apply();
    }

    public static String getRejectData() {
        String data = MyApplication.sharedPreferences.getString(REJECT_DATA, "");
        return data;
    }

    public static void saveAssignedUnAssignedData(String jsonData) {
        editor.putString(ASSIGNED_UNASSIGNED_DATA, jsonData);
        editor.apply();
    }

    public static String getAssignedUnAssignedData() {
        String data = MyApplication.sharedPreferences.getString(ASSIGNED_UNASSIGNED_DATA, "");
        return data;
    }

    public static void saveTransferData(String jsonData) {
        editor.putString(TRANSFER_DATA, jsonData);
        editor.apply();
    }

    public static String getTransferData() {
        String data = MyApplication.sharedPreferences.getString(TRANSFER_DATA, "");
        return data;
    }

    public static void saveReceiveData(String jsonData) {
        editor.putString(RECEIVE_DATA, jsonData);
        editor.apply();
    }

    public static String getReceiveData() {
        String data = MyApplication.sharedPreferences.getString(RECEIVE_DATA, "");
        return data;
    }
}
