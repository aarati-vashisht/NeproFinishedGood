package com.neprofinishedgood.dashboard;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.neprofinishedgood.R;
import com.neprofinishedgood.assignplannedunplanned.AssignPlannedAndUnplannedActivity;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.raf.RAFActivity;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.login.model.LoginResponse;
import com.neprofinishedgood.lookup.LookUpActivity;
import com.neprofinishedgood.mergestillage.MergeStillageActivity;
import com.neprofinishedgood.pickandload.PickAndLoadActivity;
import com.neprofinishedgood.plannedandunplannedmove.PlannedAndUnPlannedMoveActivity;
import com.neprofinishedgood.qualitycheck.QualityCheckDashboardActivity;
import com.neprofinishedgood.receivestillage.ReceiveStillageActivity;
import com.neprofinishedgood.transferstillage.TransferStillageActivity;
import com.neprofinishedgood.updatequantity.UpdateQuantityActivity;
import com.neprofinishedgood.utils.NetworkChangeReceiver;
import com.neprofinishedgood.utils.SharedPref;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashBoardAcivity extends BaseActivity {


    public static DashBoardAcivity instance;
    private LoginResponse loginResponse;

    public static DashBoardAcivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        ButterKnife.bind(this);
        instance = this;
        setTitle(getString(R.string.dashbord));
        initData();
        offlineCheck();

    }

    private void initData() {
        Gson gson = new Gson();
        loginResponse = gson.fromJson(SharedPref.getLoginUser(), LoginResponse.class);

    }

    @OnClick(R.id.linearLayoutPutAway)
    public void onLinearLayouPutAwayClick() {
        if (loginResponse.getUserLoginResponse().get(0).getIsMove() == 0) {
            CustomToast.showToast(getApplicationContext(), "You Don't Have right to Access It");
        } else {
            startActivity(new Intent(this, PlannedAndUnPlannedMoveActivity.class));
        }
    }

    @OnClick(R.id.linearLayoutLookUp)
    public void onlinearLayoutLookUpClick() {
        if (loginResponse.getUserLoginResponse().get(0).getIsLookUp() == 0) {
            CustomToast.showToast(getApplicationContext(), "You Don't Have right to Access It");
        } else {
            startActivity(new Intent(this, LookUpActivity.class));
        }
    }

    @OnClick(R.id.linearLayoutQualityCheck)
    public void onlinearLayoutQualityCheckClick() {
        if (loginResponse.getUserLoginResponse().get(0).getIsQualityCheck() == 0) {
            CustomToast.showToast(getApplicationContext(), "You Don't Have right to Access It");
        } else {
            startActivity(new Intent(this, QualityCheckDashboardActivity.class));
        }
    }

    @OnClick(R.id.linearLayoutAssignLocationAndFlt)
    public void onlinearLayoutAssignLocationAndFltClick() {
        if (loginResponse.getUserLoginResponse().get(0).getIsAssignedPlannedAndUnplanned() == 0) {
            CustomToast.showToast(getApplicationContext(), "You Don't Have right to Access It");
        } else {
            startActivity(new Intent(this, AssignPlannedAndUnplannedActivity.class));
        }
    }

    @OnClick(R.id.linearLayoutPutAndHold)
    public void onlinearLayoutPutAndHoldClick() {
        if (loginResponse.getUserLoginResponse().get(0).getIsPickAndCount() == 0) {
            CustomToast.showToast(getApplicationContext(), "You Don't Have right to Access It");
        } else {
            startActivity(new Intent(this, PickAndLoadActivity.class));
        }
    }

    @OnClick(R.id.linearLayoutReturnStillage)
    public void onlinearLayoutReturnStillageClick() {
        if (loginResponse.getUserLoginResponse().get(0).getIsReturnStillage() == 0) {
            CustomToast.showToast(getApplicationContext(), "You Don't Have right to Access It");
        } else {
            startActivity(new Intent(this, TransferStillageActivity.class));
        }
    }

    @OnClick(R.id.linearLayoutReceiveReturnStillage)
    public void onlinearLayoutReceiveReturnStillageClick() {
        if (loginResponse.getUserLoginResponse().get(0).getIsRecieveReturnStillage() == 0) {
            CustomToast.showToast(getApplicationContext(), "You Don't Have right to Access It");
        } else {
            startActivity(new Intent(this, ReceiveStillageActivity.class));
        }
    }

    @OnClick(R.id.linearLayoutMergeStillage)
    public void onlinearLayoutMergeStillageClick() {
        if (loginResponse.getUserLoginResponse().get(0).getIsMergeStillage() == 0) {
            CustomToast.showToast(getApplicationContext(), "You Don't Have right to Access It");
        } else {
            startActivity(new Intent(this, MergeStillageActivity.class));
        }
    }

    @OnClick(R.id.linearLayoutCounting)
    public void setlinearLayoutCountingClick() {
        if (loginResponse.getUserLoginResponse().get(0).getIsReportAsFinished() == 0) {
            CustomToast.showToast(getApplicationContext(), "You Don't Have right to Access It");
        } else {
            startActivity(new Intent(this, RAFActivity.class));
        }
    }

    @OnClick(R.id.linearLayoutUpdateQuantity)
    public void setlinearLayoutUpdateQuantityClick() {
        startActivity(new Intent(this, UpdateQuantityActivity.class));
    }

    void offlineCheck(){
        if(!NetworkChangeReceiver.isInternetConnected(DashBoardAcivity.this) ){
            showNoInternetAlert();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
