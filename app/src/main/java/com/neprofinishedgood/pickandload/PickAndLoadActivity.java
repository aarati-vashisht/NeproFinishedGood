package com.neprofinishedgood.pickandload;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.pickandload.model.LoadingPlanList;
import com.neprofinishedgood.pickandload.model.LoadingPlanResponse;
import com.neprofinishedgood.pickandload.model.ScanLoadingPlanList;
import com.neprofinishedgood.pickandload.presenter.IPickAndLoadInterFace;
import com.neprofinishedgood.pickandload.presenter.IPickAndLoadVIew;
import com.neprofinishedgood.pickandload.presenter.PickAndLoadPresenter;
import com.neprofinishedgood.move.model.AllAssignedDataInput;
import com.neprofinishedgood.utils.NetworkChangeReceiver;
import com.neprofinishedgood.utils.SharedPref;
import com.neprofinishedgood.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PickAndLoadActivity extends BaseActivity implements IPickAndLoadVIew {
    @BindView(R.id.recyclerViewLoadingPlans)
    RecyclerView recyclerViewLoadingPlans;

    private PickAndLoadAdapter loadingPlanAdapter;
    IPickAndLoadInterFace iPickAndLoadInterFace;
    public List<ScanLoadingPlanList> scanLoadingPlanList = new ArrayList<>();
    List<ScanLoadingPlanList> tempSavedList = new ArrayList<>();
    static PickAndLoadActivity instance;
    private boolean isExists = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_and_load);
        ButterKnife.bind(this);
        instance = this;
        Utils.hideSoftKeyboard(this);
        setTitle(getString(R.string.pickload));
        iPickAndLoadInterFace = new PickAndLoadPresenter(this, this);
    }

    private void getLoadingPlanData() {
        if (NetworkChangeReceiver.isInternetConnected(PickAndLoadActivity.this)) {
            showProgress(this);
            iPickAndLoadInterFace.callGetLoadingPlan(new AllAssignedDataInput(userId));
        } else {
            showSuccessDialog(getString(R.string.no_internet));
//            CustomToast.showToast(PickAndLoadActivity.this, getString(R.string.no_internet));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLoadingPlanData();
    }

    private void setAdapter(List<ScanLoadingPlanList> scanLoadingPlanList) {
        if (scanLoadingPlanList != null) {
            if (scanLoadingPlanList.size() > 0) {
                recyclerViewLoadingPlans.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                loadingPlanAdapter = new PickAndLoadAdapter(scanLoadingPlanList);
                recyclerViewLoadingPlans.setAdapter(loadingPlanAdapter);
                recyclerViewLoadingPlans.setHasFixedSize(true);
                List<ScanLoadingPlanList> savedLoadingPlanList = SharedPref.getLoadinGplanList();
                tempSavedList = savedLoadingPlanList;
                for (ScanLoadingPlanList savedLoadingPlan : savedLoadingPlanList) {
                    for (ScanLoadingPlanList getLoadingPlan : scanLoadingPlanList) {
                        if (savedLoadingPlan.getLoadingPlanNo().equals(getLoadingPlan.getLoadingPlanNo())) {
                            isExists = true;
                            break;
                        }
                    }
                    if (!isExists) {
                        tempSavedList.remove(savedLoadingPlan);
                        isExists = false;
                    }
                }
                SharedPref.saveLoadinGplanList(new Gson().toJson(tempSavedList));

            } else {
                SharedPref.saveLoadinGplanList("");
            }
        } else {
            scanLoadingPlanList = new ArrayList<>();
            recyclerViewLoadingPlans.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            loadingPlanAdapter = new PickAndLoadAdapter(scanLoadingPlanList);
            recyclerViewLoadingPlans.setAdapter(loadingPlanAdapter);
            recyclerViewLoadingPlans.setHasFixedSize(true);
            SharedPref.saveLoadinGplanList("");
        }
    }

    @Override
    public void onFailure(String message) {
        hideProgress();
        showSuccessDialog(message);
//        CustomToast.showToast(this, message);
    }

    public static PickAndLoadActivity getInstance() {
        return instance;
    }

    @Override
    public void onSuccess(LoadingPlanResponse body) {
        scanLoadingPlanList = new ArrayList<>();
        scanLoadingPlanList = body.getScanLoadingPlanList();
        hideProgress();
        setAdapter(scanLoadingPlanList);
    }

    public void refreshData(String loadingPlan) {
        for (int i = 0; i < scanLoadingPlanList.size(); i++) {
            if (scanLoadingPlanList.get(i).getLoadingPlanNo().equals(loadingPlan)) {
                scanLoadingPlanList.get(i).setStatus("1");
            }
        }
    }

    @Override
    public void onCancelFailure(String message) {
        hideProgress();
        showSuccessDialog(message);
//        CustomToast.showToast(this, message);
    }

    @Override
    public void onCancelSuccess(UniversalResponse body) {
        hideProgress();
        showSuccessDialog(body.getMessage());
//        CustomToast.showToast(this, body.getMessage());
        showProgress(this);
        List<LoadingPlanList> loadingPlanDetailLists = SharedPref.getLoadinGplanDetailList();
        loadingPlanDetailLists.clear();
        SharedPref.saveLoadinGplanDetailList(new Gson().toJson(loadingPlanDetailLists));
        iPickAndLoadInterFace.callGetLoadingPlan(new AllAssignedDataInput(userId));
    }

}