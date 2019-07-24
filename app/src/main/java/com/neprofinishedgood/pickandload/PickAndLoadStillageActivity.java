package com.neprofinishedgood.pickandload;

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
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.pickandload.model.LoadingPlanDatum;
import com.neprofinishedgood.pickandload.model.LoadingPlanDetailInput;
import com.neprofinishedgood.pickandload.model.LoadingPlanDetailResponse;
import com.neprofinishedgood.pickandload.model.LoadingPlanList1;
import com.neprofinishedgood.pickandload.presenter.ILoadingPlanDetailPresenter;
import com.neprofinishedgood.pickandload.presenter.ILoadingPlanDetailView;
import com.neprofinishedgood.pickandload.presenter.ILoadingPlanPresenter;
import com.neprofinishedgood.plannedandunplannedmove.model.AllAssignedDataInput;
import com.neprofinishedgood.raf.model.StillageDatum;
import com.neprofinishedgood.utils.Constants;
import com.neprofinishedgood.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class PickAndLoadStillageActivity extends BaseActivity implements ILoadingPlanDetailView {
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
    String loadingPlanId;
    public static String loadingPlanNumber;
    ILoadingPlanDetailPresenter iLoadingPlanDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_and_load_stillage);
        ButterKnife.bind(this);
        instance = this;
        Utils.hideSoftKeyboard(this);
        setTitle(getString(R.string.pickload));

        iLoadingPlanDetailPresenter = new ILoadingPlanDetailPresenter(this, this);
        loadingPlanId = getIntent().getStringExtra(Constants.SELECTED_STILLAGE);
        getLoadingDetailData();
    }

    public void getLoadingDetailData() {
        showProgress(this);
        iLoadingPlanDetailPresenter.callLoadingPlanDetailService(new LoadingPlanDetailInput(loadingPlanId, userId));
    }

    public static PickAndLoadStillageActivity getInstance() {
        return instance;
    }

    @OnTextChanged(value = R.id.editTextScanLoadingPlan,
            callback = OnTextChanged.Callback.TEXT_CHANGED)
    protected void afterEditTextChanged(Editable editable) {
        loadingPlanStillagesAdapter.getFilter().filter(editable);
    }

    private void setData(LoadingPlanDetailResponse loadingPlanDetailResponse) {
//        String SELECTED_STILLAGE = getIntent().getStringExtra(Constants.SELECTED_STILLAGE);
//        Gson gson = new Gson();
//        LoadingPlanDatum stillageDatum = gson.fromJson(SELECTED_STILLAGE, LoadingPlanDatum.class);
        loadingPlanNumber = loadingPlanDetailResponse.getLoadingPlanNo();
        textViewTruckNumber.setText(loadingPlanDetailResponse.getDriverName());
        textViewTruckDriver.setText(loadingPlanDetailResponse.getTruckID() );
        textViewGateNumber.setText(loadingPlanDetailResponse.getGateNo()+"");
        textViewLoadingPlan.setText(loadingPlanDetailResponse.getLoadingPlanNo());

        recyclerViewLoadingPlansStillage.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        List<StillageDatum> getFormResponseList = new ArrayList<>();
//        for (int i = 1; i <= 10; i++) {
//            StillageDatum formDatum = new StillageDatum();
//            formDatum.setNumber("W0000" + i);
//            formDatum.setName("S" + i);
//            formDatum.setItem("Item" + i);
//            formDatum.setStdQuantity("20");
//            formDatum.setLoadingPlan(stillageDatum.getloadingPlan());
//            formDatum.setQuantity("20");
//            getFormResponseList.add(formDatum);
//        }
        loadingPlanStillagesAdapter = new PickAndLoadStillagesAdapter(loadingPlanDetailResponse.getLoadingPlanList1());
        recyclerViewLoadingPlansStillage.setAdapter(loadingPlanStillagesAdapter);
        recyclerViewLoadingPlansStillage.setHasFixedSize(true);

    }

    @Override
    public void onLoadingPlanDetailSuccess(LoadingPlanDetailResponse response) {
        hideProgress();
        if (response.getStatus().equals(getString(R.string.success))) {
            CustomToast.showToast(this, response.getMessage());
            if (response.getLoadingPlanList1().size() > 0) {
                setData(response);
            }
        } else {
            CustomToast.showToast(this, getString(R.string.something_went_wrong_please_try_again));
        }
    }

    @Override
    public void onLoadingPlanDetailFailure(String message) {
        hideProgress();
        CustomToast.showToast(this, message);
    }
}
