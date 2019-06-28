package com.neprofinishedgood.dashboard;

import android.content.Intent;
import android.os.Bundle;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.counting.CountingActivity;
import com.neprofinishedgood.putaway.PutAwayActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashBoardAcivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_acivity);
        ButterKnife.bind(this);
        setTitle("Dashboard");
    }


    @OnClick(R.id.linearLayoutCounting)
    public void setlinearLayoutCountingClick() {
        startActivity(new Intent(this, CountingActivity.class));
    }

    @OnClick(R.id.linearLayoutPutAway)
    public void onLinearLayouPutAwayClick() {
        startActivity(new Intent(this, PutAwayActivity.class));
    }
}
