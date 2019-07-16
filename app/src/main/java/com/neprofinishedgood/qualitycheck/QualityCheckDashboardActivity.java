package com.neprofinishedgood.qualitycheck;

import android.content.Intent;
import android.os.Bundle;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.qualitycheck.qualityhold.QualityHoldActivity;
import com.neprofinishedgood.qualitycheck.rejectcompletestillage.RejectCompleteStillage;
import com.neprofinishedgood.qualitycheck.rejectquantity.RejectQuantityActivity;

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
        startActivity(new Intent(QualityCheckDashboardActivity.this, RejectCompleteStillage.class));
    }

    @OnClick(R.id.linearLayoutQualityHoldAndMove)
    public void onLinearLayoutQualityHoldAndMoveClick() {
        startActivity(new Intent(QualityCheckDashboardActivity.this, QualityHoldActivity.class));
    }
}
