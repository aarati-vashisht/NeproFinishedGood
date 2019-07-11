package com.neprofinishedgood.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.neprofinishedgood.R;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.login.LoginActivity;
import com.neprofinishedgood.utils.SharedPref;

public class BaseActivity extends AppCompatActivity implements IBaseInterface {
    String title;
    TextView textViewTitle;
    ImageButton imageButtonBack;
    Gson gson = new Gson();
    private KProgressHUD kProgressHUD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        setActionBarData();


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
                CustomToast.showToast(this, "Clicked On Settings");
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
}
