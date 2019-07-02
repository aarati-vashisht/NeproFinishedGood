package com.neprofinishedgood.qualitycheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.rejectquantity.RejectQuantityActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class QualityCheckDashboardActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_check_dashboard);
        ButterKnife.bind(this);
        setTitle(getString(R.string.select_quality_check_operation));
    }

    @OnClick(R.id.linearLayoutRejectQuantity)
    public void onLinearLayoutRejectQuantityClick() {
        startActivity(new Intent(QualityCheckDashboardActivity.this, RejectQuantityActivity.class));
    }

    @OnClick(R.id.linearLayoutRejectCompleteStillage)
    public void onLinearLayoutRejectCompleteStillageClick() {

    }

    @OnClick(R.id.linearLayoutQualityHoldAndMove)
    public void onLinearLayoutQualityHoldAndMoveClick() {

    }
}
