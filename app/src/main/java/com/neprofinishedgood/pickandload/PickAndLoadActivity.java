package com.neprofinishedgood.pickandload;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.pickandload.model.LoadingPlanResponse;
import com.neprofinishedgood.pickandload.model.ScanLoadingPlanList;
import com.neprofinishedgood.pickandload.presenter.ILoadingPlanPresenter;
import com.neprofinishedgood.pickandload.presenter.ILoadingPlanView;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PickAndLoadActivity extends BaseActivity implements ILoadingPlanView {
    @BindView(R.id.recyclerViewLoadingPlans)
    RecyclerView recyclerViewLoadingPlans;

    ILoadingPlanPresenter iLoadingPlanPresenter;


    private PickAndLoadAdapter loadingPlanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_and_load);
        ButterKnife.bind(this);
        Utils.hideSoftKeyboard(this);
        iLoadingPlanPresenter = new ILoadingPlanPresenter(this, this);
        setTitle(getString(R.string.pickload));
        getAllLoadingData();
    }

    void getAllLoadingData() {
        showProgress(this);
        iLoadingPlanPresenter.callLoadingPlanService(new MoveInput("", userId));
    }

    private void setAdapter(List<ScanLoadingPlanList> scanLoadingPlanLists) {
        recyclerViewLoadingPlans.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        loadingPlanAdapter = new PickAndLoadAdapter(scanLoadingPlanLists);
        recyclerViewLoadingPlans.setAdapter(loadingPlanAdapter);
        recyclerViewLoadingPlans.setHasFixedSize(true);

    }

    @Override
    public void onLoadingPlanSuccess(LoadingPlanResponse response) {
        hideProgress();
        if (response.getStatus().equals(getString(R.string.success))) {
            CustomToast.showToast(this, response.getMessage());
            if (response.getScanLoadingPlanList().size() > 0) {
                setAdapter(response.getScanLoadingPlanList());
            }
        } else {
            CustomToast.showToast(this, getString(R.string.something_went_wrong_please_try_again));
        }
    }

    @Override
    public void onLoadingPlanFailure(String message) {
        hideProgress();
        CustomToast.showToast(this, message);
    }
}
