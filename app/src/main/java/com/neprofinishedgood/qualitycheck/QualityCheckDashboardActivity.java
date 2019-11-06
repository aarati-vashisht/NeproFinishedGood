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

    @OnClick(R.id.linearLayoutRejectQuantity)
    public void onLinearLayoutRejectQuantityClick() {
        startActivity(new Intent(QualityCheckDashboardActivity.this, UpdateQuantityActivity.class)
                .putExtra(REJECT_TITLE, getString(R.string.reject_quantity)));
//        startActivity(new Intent(QualityCheckDashboardActivity.this, RejectQuantityActivity.class)
//                .putExtra(REJECT_TITLE, getString(R.string.reject_quantity)));
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
