package com.neprofinishedgood.qualitycheck;

import android.content.Intent;
import android.os.Bundle;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.qualitycheck.qualityhold.QualityHoldActivity;
import com.neprofinishedgood.qualitycheck.rejectcompletestillage.RejectCompleteStillage;
import com.neprofinishedgood.qualitycheck.rejectquantity.RejectQuantityActivity;
import com.neprofinishedgood.updatequantity.UpdateQuantityActivity;
import com.neprofinishedgood.utils.NetworkChangeReceiver;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class QualityCheckDashboardActivity extends BaseActivity {

    String REJECT_TITLE = "REJECT_TITLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_check_dashboard);
        ButterKnife.bind(this);
        setTitle(getString(R.string.quality_check));
    }

    @OnClick(R.id.linearLayoutRejectPcs)
    public void onLinearLayoutRejectQuantityClick() {
        startActivity(new Intent(QualityCheckDashboardActivity.this, RejectQuantityActivity.class)
                .putExtra(REJECT_TITLE, getString(R.string.reject_quantity_pcs))
                .putExtra("IsKg", "0"));
    }

    @OnClick(R.id.linearLayoutRejectKg)
    public void onLinearLayoutRejectCompleteStillageClick() {
        startActivity(new Intent(QualityCheckDashboardActivity.this, RejectQuantityActivity.class)
                .putExtra(REJECT_TITLE, getString(R.string.reject_quantity_kg))
                .putExtra("IsKg", "1"));
    }

    @OnClick(R.id.linearLayoutQualityHoldAndMove)
    public void onLinearLayoutQualityHoldAndMoveClick() {
        startActivity(new Intent(QualityCheckDashboardActivity.this, QualityHoldActivity.class));
    }
}
