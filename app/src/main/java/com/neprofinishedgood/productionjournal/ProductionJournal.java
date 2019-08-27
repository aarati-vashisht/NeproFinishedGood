package com.neprofinishedgood.productionjournal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.productionjournal.model.ItemPicked;
import com.neprofinishedgood.productionjournal.model.PickingListDatum;
import com.neprofinishedgood.productionjournal.model.ProductionJournalRouteDataInput;
import com.neprofinishedgood.productionjournal.model.RouteCardPicked;
import com.neprofinishedgood.productionjournal.model.RoutingListDatum;
import com.neprofinishedgood.productionjournal.model.WorkOrderInput;
import com.neprofinishedgood.productionjournal.model.WorkOrderResponse;
import com.neprofinishedgood.productionjournal.presenter.IProductionJournalInterface;
import com.neprofinishedgood.productionjournal.presenter.IProductionJournalPresenter;
import com.neprofinishedgood.productionjournal.presenter.IProductionJournalView;
import com.neprofinishedgood.productionjournal.ui.main.PickingListFragment;
import com.neprofinishedgood.productionjournal.ui.main.RouteCardFragment;
import com.neprofinishedgood.productionjournal.ui.main.SectionsPagerAdapter;
import com.neprofinishedgood.utils.NetworkChangeReceiver;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class ProductionJournal extends BaseActivity implements IProductionJournalView {

    @BindView(R.id.editTextScanWorkOrder)
    public AppCompatEditText editTextScanWorkOrder;

    @BindView(R.id.linearLayoutWorkOrderNumber)
    public  LinearLayout linearLayoutWorkOrderNumber;

    @BindView(R.id.view_pager)
    public  ViewPager view_pager;

    @BindView(R.id.tabs)
    public TabLayout tabs;

    @BindView(R.id.textViewWorkOrderNumber)
    public  TextView textViewWorkOrderNumber;

    @BindView(R.id.textViewitem)
    public TextView textViewitem;

    @BindView(R.id.textViewItemId)
    public TextView textViewItemId;

    @BindView(R.id.textViewQuatity)
    public  TextView textViewQuatity;

    @BindView(R.id.cardView)
    public  CardView cardView;

    long scanStillageLastTexxt = 0;
    long delay = 1500;

    public IProductionJournalInterface iProductionJournalInterface;

    public String workOrderNo;

    public ArrayList<PickingListDatum> pickingListDatumList;
    public ArrayList<RoutingListDatum> routingListDatumList;

    public ArrayList<ItemPicked> addedPickingListDatumList;
    public ArrayList<RouteCardPicked> addedRoutingListDatumList;

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

        pickingListDatumList = new ArrayList<>();
        addedPickingListDatumList = new ArrayList<>();
        addedRoutingListDatumList = new ArrayList<>();
        routingListDatumList = new ArrayList<>();
        editTextScanWorkOrder.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        iProductionJournalInterface = new IProductionJournalPresenter(this, this);
    }

    @OnTextChanged(value = R.id.editTextScanWorkOrder, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanWorkOrderChanged(Editable text) {
        if (!text.toString().trim().equals("")) {
//            scanWorkOrderhandler.postDelayed(stillageRunnable, delay);
            if (text.toString().trim().length() == scanWorkOrderLength) {
                if (NetworkChangeReceiver.isInternetConnected(ProductionJournal.this)) {
                    showProgress(ProductionJournal.this);
                    if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
                        WorkOrderInput workOrderInput = new WorkOrderInput(userId, editTextScanWorkOrder.getText().toString().trim());
                        iProductionJournalInterface.callScanWorkOrderService(workOrderInput);
                    }
                } else {
                    CustomToast.showToast(ProductionJournal.this, getString(R.string.no_internet));
                }
            }
        }
    }

    @OnTextChanged(value = R.id.editTextScanWorkOrder, callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void onEditTextScanWorkOrderTEXTCHANGED(Editable text) {
//        scanWorkOrderhandler.removeCallbacks(stillageRunnable);

    }

//    //for call service on text change
//    Handler scanWorkOrderhandler = new Handler();
//    private Runnable stillageRunnable = new Runnable() {
//        public void run() {
//            if (NetworkChangeReceiver.isInternetConnected(ProductionJournal.this)) {
//                showProgress(ProductionJournal.this);
//                if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
//                    WorkOrderInput workOrderInput = new WorkOrderInput(userId, editTextScanWorkOrder.getText().toString().trim());
//                    iProductionJournalInterface.callScanWorkOrderService(workOrderInput);
//                }
//            } else {
//                CustomToast.showToast(ProductionJournal.this, getString(R.string.no_internet));
//            }
//        }
//    };

    void setData(WorkOrderResponse body) {
        cardView.setVisibility(View.VISIBLE);
        tabs.setVisibility(View.VISIBLE);
        view_pager.setVisibility(View.VISIBLE);

        editTextScanWorkOrder.setEnabled(false);
        textViewWorkOrderNumber.setText(body.getWorkOrderNo());
        textViewitem.setText(body.getItemName());
        textViewItemId.setText(body.getItemId());
        textViewQuatity.setText(body.getQuantity());
        pickingListDatumList = body.getPickingListData();
        pickingListDatumList.add(0, new PickingListDatum("", "", getResources().getString(R.string.select_item)));
        routingListDatumList = body.getRoutingListData();
        routingListDatumList.add(0, new RoutingListDatum(getResources().getString(R.string.select_operation), "", "", "", ""));
        workOrderNo = body.getWorkOrderNo();

    }

    public void disableViews() {
        editTextScanWorkOrder.setText("");
        cardView.setVisibility(View.GONE);
        tabs.setVisibility(View.GONE);
        view_pager.setVisibility(View.GONE);

        editTextScanWorkOrder.setEnabled(true);
        textViewWorkOrderNumber.setText("");
        textViewitem.setText("");
        textViewItemId.setText("");
        textViewQuatity.setText("");
        pickingListDatumList = new ArrayList<>();
        routingListDatumList = new ArrayList<>();
        addedPickingListDatumList = new ArrayList<>();
        addedRoutingListDatumList = new ArrayList<>();
        PickingListFragment.getInstance().clearInputs();
        RouteCardFragment.getInstance().clearInputs();
        PickingListFragment.getInstance().adapter.notifyDataSetChanged();
        RouteCardFragment.getInstance().adapter.notifyDataSetChanged();
        workOrderNo = "";
    }

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
            setData(body);
        } else {
            CustomToast.showToast(getApplicationContext(), body.getMessage());
            editTextScanWorkOrder.setText("");
        }
    }

    @Override
    public void onSubmitPickingProcessFailure(String message) {
        hideProgress();
        CustomToast.showToast(this, message);
    }

    @Override
    public void onSubmitPickingProcessSuccess(UniversalResponse body) {
        hideProgress();
        CustomToast.showToast(this, body.getMessage());
//        disableViews();
        finish();
        startActivity(new Intent(ProductionJournal.this, ProductionJournal.class));
    }

    @Override
    public void onSubmitRouteProcessFailure(String message) {
        hideProgress();
        CustomToast.showToast(this, message);
    }

    @Override
    public void onSubmitRouteProcessSuccess(UniversalResponse body) {
        hideProgress();
        CustomToast.showToast(this, body.getMessage());
//        disableViews();
        finish();
        startActivity(new Intent(ProductionJournal.this, ProductionJournal.class));
    }

    @Override
    public void onBackPressed() {
        showBackAlert();
    }

    @Override
    public void imageButtonBackClick(View view) {
        showBackAlert();
    }

    public void showBackAlert() {
        builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.production_journal_back_confirmation));
        builder.setMessage(getString(R.string.are_you_sure_you_want_to_go_back));
        builder.setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
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