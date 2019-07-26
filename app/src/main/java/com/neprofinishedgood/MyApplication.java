package com.neprofinishedgood;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.base.model.MasterData;
import com.neprofinishedgood.utils.NetworkChangeReceiver;
import com.neprofinishedgood.utils.SharedPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyApplication extends Application {
    public static SharedPreferences sharedPreferences, sharedPreferencesMaster;
    static String SHARED_PREF = "SHARED_PREF";
    static String SHARED_PREF_MASTER = "SHARED_PREF_MASTER";
    public static SharedPreferences.Editor editor, editor_master;
    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sharedPreferences = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        sharedPreferencesMaster = getSharedPreferences(SHARED_PREF_MASTER, Context.MODE_PRIVATE);
        editor_master = sharedPreferencesMaster.edit();

        if (NetworkChangeReceiver.isInternetConnected(getApplicationContext())) {
            callMasterData();
        }
    }

    public void callMasterData() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<MasterData> call = apiInterface.masterData();
        call.enqueue(new Callback<MasterData>() {
            @Override
            public void onResponse(Call<MasterData> call, Response<MasterData> response) {
                getMasterData(response);
            }

            @Override
            public void onFailure(Call<MasterData> call, Throwable t) {
                getMasterData(null);

            }
        });
    }

    private void getMasterData(Response<MasterData> response) {
        Gson gson = new Gson();
        String jsonData = gson.toJson(response.body());
        SharedPref.saveMasterData(jsonData);
    }
}
