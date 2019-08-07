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
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.pickandload.model.LoadingPlanDetails;
import com.neprofinishedgood.pickandload.model.LoadingPlanInput;
import com.neprofinishedgood.pickandload.model.LoadingPlanList;
import com.neprofinishedgood.pickandload.model.ScanLoadingPlanList;
import com.neprofinishedgood.pickandload.presenter.IPickLoadItemInterface;
import com.neprofinishedgood.pickandload.presenter.IPickLoadItemView;
import com.neprofinishedgood.pickandload.presenter.PickAndLoadItemPresenter;
import com.neprofinishedgood.move.model.AllAssignedDataInput;
import com.neprofinishedgood.utils.Constants;
import com.neprofinishedgood.utils.SharedPref;
import com.neprofinishedgood.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class PickAndLoadStillageActivity extends BaseActivity implements IPickLoadItemView {
    private static PickAndLoadStillageActivity instance;
    public String stillageNoToDelete;
    @BindView(R.id.recyclerViewLoadingPlansStillage)
    RecyclerView recyclerViewLoadingPlansStillage;

    @BindView(R.id.linearLayoutStillages)
    LinearLayout linearLayoutStillages;
    @BindView(R.id.linearLayoutLoadingPlan)
    LinearLayout linearLayoutLoadingPlan;

    @BindView(R.id.editTextScanLoadingPlan)
    AppCompatEditText editTextScanLoadingPlan;
    @BindView(R.id.editTextScanStillage)
    AppCompatEditText editTextScanStillage;

    @BindView(R.id.textViewGateNumber)
    TextView textViewGateNumber;
    @BindView(R.id.textViewTruckDriver)
    TextView textViewTruckDriver;
    @BindView(R.id.textViewTruckNumber)
    TextView textViewTruckNumber;
    @BindView(R.id.buttonEndPick)
    CustomButton buttonEndPick;

    private PickAndLoadStillagesAdapter loadingPlanStillagesAdapter;
    IPickLoadItemInterface iPickAndLoadItemInterFace;
    long delay = 1000;
    long scanStillageLastTexxt = 0, scanStillageLastTexxt2 = 0;
    String loadingPlan = "", scanStillageNo = "";

    public ScanLoadingPlanList scanLoadingPlanList;
    List<ScanLoadingPlanList> saveLoadingPlanList = new ArrayList<>();
    boolean isAlreadyExist, isSearchOnStillages = false;
    private List<LoadingPlanList> loadingPlanDetailLists = new ArrayList<>();
    List<LoadingPlanList> loadingPlanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_and_load_stillage);
        ButterKnife.bind(this);
        instance = this;
        Utils.hideSoftKeyboard(this);
        iPickAndLoadItemInterFace = new PickAndLoadItemPresenter(this, this);
        initData();
    }

    public static PickAndLoadStillageActivity getInstance() {
        return instance;
    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void oneditTextScanStillageChanged(Editable text) {
        if (!text.toString().trim().equals("")) {
            scanStillagehandler2.postDelayed(stillageRunnable2, delay);
        }

    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void oneditTextScanStillageTEXTCHANGED(Editable text) {
        scanStillageNo = text.toString();
        scanStillagehandler2.removeCallbacks(stillageRunnable2);

    }

    //for call service on text change
    Handler scanStillagehandler2 = new Handler();
    private Runnable stillageRunnable2 = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        public void run() {
            if (System.currentTimeMillis() > (scanStillageLastTexxt2 + delay - 500)) {
                loadingPlanStillagesAdapter.getFilter().filter(scanStillageNo);
                editTextScanStillage.setText("");
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
                    saveLoadingPlanList = SharedPref.getLoadinGplanList();
                    saveLoadingPlanList.add(scanLoadingPlanList);
                    String saveLoadingPlanListData = new Gson().toJson(saveLoadingPlanList);
                    SharedPref.saveLoadinGplanList(saveLoadingPlanListData);
                } else {
                    editTextScanLoadingPlan.setText("");
                    editTextScanLoadingPlan.requestFocus();
                    CustomToast.showToast(PickAndLoadStillageActivity.this, getString(R.string.loading_plan_not_matched_with_tar));
                }


            }
        }
    };


    @Override
    public void onFailure(String message) {
        hideProgress();
        CustomToast.showToast(this, message);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onSuccess(LoadingPlanDetails body) {
        hideProgress();
        if (body.getDriverName() == null || body.getGateNo() == null || body.getLoadingPlanList1() == null ||
                body.getLoadingPlanNo() == null || body.getTruckID() == null) {
            CustomToast.showToast(PickAndLoadStillageActivity.this, getString(R.string.no_data_found));
            textViewGateNumber.setText("");
//            textViewLoadingPlan.setText("");
            textViewTruckDriver.setText("");
            textViewTruckNumber.setText("");
        } else {
            setData(body);
        }

    }

    @Override
    public void onUpdateLoadingPlanDetailsFailure(String message) {
        hideProgress();
        CustomToast.showToast(this, message);
    }

    @Override
    public void onUpdateLoadingPlanDetailsSuccess(UniversalResponse body) {
        hideProgress();
        if (body.getStatus().equals(getString(R.string.success))) {
            loadingPlanDetailLists = SharedPref.getLoadinGplanDetailList();
            for (LoadingPlanList loadingPlanList : loadingPlanDetailLists) {
                if (loadingPlanList.getStillageNO().equals(stillageNoToDelete)) {
                    if (loadingPlanDetailLists.size() == 1) {
                        loadingPlanDetailLists.clear();
                    } else if (loadingPlanDetailLists.size() > 1) {
                        loadingPlanDetailLists.remove(loadingPlanList);
                    }
                    SharedPref.saveLoadinGplanDetailList(new Gson().toJson(loadingPlanDetailLists));
                }
            }
            CustomToast.showToast(this, body.getMessage());
            showProgress(this);
            //  PickAndLoadActivity.getInstance().iPickAndLoadInterFace.callGetLoadingPlan(new AllAssignedDataInput(userId));
            iPickAndLoadItemInterFace.callGetLoadingPlanDetails(new LoadingPlanInput(scanLoadingPlanList.getTLPHID() + "", userId));
        } else {
            CustomToast.showToast(this, body.getMessage());
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setData(LoadingPlanDetails body) {
        textViewGateNumber.setText(body.getGateNo() + "");
//        textViewLoadingPlan.setText(body.getLoadingPlanNo());
        textViewTruckDriver.setText(body.getDriverName());
        textViewTruckNumber.setText(body.getTruckID());
        setAdapter(body.getLoadingPlanList1());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setAdapter(List<LoadingPlanList> loadingPlanList) {
        this.loadingPlanList = loadingPlanList;
        for (ScanLoadingPlanList scanLoadingPlanList : saveLoadingPlanList) {
            if (scanLoadingPlanList.getLoadingPlanNo().equals(loadingPlan)) {
                isAlreadyExist = true;
                break;
            }
        }
        if (this.loadingPlanList.size() > 0) {
            buttonEndPick.setVisibility(View.VISIBLE);
        }

        if (isAlreadyExist) {
            PickAndLoadActivity.getInstance().refreshData(loadingPlan);
            recyclerViewLoadingPlansStillage.setClickable(true);
            recyclerViewLoadingPlansStillage.setEnabled(true);
            recyclerViewLoadingPlansStillage.setForeground(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent)));
            linearLayoutLoadingPlan.setVisibility(View.GONE);
            linearLayoutStillages.setVisibility(View.VISIBLE);
            editTextScanStillage.requestFocus();
        } else {
            if (this.loadingPlanList.size() > 0) {
                if (scanLoadingPlanList.getStatus().equals("1")) {
                    recyclerViewLoadingPlansStillage.setClickable(true);
                    recyclerViewLoadingPlansStillage.setEnabled(true);
                    recyclerViewLoadingPlansStillage.setForeground(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent)));
                    linearLayoutLoadingPlan.setVisibility(View.GONE);
                    linearLayoutStillages.setVisibility(View.VISIBLE);
                    editTextScanStillage.requestFocus();
                } else {
                    recyclerViewLoadingPlansStillage.setClickable(false);
                    recyclerViewLoadingPlansStillage.setEnabled(false);
                    recyclerViewLoadingPlansStillage.setForeground(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.blur_transparent)));
                    linearLayoutLoadingPlan.setVisibility(View.VISIBLE);
                    linearLayoutStillages.setVisibility(View.GONE);
                    editTextScanLoadingPlan.requestFocus();
                }
            }
        }
        recyclerViewLoadingPlansStillage.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        loadingPlanStillagesAdapter = new PickAndLoadStillagesAdapter(this.loadingPlanList);
        recyclerViewLoadingPlansStillage.setAdapter(loadingPlanStillagesAdapter);
        recyclerViewLoadingPlansStillage.setHasFixedSize(true);

    }

    private void initData() {
        String SELECTED_STILLAGE = getIntent().getStringExtra(Constants.SELECTED_STILLAGE);
        Gson gson = new Gson();
        scanLoadingPlanList = gson.fromJson(SELECTED_STILLAGE, ScanLoadingPlanList.class);
        saveLoadingPlanList = SharedPref.getLoadinGplanList();
        loadingPlan = scanLoadingPlanList.getLoadingPlanNo();
//        textViewLoadingPlan.setText(loadingPlan);
        showProgress(this);
        setTitle(loadingPlan);
        iPickAndLoadItemInterFace.callGetLoadingPlanDetails(new LoadingPlanInput(scanLoadingPlanList.getTLPHID() + "", userId));

    }

    @OnClick(R.id.buttonEndPick)
    public void buttonEndPickClick() {
        showProgress(this);
        iPickAndLoadItemInterFace.callEndPickService(new LoadingPlanInput(scanLoadingPlanList.getTLPHID() + "", userId));
    }

    @Override
    public void onEndPickFailure(String message) {
        hideProgress();
        CustomToast.showToast(this, message);
    }

    @Override
    public void onEndPickSuccess(UniversalResponse body) {
        hideProgress();
        if (body.getStatus().equals(getString(R.string.success))) {
            CustomToast.showToast(this, body.getMessage());
            loadingPlanDetailLists = SharedPref.getLoadinGplanDetailList();
            for (LoadingPlanList loadingPlanList : loadingPlanDetailLists) {
                if (loadingPlanList.getLoadingNumber().equals(loadingPlan)) {
                    if (loadingPlanDetailLists.size() == 1) {
                        loadingPlanDetailLists.clear();
                    } else if (loadingPlanDetailLists.size() > 1) {
                        loadingPlanDetailLists.remove(loadingPlanList);
                    }

                }
            }
            SharedPref.saveLoadinGplanDetailList(new Gson().toJson(loadingPlanDetailLists));
            PickAndLoadActivity.getInstance().iPickAndLoadInterFace.callGetLoadingPlan(new AllAssignedDataInput(userId));
            finish();
        } else {
            CustomToast.showToast(this, body.getMessage());
        }
    }
}