package com.neprofinishedgood.pickandload;

import android.os.Bundle;
import android.text.Editable;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.pickandload.model.LoadingPlanDatum;
import com.neprofinishedgood.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class PickAndLoadActivity extends BaseActivity {
    @BindView(R.id.recyclerViewLoadingPlans)
    RecyclerView recyclerViewLoadingPlans;


    private PickAndLoadAdapter loadingPlanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_and_load);
        ButterKnife.bind(this);
        Utils.hideSoftKeyboard(this);
        setTitle(getString(R.string.pickload));
        setAdapter();
    }

    private void setAdapter() {
        recyclerViewLoadingPlans.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        List<LoadingPlanDatum> loadingPlanDataList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            LoadingPlanDatum loadingPlanDatum = new LoadingPlanDatum();
            loadingPlanDatum.setloadingPlan("L0000" + i);
            loadingPlanDatum.setcustomer("Customer" + i);
            loadingPlanDatum.setloadingPlanId("Id" + i);
            loadingPlanDataList.add(loadingPlanDatum);
        }
        loadingPlanAdapter = new PickAndLoadAdapter(loadingPlanDataList);
        recyclerViewLoadingPlans.setAdapter(loadingPlanAdapter);
        recyclerViewLoadingPlans.setHasFixedSize(true);

    }
}
