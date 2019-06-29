package com.neprofinishedgood.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.neprofinishedgood.R;
import com.neprofinishedgood.utils.Utils;

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
        getSupportActionBar().setDisplayShowHomeEnabled(false);
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
}
