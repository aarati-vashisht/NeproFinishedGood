package com.neprofinishedgood.pickandhold;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.pickandhold.model.LoadingPlanDatum;
import com.neprofinishedgood.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class PickAndLoadActivity extends BaseActivity {
    @BindView(R.id.recyclerViewLoadingPlans)
    RecyclerView recyclerViewLoadingPlans;

    @BindView(R.id.editTextScanLoadingPlan)
    AppCompatEditText editTextScanLoadingPlan;


    private PickAndLoadAdapter loadingPlanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_and_hold);
        ButterKnife.bind(this);
        Utils.hideSoftKeyboard(this);
        setTitle(getString(R.string.pickload));
        setAdapter();
    }

    @OnTextChanged(value = R.id.editTextScanLoadingPlan,
            callback = OnTextChanged.Callback.TEXT_CHANGED)
    protected void afterEditTextChanged(Editable editable) {
        loadingPlanAdapter.getFilter().filter(editable);
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
