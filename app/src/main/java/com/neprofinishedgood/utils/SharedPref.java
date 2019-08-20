package com.neprofinishedgood.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neprofinishedgood.MyApplication;
import com.neprofinishedgood.pickandload.model.LoadingPlanList;
import com.neprofinishedgood.pickandload.model.ScanLoadingPlanList;
import com.neprofinishedgood.productionjournal.model.PickingListDatum;
import com.neprofinishedgood.updatequantity.model.UpdateQtyInput;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.neprofinishedgood.MyApplication.editor;
import static com.neprofinishedgood.MyApplication.editor_master;

public class SharedPref {
    static String LOGIN_DATA = "LOGIN_DATA";
    private static String MASTER_DATA = "MASTER_DATA";
    private static String MOVE_DATA = "MOVE_DATA";
    private static String RAF_DATA = "MOVE_DATA";
    private static String REJECT_DATA = "REJECT_DATA";
    private static String COMPLETE_REJECT_DATA = "COMPLETE_REJECT_DATA";
    private static String ASSIGNED_UNASSIGNED_DATA = "ASSIGNED_UNASSIGNED_DATA";
    private static String TRANSFER_DATA = "TRANSFER_DATA";
    private static String RECEIVE_DATA = "RECEIVE_DATA";
    private static String LOADING_PLAN_LIST = "LOADING_PLAN_LIST";
    private static String LOADING_PLAN_DETAIL_LIST = "LOADING_PLAN_DETAIL_LIST";
    private static String UPDATE_DATA = "UPDATE_DATA";
    private static String PICKING_LIST_DATA = "PICKING_LIST_DATA";


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
        editor_master.putString(MASTER_DATA, jsonData);
        editor_master.apply();
    }

    public static String getMasterData() {
        String data = MyApplication.sharedPreferencesMaster.getString(MASTER_DATA, "");
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

    public static void saveCompleteRejectData(String jsonData) {
        editor.putString(COMPLETE_REJECT_DATA, jsonData);
        editor.apply();
    }

    public static String getCompleteRejectData() {
        String data = MyApplication.sharedPreferences.getString(COMPLETE_REJECT_DATA, "");
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

    public static void saveLoadinGplanList(String saveLoadingPlanListData) {
        editor.putString(LOADING_PLAN_LIST, saveLoadingPlanListData);
        editor.apply();
    }

    public static List<ScanLoadingPlanList> getLoadinGplanList() {
        List<ScanLoadingPlanList> scanLoadingPlanList = new ArrayList<>();
        String data = MyApplication.sharedPreferences.getString(LOADING_PLAN_LIST, "");
        if (!data.equals("")) {
            Type type = new TypeToken<ArrayList<ScanLoadingPlanList>>() {
            }.getType();
            scanLoadingPlanList = new Gson().fromJson(data, type);
        }
        return scanLoadingPlanList;
    }

    public static void saveLoadinGplanDetailList(String saveLoadingPlanListData) {
        editor.putString(LOADING_PLAN_DETAIL_LIST, saveLoadingPlanListData);
        editor.apply();
    }

    public static List<LoadingPlanList> getLoadinGplanDetailList() {
        List<LoadingPlanList> loadingPlanLists = new ArrayList<>();
        String data = MyApplication.sharedPreferences.getString(LOADING_PLAN_DETAIL_LIST, "");
        if (!data.equals("")) {
            Type type = new TypeToken<ArrayList<LoadingPlanList>>() {
            }.getType();
            loadingPlanLists = new Gson().fromJson(data, type);
        }
        return loadingPlanLists;
    }

    public static void saveUpdateData(String jsonData) {
        editor.putString(UPDATE_DATA, jsonData);
        editor.apply();
    }

    public static ArrayList<UpdateQtyInput> getUpdateData() {
        ArrayList<UpdateQtyInput> updateList = new ArrayList<>();
        Gson gson = new Gson();
        String data = MyApplication.sharedPreferences.getString(UPDATE_DATA, "");
        if (!data.equals("")) {
            Type type = new TypeToken<ArrayList<UpdateQtyInput>>() {
            }.getType();
            updateList = gson.fromJson(data, type);
        }
        return updateList;
    }


    public static void savePickingListData(String jsonData) {
        editor.putString(PICKING_LIST_DATA, jsonData);
        editor.apply();
    }

    public static ArrayList<PickingListDatum> getPickingListData() {
        ArrayList<PickingListDatum> pickingLists = new ArrayList<>();
        Gson gson = new Gson();
        String data = MyApplication.sharedPreferences.getString(PICKING_LIST_DATA, "");
        if (!data.equals("")) {
            Type type = new TypeToken<ArrayList<PickingListDatum>>() {
            }.getType();
            pickingLists = gson.fromJson(data, type);
        }
        return pickingLists;
    }
}
