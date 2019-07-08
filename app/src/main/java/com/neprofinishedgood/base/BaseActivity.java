package com.neprofinishedgood.base;

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

import com.neprofinishedgood.R;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.login.LoginActivity;

public class BaseActivity extends AppCompatActivity implements IBaseInterface {
    String title;
    TextView textViewTitle;
    ImageButton imageButtonBack;


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
                CustomToast.showToast(this, getResources().getString(R.string.logout_successfully));
                startActivity(new Intent(this, LoginActivity.class));
                finishAffinity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
