package com.neprofinishedgood.pickandload;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.pickandload.model.LoadingPlanDetails;
import com.neprofinishedgood.pickandload.model.LoadingPlanInput;
import com.neprofinishedgood.pickandload.model.LoadingPlanList;
import com.neprofinishedgood.pickandload.model.ScanLoadingPlanList;
import com.neprofinishedgood.pickandload.presenter.IPickLoadItemInterface;
import com.neprofinishedgood.pickandload.presenter.IPickLoadItemView;
import com.neprofinishedgood.pickandload.presenter.PickAndLoadItemPresenter;
import com.neprofinishedgood.utils.Constants;
import com.neprofinishedgood.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class PickAndLoadStillageActivity extends BaseActivity implements IPickLoadItemView {
    private static PickAndLoadStillageActivity instance;
    @BindView(R.id.recyclerViewLoadingPlansStillage)
    RecyclerView recyclerViewLoadingPlansStillage;

    @BindView(R.id.linearLayoutStillages)
    LinearLayout linearLayoutStillages;
    @BindView(R.id.linearLayoutLoadingPlan)
    LinearLayout linearLayoutLoadingPlan;

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

    private PickAndLoadStillagesAdapter loadingPlanStillagesAdapter;
    private IPickLoadItemInterface iPickAndLoadItemInterFace;
    long delay = 1000;
    long scanStillageLastTexxt = 0;
    String loadingPlan = "";
    private ScanLoadingPlanList stillageDatum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_and_load_stillage);
        ButterKnife.bind(this);
        instance = this;
        Utils.hideSoftKeyboard(this);
        setTitle(getString(R.string.pickload));
        iPickAndLoadItemInterFace = new PickAndLoadItemPresenter(this, this);
        initData();
    }

    public static PickAndLoadStillageActivity getInstance() {
        return instance;
    }
    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void oneditTextScanStillageChanged(Editable text) {
        if (!text.toString().trim().equals("")) {
            scanStillagehandler2.postDelayed(stillageRunnable, delay);
        }

    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void oneditTextScanStillageTEXTCHANGED(Editable text) {
        scanStillagehandler2.removeCallbacks(stillageRunnable2);

    }

    //for call service on text change
    Handler scanStillagehandler2 = new Handler();
    private Runnable stillageRunnable2 = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        public void run() {
            if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {

            }
        }
    };







    @OnTextChanged(value = R.id.editTextScanLoadingPlan, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanStillageChanged(Editable text) {
        if (!text.toString().trim().equals("")) {
            scanStillagehandler.postDelayed(stillageRunnable, delay);
        }

    }

    @OnTextChanged(value = R.id.editTextScanLoadingPlan, callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void onEditTextScanStillageTEXTCHANGED(Editable text) {
        scanStillagehandler.removeCallbacks(stillageRunnable);

    }

    //for call service on text change
    Handler scanStillagehandler = new Handler();
    private Runnable stillageRunnable = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        public void run() {
            if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
                if (editTextScanLoadingPlan.getText().toString().trim().equals(loadingPlan)) {
                    PickAndLoadActivity.getInstance().refreshData(loadingPlan);
                    recyclerViewLoadingPlansStillage.setClickable(true);
                    recyclerViewLoadingPlansStillage.setEnabled(true);
                    recyclerViewLoadingPlansStillage.setForeground(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent)));
                    linearLayoutLoadingPlan.setVisibility(View.GONE);
                    linearLayoutStillages.setVisibility(View.VISIBLE);
                } else {
                    editTextScanLoadingPlan.setText("");
                    editTextScanLoadingPlan.requestFocus();
                    CustomToast.showToast(PickAndLoadStillageActivity.this, getString(R.string.loading_plan_not_matched_with_tar));
                }
            }
        }
    };

    private void initData() {
        String SELECTED_STILLAGE = getIntent().getStringExtra(Constants.SELECTED_STILLAGE);
        Gson gson = new Gson();
        stillageDatum = gson.fromJson(SELECTED_STILLAGE, ScanLoadingPlanList.class);
        loadingPlan = stillageDatum.getLoadingPlanNo();
        textViewLoadingPlan.setText(loadingPlan);
        showProgress(this);
        iPickAndLoadItemInterFace.callGetLoadingPlanDetails(new LoadingPlanInput(stillageDatum.getTLPHID() + "", userId));

    }

    @Override
    public void onFailure(String message) {
        hideProgress();
        CustomToast.showToast(this, message);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onSuccess(LoadingPlanDetails body) {
        hideProgress();
        setData(body);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setData(LoadingPlanDetails body) {
        textViewGateNumber.setText(body.getGateNo() + "");
        textViewLoadingPlan.setText(body.getLoadingPlanNo());
        textViewTruckDriver.setText(body.getDriverName());
        textViewTruckNumber.setText(body.getTruckID());
        setAdapter(body.getLoadingPlanList1());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setAdapter(List<LoadingPlanList> loadingPlanList) {
        recyclerViewLoadingPlansStillage.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        loadingPlanStillagesAdapter = new PickAndLoadStillagesAdapter(loadingPlanList);
        recyclerViewLoadingPlansStillage.setAdapter(loadingPlanStillagesAdapter);
        recyclerViewLoadingPlansStillage.setHasFixedSize(true);
        if (loadingPlanList.size() > 0) {
            if (stillageDatum.getStatus().equals("1")) {
                recyclerViewLoadingPlansStillage.setClickable(true);
                recyclerViewLoadingPlansStillage.setEnabled(true);
                recyclerViewLoadingPlansStillage.setForeground(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent)));
                linearLayoutLoadingPlan.setVisibility(View.GONE);
                linearLayoutStillages.setVisibility(View.VISIBLE);
            } else {
                recyclerViewLoadingPlansStillage.setClickable(false);
                recyclerViewLoadingPlansStillage.setEnabled(false);
                recyclerViewLoadingPlansStillage.setForeground(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.transparent)));
                linearLayoutLoadingPlan.setVisibility(View.VISIBLE);
                linearLayoutStillages.setVisibility(View.GONE);
            }
        }
    }
}