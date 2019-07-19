package com.neprofinishedgood.utils;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private static boolean isConnected = true;

    public static boolean isInternetConnected(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

            return isConnected;
        } catch (Exception e) {
            return isConnected;
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ComponentName comp = new ComponentName(context.getPackageName(), NetworkHandleService.class.getName());
        intent.putExtra("isNetworkConnected", isInternetConnected(context));
        context.startService((intent.setComponent(comp)));
    }
}