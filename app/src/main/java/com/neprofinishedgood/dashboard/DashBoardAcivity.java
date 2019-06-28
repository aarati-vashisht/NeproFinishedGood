package com.neprofinishedgood.dashboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.neprofinishedgood.R;
import com.neprofinishedgood.putaway.PutAwayActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashBoardAcivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_acivity);

        ButterKnife.bind(this);


    }

    @OnClick(R.id.linearLayoutPutAway)
    public void onLinearLayouPutAwayClick(){
        startActivity(new Intent(this, PutAwayActivity.class));
        finish();
    }
}
