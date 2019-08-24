package com.neprofinishedgood.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.neprofinishedgood.R;
import com.neprofinishedgood.base.model.LocationList;
import com.neprofinishedgood.base.model.MasterData;
import com.neprofinishedgood.base.model.UniversalSpinner;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.login.LoginActivity;
import com.neprofinishedgood.login.model.LoginResponse;
import com.neprofinishedgood.utils.NetworkChangeReceiver;
import com.neprofinishedgood.utils.NetworkHandleService;
import com.neprofinishedgood.utils.SharedPref;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity implements IBaseInterface {
    String title;
    TextView textViewTitle;
    Gson gson = new Gson();
    private KProgressHUD kProgressHUD;
    public String userId;
    public Animation fadeOut;
    public Animation fadeIn;
    public List<UniversalSpinner> aisleList = new ArrayList<>();
    public List<UniversalSpinner> rackList = new ArrayList<>();
    public List<UniversalSpinner> binList = new ArrayList<>();
    public List<UniversalSpinner> reasonList = new ArrayList<>();
    public List<UniversalSpinner> fltList = new ArrayList<>();
    public List<UniversalSpinner> warehouseList = new ArrayList<>();
    public List<LocationList> locationList = new ArrayList<>();

    AlertDialog.Builder builder;

    boolean isOffline = false;

    static BaseActivity instance;

    public static BaseActivity getInstance() {
        return instance;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        setActionBarData();
        instance = this;
        gson = new Gson();
        LoginResponse loginResponse = gson.fromJson(SharedPref.getLoginUser(), LoginResponse.class);
        if (loginResponse != null) {
            userId = loginResponse.getUserLoginResponse().get(0).getUserId();
        } else {
            userId = "";
        }
        fadeOut = AnimationUtils.loadAnimation(BaseActivity.this, R.anim.animate_fade_out);
        fadeIn = AnimationUtils.loadAnimation(BaseActivity.this, R.anim.animate_fade_in);
        getMAsterData(gson);


    }

    private void getMAsterData(Gson gson) {
        MasterData masterData = gson.fromJson(SharedPref.getMasterData(), MasterData.class);
        if (masterData != null) {
            if (masterData.getStatus().equals(getString(R.string.success))) {
                aisleList = masterData.getAisleList();
                aisleList.add(0, new UniversalSpinner("Select Aisle", "000"));
                rackList = masterData.getRackList();
                rackList.add(0, new UniversalSpinner("Select Rack", "000"));
                binList = masterData.getBinList();
                binList.add(0, new UniversalSpinner("Select Bin", "000"));
                reasonList = masterData.getReasonList();
                reasonList.add(0, new UniversalSpinner("Select Reason", "000"));
                fltList = masterData.getFLTList();
                fltList.add(0, new UniversalSpinner("Select FLT", "000"));
                warehouseList = masterData.getWareHouseList();
                warehouseList.add(0, new UniversalSpinner("Select WareHouse", "000"));
                locationList = masterData.getLocationList();
            }
        } else {
            aisleList = new ArrayList<>();
            rackList = new ArrayList<>();
            binList = new ArrayList<>();
            reasonList = new ArrayList<>();
            fltList = new ArrayList<>();
            warehouseList = new ArrayList<>();
            locationList = new ArrayList<>();
        }

    }

    private void setActionBarData() {
        LayoutInflater mInflater = LayoutInflater.from(this);
        View view = mInflater.inflate(R.layout.action_bar_layout, null);
        textViewTitle = view.findViewById(R.id.textViewTitle);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(view);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

    }

    @Override
    public void setTitle(String title) {
        this.title = title;
        textViewTitle.setText(this.title);

    }

    @Override
    public void showProgress(Activity activity) {
        if (kProgressHUD != null) {
            kProgressHUD = null;
        }
        if (!activity.isFinishing()) {
            kProgressHUD = KProgressHUD.create(activity)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait")
                    .setCancellable(true)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f);
            kProgressHUD.show();
        }

    }

    @Override
    public void hideProgress() {
        if (kProgressHUD != null) {
            kProgressHUD.dismiss();
        }
    }

    public void imageButtonBackClick(View view) {

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_menu:
//                CustomToast.showToast(this, "Clicked On Settings");
                return true;
            case R.id.logout_menu:
                SharedPref.clearPrefs();
                CustomToast.showToast(this, getResources().getString(R.string.logout_successfully));
                startActivity(new Intent(this, LoginActivity.class));
                finishAffinity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showNoInternetAlert() {
        builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.proceed_offline));
        builder.setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        isOffline = true;
                        dialog.cancel();
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
//                        intent.setClassName("com.android.phone", "com.android.phone.ACTION_WIRELESS_SETTINGS");
                        startActivity(intent);
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void showSuccessDialog(String message){
        builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(false)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        isOffline = true;
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public static float round(float number) {
        int decimalPlace = 2;
        BigDecimal bd = new BigDecimal(number);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

}
