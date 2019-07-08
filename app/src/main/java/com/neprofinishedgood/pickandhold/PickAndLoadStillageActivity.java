package com.neprofinishedgood.pickandhold;

import android.os.Bundle;
import android.text.Editable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.counting.model.StillageDatum;
import com.neprofinishedgood.pickandhold.model.LoadingPlanDatum;
import com.neprofinishedgood.utils.Constants;
import com.neprofinishedgood.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class PickAndLoadStillageActivity extends BaseActivity {
    private static PickAndLoadStillageActivity instance;
    @BindView(R.id.recyclerViewLoadingPlansStillage)
    RecyclerView recyclerViewLoadingPlansStillage;

    @BindView(R.id.editTextScanLoadingPlan)
    AppCompatEditText editTextScanLoadingPlan;

    @BindView(R.id.textViewGateNumber)
    TextView textViewGateNumber;
    @BindView(R.id.textViewTruckDriver)
    TextView textViewTruckDriver;
    @BindView(R.id.textViewTruckNumber)
    TextView textViewTruckNumber;
    @BindView(R.id.textViewLoadingPlan)
    TextView textViewLoadingPlan;
    @BindView(R.id.linearLayoutLoadingPlanNumber)
    LinearLayout linearLayoutLoadingPlanNumber;

    private PickAndLoadStillagesAdapter loadingPlanStillagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_and_load_stillage);
        ButterKnife.bind(this);
        instance = this;
        Utils.hideSoftKeyboard(this);
        setTitle(getString(R.string.pickload));
        setAdapter();
    }

    public static PickAndLoadStillageActivity getInstance() {
        return instance;
    }

    @OnTextChanged(value = R.id.editTextScanLoadingPlan,
            callback = OnTextChanged.Callback.TEXT_CHANGED)
    protected void afterEditTextChanged(Editable editable) {
        loadingPlanStillagesAdapter.getFilter().filter(editable);
    }

    private void setAdapter() {
        String SELECTED_STILLAGE = getIntent().getStringExtra(Constants.SELECTED_STILLAGE);
        Gson gson = new Gson();
        LoadingPlanDatum stillageDatum = gson.fromJson(SELECTED_STILLAGE, LoadingPlanDatum.class);
        textViewLoadingPlan.setText(stillageDatum.getloadingPlan());

        recyclerViewLoadingPlansStillage.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        List<StillageDatum> getFormResponseList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            StillageDatum formDatum = new StillageDatum();
            formDatum.setNumber("W0000" + i);
            formDatum.setName("S" + i);
            formDatum.setItem("Item" + i);
            formDatum.setStdQuantity("20");
            formDatum.setLoadingPlan(stillageDatum.getloadingPlan());
            formDatum.setQuantity("20");
            getFormResponseList.add(formDatum);
        }
        loadingPlanStillagesAdapter = new PickAndLoadStillagesAdapter(getFormResponseList);
        recyclerViewLoadingPlansStillage.setAdapter(loadingPlanStillagesAdapter);
        recyclerViewLoadingPlansStillage.setHasFixedSize(true);

    }
}
