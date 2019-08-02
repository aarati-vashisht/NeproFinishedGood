package com.neprofinishedgood.productionjournal;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.productionjournal.model.WorkOrderInput;
import com.neprofinishedgood.productionjournal.model.WorkOrderResponse;
import com.neprofinishedgood.productionjournal.presenter.IProductionJournalInterface;
import com.neprofinishedgood.productionjournal.presenter.IProductionJournalPresenter;
import com.neprofinishedgood.productionjournal.presenter.IProductionJournalView;
import com.neprofinishedgood.productionjournal.ui.main.SectionsPagerAdapter;
import com.neprofinishedgood.utils.NetworkChangeReceiver;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class ProductionJournal extends BaseActivity implements IProductionJournalView {

    @BindView(R.id.editTextSearchItem)
    AppCompatEditText editTextScanWorkOrder;

    @BindView(R.id.linearLayoutWorkOrderNumber)
    LinearLayout linearLayoutWorkOrderNumber;

    @BindView(R.id.view_pager)
    ViewPager view_pager;

    @BindView(R.id.tabs)
    TabLayout tabs;

    @BindView(R.id.buttonSubmit)
    CustomButton buttonSubmit;

    @BindView(R.id.textViewWorkOrderNumber)
    TextView textViewWorkOrderNumber;

    long scanStillageLastTexxt = 0;
    long delay = 1500;

    IProductionJournalInterface iProductionJournalInterface;

    public String workOrderId;
    public String workOrderNo;

    static ProductionJournal instance;

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
        CustomButton buttonSubmit = findViewById(R.id.buttonSubmit);
        ButterKnife.bind(this);
        instance = this;
        setTitle(getString(R.string.production_journal));

        iProductionJournalInterface = new IProductionJournalPresenter(this, this);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                CustomToast.showToast(ProductionJournal.this, "SubmitClicked");
            }
        });
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
                if (NetworkChangeReceiver.isInternetConnected(ProductionJournal.this)) {
                    showProgress(ProductionJournal.this);
                    if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
                        WorkOrderInput workOrderInput = new WorkOrderInput(userId,editTextScanWorkOrder.getText().toString().trim() );
                        iProductionJournalInterface.callScanWorkOrderService(workOrderInput);
                    }
                } else {
                    CustomToast.showToast(ProductionJournal.this, getString(R.string.no_internet));
                }
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
        // initData();
        if (body.getStatus().equals(getResources().getString(R.string.success))) {
            setData(body);
        } else {
            CustomToast.showToast(getApplicationContext(), body.getMessage());
            editTextScanWorkOrder.setText("");
        }
    }

    void setData(WorkOrderResponse body) {
        linearLayoutWorkOrderNumber.setVisibility(View.VISIBLE);
        tabs.setVisibility(View.VISIBLE);
        view_pager.setVisibility(View.VISIBLE);
        buttonSubmit.setVisibility(View.VISIBLE);

        editTextScanWorkOrder.setEnabled(false);

        textViewWorkOrderNumber.setText(body.getWorkOrderNo());

        workOrderId = body.getWorkOrderId();
        workOrderNo = body.getWorkOrderNo();
    }
}