package com.neprofinishedgood.productionjournal;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.productionjournal.model.PickingModel;
import com.neprofinishedgood.productionjournal.model.RouteModel;
import com.neprofinishedgood.productionjournal.model.WorkOrderInput;
import com.neprofinishedgood.productionjournal.model.WorkOrderResponse;
import com.neprofinishedgood.productionjournal.model.WorkOrderSubmitInput;
import com.neprofinishedgood.productionjournal.presenter.IProductionJournalInterface;
import com.neprofinishedgood.productionjournal.presenter.IProductionJournalPresenter;
import com.neprofinishedgood.productionjournal.presenter.IProductionJournalView;
import com.neprofinishedgood.productionjournal.ui.main.PickingListFragment;
import com.neprofinishedgood.productionjournal.ui.main.SectionsPagerAdapter;
import com.neprofinishedgood.utils.NetworkChangeReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class ProductionJournal extends BaseActivity implements IProductionJournalView {

    @BindView(R.id.editTextSearchItem)
    AppCompatEditText editTextScanWorkOrder;

    @BindView(R.id.linearLayoutWorkOrderNumber)
    LinearLayout linearLayoutWorkOrderNumber;

    @BindView(R.id.linearLayoutButtons)
    LinearLayout linearLayoutButtons;

    @BindView(R.id.view_pager)
    ViewPager view_pager;

    @BindView(R.id.tabs)
    TabLayout tabs;

    @BindView(R.id.buttonConfirm)
    CustomButton buttonConfirm;

    @BindView(R.id.textViewWorkOrderNumber)
    TextView textViewWorkOrderNumber;

    @BindView(R.id.cardView)
    CardView cardView;

    long scanStillageLastTexxt = 0;
    long delay = 1500;

    IProductionJournalInterface iProductionJournalInterface;

    public String workOrderId;
    public String workOrderNo;

    public ArrayList<PickingModel> pickingModelList;
    public ArrayList<RouteModel> routeModelList;

    public ArrayList<PickingModel> addedPickingModelList;
    public ArrayList<RouteModel> addedRouteModelList;

    static ProductionJournal instance;
    AlertDialog.Builder builder;

    public static ProductionJournal getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production_journal);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        ButterKnife.bind(this);
        instance = this;
        setTitle(getString(R.string.production_journal));

        pickingModelList = new ArrayList<>();
        addedPickingModelList = new ArrayList<>();
        routeModelList = new ArrayList<>();

        iProductionJournalInterface = new IProductionJournalPresenter(this, this);
    }

    @OnTextChanged(value = R.id.editTextSearchItem, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanWorkOrderChanged(Editable text) {
        if (!text.toString().trim().equals("")) {
            scanWorkOrderhandler.postDelayed(stillageRunnable, delay);
        }

    }

    @OnTextChanged(value = R.id.editTextSearchItem, callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void onEditTextScanWorkOrderTEXTCHANGED(Editable text) {
        scanWorkOrderhandler.removeCallbacks(stillageRunnable);

    }

    //for call service on text change
    Handler scanWorkOrderhandler = new Handler();
    private Runnable stillageRunnable = new Runnable() {
        public void run() {
            if(editTextScanWorkOrder.getText().toString().trim().equalsIgnoreCase("wo-00001")){
                setData();
            }
//            if (NetworkChangeReceiver.isInternetConnected(ProductionJournal.this)) {
//                showProgress(ProductionJournal.this);
//                if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
//                    WorkOrderInput workOrderInput = new WorkOrderInput(userId, editTextScanWorkOrder.getText().toString().trim());
//                    iProductionJournalInterface.callScanWorkOrderService(workOrderInput);
//                }
//            } else {
//                CustomToast.showToast(ProductionJournal.this, getString(R.string.no_internet));
//            }
        }
    };

    @Override
    public void onFailure(String message) {
        hideProgress();
        CustomToast.showToast(this, message);
        editTextScanWorkOrder.setText("");
    }

    @Override
    public void onSuccess(WorkOrderResponse body) {
        hideProgress();
        if (body.getStatus().equals(getResources().getString(R.string.success))) {
//            setData(body);
        } else {
            CustomToast.showToast(getApplicationContext(), body.getMessage());
            editTextScanWorkOrder.setText("");
        }
    }

    void setData(/*WorkOrderResponse body*/) {
        cardView.setVisibility(View.VISIBLE);
        tabs.setVisibility(View.VISIBLE);
        view_pager.setVisibility(View.VISIBLE);
        linearLayoutButtons.setVisibility(View.VISIBLE);

        editTextScanWorkOrder.setEnabled(false);
        textViewWorkOrderNumber.setText("WO-00001");

//        textViewWorkOrderNumber.setText(body.getWorkOrderNo());
//        workOrderId = body.getWorkOrderId();
//        workOrderNo = body.getWorkOrderNo();

    }

    @OnClick(R.id.buttonConfirm)
    public void onButtonSubmitClick() {
        showConfirmationAlert();
    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        finish();
        startActivity(new Intent(ProductionJournal.this,ProductionJournal.class));
//        PickingListFragment.getInstance().clearInputs();
//        addedPickingModelList = new ArrayList<>();
//        PickingListFragment.getInstance().adapter.notifyDataSetChanged();
//        PickingListFragment.getInstance().clearPickingList();
//        editTextScanWorkOrder.setEnabled(true);
//        editTextScanWorkOrder.setText("");
//
//        cardView.setVisibility(View.GONE);
//        tabs.setVisibility(View.GONE);
//        view_pager.setVisibility(View.GONE);
//        linearLayoutButtons.setVisibility(View.GONE);
//        textViewWorkOrderNumber.setText("");
    }

    @Override
    public void onSubmitProcessFailure(String message) {
        hideProgress();
        CustomToast.showToast(this, message);
    }

    @Override
    public void onSubmitProcessSuccess(UniversalResponse body) {
        hideProgress();
        CustomToast.showToast(this, body.getMessage());
        disableViews();
    }

    public void disableViews() {
        editTextScanWorkOrder.setText("");
    }

    public void showConfirmationAlert() {
        builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.production_journal_submit_confirmation));
        builder.setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        WorkOrderSubmitInput workOrderSubmitInput = new WorkOrderSubmitInput(userId, workOrderNo, pickingModelList, routeModelList);
//                        showProgress(ProductionJournal.this);
//                        iProductionJournalInterface.callSubmitProductionJournalService(workOrderSubmitInput);
                        dialog.cancel();
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }



}