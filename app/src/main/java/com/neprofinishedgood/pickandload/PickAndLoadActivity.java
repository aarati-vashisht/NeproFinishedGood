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
import com.neprofinishedgood.plannedandunplannedmove.model.AllAssignedDataInput;
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
        getLoadingPlanData();
    }

    private void getLoadingPlanData() {
        showProgress(this);
        iPickAndLoadInterFace.callGetLoadingPlan(new AllAssignedDataInput(userId));
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
            SharedPref.saveLoadinGplanList("");
        }
    }

    @Override
    public void onFailure(String message) {
        hideProgress();
        CustomToast.showToast(this, message);
    }

    public static PickAndLoadActivity getInstance() {
        return instance;
    }

    @Override
    public void onSuccess(LoadingPlanResponse body) {
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
        CustomToast.showToast(this, message);
    }

    @Override
    public void onCancelSuccess(UniversalResponse body) {
        hideProgress();
        CustomToast.showToast(this, body.getMessage());
        showProgress(this);
        List<LoadingPlanList> loadingPlanDetailLists = SharedPref.getLoadinGplanDetailList();
        loadingPlanDetailLists.clear();
        SharedPref.saveLoadinGplanDetailList(new Gson().toJson(loadingPlanDetailLists));
        iPickAndLoadInterFace.callGetLoadingPlan(new AllAssignedDataInput(userId));
    }

}