package com.neprofinishedgood.utils;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;


public class NetworkHandleService extends IntentService {

    private Handler handler;

    public NetworkHandleService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Bundle extras = intent.getExtras();
        handler = new Handler();
        boolean isNetworkConnected = extras.getBoolean("isNetworkConnected");
        if (isNetworkConnected) {

        }
    }

}