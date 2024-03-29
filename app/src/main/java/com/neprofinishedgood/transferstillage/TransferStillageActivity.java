package com.neprofinishedgood.transferstillage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neprofinishedgood.R;
import com.neprofinishedgood.assigntransfer.AssignTransferActivity;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.base.model.UniversalSpinner;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.dashboard.DashBoardAcivity;
import com.neprofinishedgood.move.adapter.SpinnerAdapter;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.transferstillage.adapter.TransferAdapter;
import com.neprofinishedgood.transferstillage.model.LocationResponse;
import com.neprofinishedgood.transferstillage.model.LocationsInput;
import com.neprofinishedgood.transferstillage.model.ShipInput;
import com.neprofinishedgood.transferstillage.model.TransferStillageDetail;
import com.neprofinishedgood.transferstillage.model.TransferInput;
import com.neprofinishedgood.transferstillage.model.WareHouseInput;
import com.neprofinishedgood.transferstillage.model.WareHouseResponse;
import com.neprofinishedgood.transferstillage.presenter.ITransferInterface;
import com.neprofinishedgood.transferstillage.presenter.ITransferView;
import com.neprofinishedgood.transferstillage.presenter.TransferPresenter;
import com.neprofinishedgood.utils.NetworkChangeReceiver;
import com.neprofinishedgood.utils.SharedPref;
import com.neprofinishedgood.utils.StillageLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class TransferStillageActivity extends BaseActivity implements ITransferView {

    @BindView(R.id.relativeLayoutScanDetail)
    RelativeLayout relativeLayoutScanDetail;


    @BindView(R.id.buttonTransfer)
    CustomButton buttonTransfer;

    @BindView(R.id.editTextScanStillage)
    AppCompatEditText editTextScanStillage;

    @BindView(R.id.stillageDetail)
    View stillageDetail;

    @BindView(R.id.spinnerWarehouse)
    Spinner spinnerWarehouse;

    @BindView(R.id.spinnerSite)
    Spinner spinnerSite;

    @BindView(R.id.linearLayoutOfflineData)
    LinearLayout linearLayoutOfflineData;

    @BindView(R.id.linearLayoutToWarehouse)
    LinearLayout linearLayoutToWarehouse;

    @BindView(R.id.linearLayoutToSite)
    LinearLayout linearLayoutToSite;

    @BindView(R.id.linearLayoutRecyclerView)
    LinearLayout linearLayoutRecyclerView;

    @BindView(R.id.linearLayoutLocationSelection)
    LinearLayout linearLayoutLocationSelection;

    @BindView(R.id.textViewToWarehouse)
    TextView textViewToWarehouse;

    @BindView(R.id.textViewNumberOffline)
    TextView textViewNumberOffline;

    @BindView(R.id.recyclerViewTransferList)
    RecyclerView recyclerViewTransferList;

    private TransferAdapter adapter;

    StillageLayout stillageLayout;


    long delay = 1000;
    long scanStillageLastTexxt = 0;

    private ITransferInterface iTransferInterface;
    private String warehouseId = "", siteId = "", locationId;
    private String stillageWarehouseId = "";

    ArrayList<UniversalSpinner> siteList;
    ArrayList<UniversalSpinner> warehouseList;
    List<UniversalSpinner> locationList;

    public ArrayList<TransferStillageDetail> stickersList;
    ArrayList<ScanStillageResponse> stillageDetailsList;

    static TransferStillageActivity instance;
    private String transferId;
    private String toBeTransferWHID = "", toBeTransferLocationId;
    String makeTJ = "1";

    boolean isTransferMultiple = true;

    String unShipedStillages[];

    Spinner spinnerWarehouseDialog;
    Spinner spinnerLocationDialog;

    public static TransferStillageActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_stillage);
        ButterKnife.bind(this);
        instance = this;
        stillageLayout = new StillageLayout();
        if (isTransferMultiple) {
            ButterKnife.bind(this);
            setAdapter();
            onEditTextScanStillageChanged();
        } else {
            ButterKnife.bind(stillageLayout, stillageDetail);
            onEditTextScanStillageChangedSingle();
        }
        setTitle(getString(R.string.transfer));
        iTransferInterface = new TransferPresenter(this, this);
//        initData();
        editTextScanStillage.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        callService();
        alert_TO_TJ_selection(this);

    }

    public void alert_TO_TJ_selection(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_alert_to_tj_selection);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        CustomButton buttonOk = dialog.findViewById(R.id.buttonOk);
        RadioGroup radioGroupTransferType = dialog.findViewById(R.id.radioGroupTransferType);
        RadioButton radioButtonTJ = dialog.findViewById(R.id.radioButtonTJ);
        RadioButton radioButtonTO = dialog.findViewById(R.id.radioButtonTO);
        ImageView imgBackButton = dialog.findViewById(R.id.imgBackButton);
        radioGroupTransferType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == radioButtonTJ.getId()) {
                makeTJ = "1";
                setTitle(getString(R.string.transfer_journal_process));
            } else if (checkedId == radioButtonTO.getId()) {
                makeTJ = "0";
                setTitle(getString(R.string.transfer_order_process));
            }
        });

        radioButtonTJ.setChecked(true);

        buttonOk.setOnClickListener(v -> {
            dialog.cancel();
        });

        imgBackButton.setOnClickListener(v -> {
            dialog.cancel();
            finish();
        });

        dialog.show();
    }

//    private void initData() {
//        SpinnerAdapter reasonListAdapter = new SpinnerAdapter(TransferStillageActivity.this, R.layout.spinner_layout, warehouseList);
//        spinnerWarehouse.setAdapter(reasonListAdapter);
//    }

    private void setAdapter() {

        stickersList = new ArrayList<>();
        stillageDetailsList = new ArrayList<>();

        recyclerViewTransferList.setVisibility(View.VISIBLE);
        adapter = new TransferAdapter(stillageDetailsList);
        recyclerViewTransferList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewTransferList.setAdapter(adapter);
        recyclerViewTransferList.setHasFixedSize(true);
    }

    @OnItemSelected(R.id.spinnerWarehouse)
    public void onSpinnerWarehouseSelected(Spinner spinner, int position) {
        warehouseId = warehouseList.get(position).getId().trim();
//        if (isTransferMultiple) {
//            boolean isSameWarehouse = false;
//            for (int i = 0; i < scannedWarehouseList.size(); i++) {
//                if (scannedWarehouseList.get(i).equals(warehouseId)) {
//                    isSameWarehouse = true;
//                    showSuccessDialog(getResources().getString(R.string.cannot_transfer));
//                    break;
//                }
//            }
//            if (isSameWarehouse) {
//                buttonTransfer.setEnabled(false);
//            } else {
//                buttonTransfer.setEnabled(true);
//            }
//        } else {
        if (warehouseId.equalsIgnoreCase(stillageWarehouseId)) {
            showSuccessDialog(getResources().getString(R.string.cannot_transfer));
            buttonTransfer.setEnabled(false);
        } else {
            buttonTransfer.setEnabled(true);
        }
//        }
    }

    @OnItemSelected(R.id.spinnerSite)
    public void onSpinnerSiteSelected(Spinner spinner, int position) {
        siteId = siteList.get(position).getId().trim();
        if (position > 0) {
            if (NetworkChangeReceiver.isInternetConnected(TransferStillageActivity.this)) {
                showProgress(this);
                iTransferInterface.callGetWareHouse(new WareHouseInput(siteId));
            } else {
                showSuccessDialog(getString(R.string.no_internet));
            }
        }
    }

    public void onEditTextScanStillageChanged() {
        editTextScanStillage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable text) {
                if (!text.toString().trim().equals("")) {
//            scanStillagehandler.postDelayed(stillageRunnable, delay);
                    boolean isStillageExist = false;
                    for (int i = 0; i < stickersList.size(); i++) {
                        if (stickersList.get(i).getStillageID().equals(text.toString().trim())) {
                            isStillageExist = true;
                            showSuccessDialog(getResources().getString(R.string.already_scanned));
                            editTextScanStillage.setText("");
                            break;
                        }
                    }
                    if (!isStillageExist) {
                        if (text.toString().trim().length() == scanStillageLength) {
                            if (NetworkChangeReceiver.isInternetConnected(TransferStillageActivity.this)) {
                                showProgress(TransferStillageActivity.this);
                                iTransferInterface.callScanStillageService(new MoveInput(editTextScanStillage.getText().toString().trim(), userId));
                            } else {
                                showSuccessDialog(getString(R.string.no_internet));
                            }
//                            } else {
//                                setDataOffline();
//                            }
                        }
                    }
                }
            }
        });
    }

    public void onEditTextScanStillageChangedSingle() {
        editTextScanStillage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable text) {
                if (!text.toString().trim().equals("")) {
//            scanStillagehandler.postDelayed(stillageRunnable, delay);

                    if (text.toString().trim().length() == scanStillageLength) {
                        if (NetworkChangeReceiver.isInternetConnected(TransferStillageActivity.this)) {
                            showProgress(TransferStillageActivity.this);
                            iTransferInterface.callScanStillageService(new MoveInput(editTextScanStillage.getText().toString().trim(), userId));
                        } else {
                            showSuccessDialog(getString(R.string.no_internet));
                            editTextScanStillage.setText("");
                        }
//                        } else {
//                            setDataOffline();
//                        }
                    }
                }
            }
        });
    }

//    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
//    public void onEditTextScanStillageChanged(Editable text) {
//        if (!text.toString().trim().equals("")) {
////            scanStillagehandler.postDelayed(stillageRunnable, delay);
//            if (text.toString().trim().length() == scanStillageLength) {
//                if (NetworkChangeReceiver.isInternetConnected(TransferStillageActivity.this)) {
//                    showProgress(TransferStillageActivity.this);
//                    iTransferInterface.callScanStillageService(new MoveInput(editTextScanStillage.getText().toString().trim(), userId));
//                } else {
//                    setDataOffline();
//                }
//            }
//        }
//    }

//    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.TEXT_CHANGED)
//    public void onEditTextScanStillageTEXTCHANGED(Editable text) {
//        scanStillagehandler.removeCallbacks(stillageRunnable);
//
//    }
//
//    //for call service on text change
//    Handler scanStillagehandler = new Handler();
//    private Runnable stillageRunnable = new Runnable() {
//        public void run() {
//            if (NetworkChangeReceiver.isInternetConnected(TransferStillageActivity.this)) {
//                showProgress(TransferStillageActivity.this);
//                if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
//                    iTransferInterface.callScanStillageService(new AisleInput(editTextScanStillage.getText().toString().trim(), userId));
//
//                }
//            } else {
//                setDataOffline();
//            }
//        }
//    };

    public void imageButtonCloseClick(View view) {
        relativeLayoutScanDetail.startAnimation(fadeOut);
        relativeLayoutScanDetail.setVisibility(View.GONE);
        editTextScanStillage.setText("");
        editTextScanStillage.setEnabled(true);

    }

    @Override
    public void onFailure(String message) {
        hideProgress();
        showSuccessDialog(message);
        editTextScanStillage.setText("");
//        CustomToast.showToast(this, message);
    }

    @Override
    public void onSuccess(ScanStillageResponse body) {
        try {
            hideProgress();
            if (body.getStatus().equalsIgnoreCase(getString(R.string.success))) {
                if (isLocationMatched(body.getWareHouseID())) {
                    /*if (body.getIsTJ().equals("1")) {
                        showSuccessDialog(getString(R.string.this_stillage_already_tj_transfred));
                        editTextScanStillage.setText("");
                    } else */
                    if (!body.getTransferId().equals("") && body.getIsShiped().equals("1")) {
                        showSuccessDialog(getString(R.string.this_stillage_already_to_transfred));
                        editTextScanStillage.setText("");
                    } else if (body.getIsCounted().equals("0")) {
                        showSuccessDialog(getString(R.string.raf_not_posted));
                        editTextScanStillage.setText("");
                    } else {
                        if (body.getStandardQty() > 0) {
                            if (stillageWarehouseId == "") {
                                stillageWarehouseId = body.getWareHouseID().trim();
                            }
                            if (stillageWarehouseId.equals(body.getWareHouseID().trim())) {
                                isScanned = true;
                                if (isTransferMultiple) {

                                    if (makeTJ.equals("0")) {
                                        boolean isSameTransWH = true;
                                        if (!body.getTransferId().equals("")) {
                                            if (toBeTransferWHID.equals("")) {
                                                toBeTransferWHID = body.getToBeTransferWHID();
                                                toBeTransferLocationId = body.getToBeTransferLocationId();
                                                isSameTransWH = true;
                                            } else if (!toBeTransferWHID.equals(body.getToBeTransferWHID())) {
                                                showSuccessDialog(getString(R.string.warehouse_differ));
                                                isSameTransWH = false;
                                            }
                                        }
                                        if (isSameTransWH) {
                                            setData(body);
                                        }
                                    } else {
                                        if (!body.getTransferId().equals("")) {
                                            showSuccessDialog(getString(R.string.transferred_to_another_wh));
                                            editTextScanStillage.setText("");
                                        } else {
                                            setData(body);
                                        }
                                    }
                                } else {
                                    setDataSingle(body);
                                }

                            } else {
                                showSuccessDialog("Can't scan stillage of different warehouse.");
                                editTextScanStillage.setText("");
                            }
                        } else {
                            showSuccessDialog(getResources().getString(R.string.stillage_discarded));
                            editTextScanStillage.setText("");
                        }
                    }
                } else {
                    editTextScanStillage.setText("");
                    showSuccessDialog(getResources().getString(R.string.stillage_not_found));
                }
            } else {
                showSuccessDialog(body.getMessage());
                editTextScanStillage.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
            hideProgress();
            showSuccessDialog(getString(R.string.data_error));
            editTextScanStillage.setText("");
        }
    }

    @Override
    public void onUpdateTransferFailure(String message) {
        hideProgress();
        if (relativeLayoutScanDetail.getVisibility() == View.VISIBLE) {
            showSuccessDialog(message);
        }
    }

    @Override
    public void onUpdateTransferSuccess(UniversalResponse body) {
        hideProgress();

        if (isTransferMultiple) {
            if (relativeLayoutScanDetail.getVisibility() == View.VISIBLE) {
                if (body.getStatus().equalsIgnoreCase(getString(R.string.success))) {
                    if (body.getStillageNotSH() != null) {
                        if (!body.getStillageNotSH().equals("")) {
                            unShippedStillgesFound(body);
                        } else {
                            showSuccessDialog(body.getMessage());
                            cancelClick();
                        }
                    } else {
                        showSuccessDialog(body.getMessage());
                        cancelClick();
                    }
                } else {
                    showSuccessDialog(body.getMessage());

                }
            }
        } else {
            if (relativeLayoutScanDetail.getVisibility() == View.VISIBLE) {
                showSuccessDialog(body.getMessage());
                if (body.getTransferId().equals("")) {
                    cancelClick();
                } else {
                    linearLayoutToSite.setVisibility(View.GONE);
                    linearLayoutToWarehouse.setVisibility(View.GONE);
                    textViewToWarehouse.setVisibility(View.GONE);
                    buttonTransfer.setText(getString(R.string.ship));
                    transferId = body.getTransferId();
                }
            }
        }
    }

    void unShippedStillgesFound(UniversalResponse body) {
        ArrayList<TransferStillageDetail> stickersListLocal = new ArrayList<>();
        ArrayList<ScanStillageResponse> stillageDetailsListLocal = new ArrayList<>();
        unShipedStillages = body.getStillageNotSH().split(",");
        showSuccessDialog("Some stillages have not been shipped");
        for (int i = 0; i < unShipedStillages.length; i++) {
            for (int j = 0; j < unShipedStillages.length; j++) {
                if (unShipedStillages[i].equals(stickersList.get(j).getStillageID())) {
                    stickersListLocal.add(stickersList.get(j));
                }
                if (unShipedStillages[i].equals(stillageDetailsList.get(j).getStickerID())) {
                    stillageDetailsListLocal.add(stillageDetailsList.get(j));
                }
            }
        }
        editTextScanStillage.setEnabled(false);
        stickersList = stickersListLocal;
        stillageDetailsList = stillageDetailsListLocal;
        adapter.notifyDataSetChanged();
        adapter.lockDelete = true;
        buttonTransfer.setVisibility(View.GONE);
    }

    @Override
    public void onShipFailure(String message) {
        hideProgress();
        showSuccessDialog(message);
    }

    @Override
    public void onShipSuccess(UniversalResponse body) {
        hideProgress();
        showSuccessDialog(body.getMessage());
        if (body.getStatus().equals(getString(R.string.success))) {
            cancelClick();
        }
    }

    @Override
    public void onGetWareHouseFailure(String message) {
        hideProgress();
        showSuccessDialog(message);
    }

    @Override
    public void onGetWareHouseSuccess(WareHouseResponse body) {
        hideProgress();
        SpinnerAdapter wareHouseListAdapter;
        if (body.getStatus().equals(getString(R.string.success))) {
            if (body.getWareHouseListData() != null) {
                warehouseList = body.getWareHouseListData();
            } else {
                warehouseList = new ArrayList<>();
            }
            warehouseList.add(0, new UniversalSpinner("Select Warehouse", "0"));
            wareHouseListAdapter = new SpinnerAdapter(TransferStillageActivity.this, R.layout.spinner_layout, warehouseList);
            spinnerWarehouseDialog.setAdapter(wareHouseListAdapter);

        } else {
            showSuccessDialog(body.getMessage());
        }
    }

    @Override
    public void onGetLocationFailure(String message) {
        hideProgress();
        showSuccessDialog(message);
    }

    @Override
    public void onGetLocationSuccess(LocationResponse body) {
        hideProgress();
        SpinnerAdapter locationListAdapter;
        if (body.getStatus().equals(getString(R.string.success))) {
            if (body.getLocationData() != null) {
                locationList = body.getLocationData();
            } else {
                locationList = new ArrayList<>();
            }
            locationList.add(0, new UniversalSpinner("Select Location", "0"));
            locationListAdapter = new SpinnerAdapter(TransferStillageActivity.this, R.layout.spinner_layout, locationList);
            spinnerLocationDialog.setAdapter(locationListAdapter);

        } else {
            showSuccessDialog(body.getMessage());
        }
    }

    void clearViews() {
        isScanned = false;
        editTextScanStillage.setEnabled(true);
        editTextScanStillage.setText("");
        editTextScanStillage.requestFocus();
        stillageDetail.setVisibility(View.GONE);
        stillageDetail.setAnimation(fadeOut);
        relativeLayoutScanDetail.setVisibility(View.GONE);
    }

    void setData(ScanStillageResponse body) {
        try {
            linearLayoutLocationSelection.setVisibility(View.GONE);
            linearLayoutRecyclerView.setVisibility(View.VISIBLE);
            stillageDetail.setVisibility(View.GONE);
            editTextScanStillage.setText("");
            editTextScanStillage.setEnabled(true);

            stillageWarehouseId = body.getWareHouseID().trim();
            relativeLayoutScanDetail.setVisibility(View.VISIBLE);

            stillageDetailsList.add(body);
            adapter.notifyDataSetChanged();

            String transId;
            if (body.getTransferId() != null) {
                transId = body.getTransferId();
            } else {
                transId = "";
            }

            stickersList.add(new TransferStillageDetail(body.getStickerID(), transId));

            if (siteList == null) {
                siteList = new ArrayList<>();
            }

            if (siteList.isEmpty()) {
                siteList = body.getSiteListData();
                siteList.add(0, new UniversalSpinner("Select Site", "0"));

                if (siteList != null) {
                    SpinnerAdapter siteAdapter = new SpinnerAdapter(TransferStillageActivity.this, R.layout.spinner_layout, siteList);
                    spinnerSite.setAdapter(siteAdapter);
                }
            }
//-------------------------------- it may change ----------------------------------------
//        if (body.getIsShiped().equals("0") && !body.getTransferId().equals("")) {
//            linearLayoutToSite.setVisibility(View.GONE);
//            linearLayoutToWarehouse.setVisibility(View.GONE);
//            textViewToWarehouse.setVisibility(View.GONE);
//            buttonTransfer.setText(getString(R.string.ship));
//            transferId = body.getTransferId();
//        } else if (body.getIsShiped().equals("0") && body.getTransferId().equals("")) {
//            linearLayoutToSite.setVisibility(View.VISIBLE);
//            linearLayoutToWarehouse.setVisibility(View.VISIBLE);
//            textViewToWarehouse.setVisibility(View.VISIBLE);
//            buttonTransfer.setText(getString(R.string.transfer));
//        }
//---------------------------------------------------------------------------------------
        } catch (Exception e) {
            e.printStackTrace();
            showSuccessDialog(getString(R.string.data_error));
            editTextScanStillage.setText("");
        }
    }

    void setDataSingle(ScanStillageResponse body) {
        try {
            stickersList = new ArrayList<>();
            linearLayoutRecyclerView.setVisibility(View.GONE);
            stillageDetail.setVisibility(View.VISIBLE);

            editTextScanStillage.setEnabled(false);
            stillageWarehouseId = body.getWareHouseID().trim();
            stillageDetail.setVisibility(View.VISIBLE);
            stillageDetail.setAnimation(fadeIn);
            relativeLayoutScanDetail.setVisibility(View.VISIBLE);

            stillageLayout.linearLayoutLocation.setVisibility(View.VISIBLE);
            stillageLayout.textViewLocation.setText(body.getLocation());
            stillageLayout.linearLayoutWarehouse.setVisibility(View.VISIBLE);
            stillageLayout.textViewWarehouse.setText(body.getWareHouseID() + " | " + body.getWareHouseName());
            stillageLayout.textViewitem.setText(body.getItemId());
            stillageLayout.textViewNumber.setText(body.getStickerID());
            stillageLayout.textViewQuantity.setText(body.getStandardQty() + "");
            stillageLayout.textViewStdQuantity.setText(body.getItemStdQty() + "");
            stillageLayout.textViewitemDesc.setText(body.getDescription());

            siteList = body.getSiteListData();
            siteList.add(0, new UniversalSpinner("Select Site", "0"));

            if (siteList != null) {
                SpinnerAdapter siteAdapter = new SpinnerAdapter(TransferStillageActivity.this, R.layout.spinner_layout, siteList);
                spinnerSite.setAdapter(siteAdapter);
            }

            if (body.getIsShiped().equals("0") && !body.getTransferId().equals("")) {
                linearLayoutToSite.setVisibility(View.GONE);
                linearLayoutToWarehouse.setVisibility(View.GONE);
                textViewToWarehouse.setVisibility(View.GONE);
                buttonTransfer.setText(getString(R.string.ship));
                transferId = body.getTransferId();
            } else if (body.getIsShiped().equals("0") && body.getTransferId().equals("")) {
                linearLayoutToSite.setVisibility(View.VISIBLE);
                linearLayoutToWarehouse.setVisibility(View.VISIBLE);
                textViewToWarehouse.setVisibility(View.VISIBLE);
                buttonTransfer.setText(getString(R.string.transfer));
            }
        } catch (Exception e) {
            e.printStackTrace();
            showSuccessDialog(getString(R.string.data_error));
            editTextScanStillage.setText("");
        }
    }

    public void deleteAddedData(int position) {
        stickersList.remove(position);
        stillageDetailsList.remove(position);
        adapter.notifyDataSetChanged();
        if (stickersList.isEmpty()) {
            cancelClick();
        }

        boolean isTransIdFound = false;
        for (int i = 0; i < stillageDetailsList.size(); i++) {
            if (!stillageDetailsList.get(i).getTransferId().equals("")) {
                isTransIdFound = true;
                break;
            } else {
                isTransIdFound = false;
            }
        }
        if (!isTransIdFound) {
            toBeTransferWHID = "";
            toBeTransferLocationId = "";
        }
    }

    @OnClick(R.id.buttonTransfer)
    public void onButtonDropClick() {

        if (isTransferMultiple) {
            if (linearLayoutOfflineData.getVisibility() == View.GONE) {
                if (toBeTransferWHID.equals("")) {
                    alertTransferWarehouseSelection(TransferStillageActivity.this);
                } else {
                    showTransferAlert();
                }
//                    if (spinnerWarehouse.getSelectedItemPosition() > 0) {
//                        showProgress(this);
//                        TransferInput transferInput = new TransferInput("", stickersList, warehouseId, userId);
//                        iTransferInterface.callNewTranferStillage(transferInput);
//                    } else {
//                        showSuccessDialog("Select site and warehouse");
//                    }

            } else {
                if (isOfflineValidated()) {
                    TransferInput transferInput = new TransferInput("", stickersList, warehouseId, userId, makeTJ, siteId, locationId);
                    saveDataOffline(transferInput);
                }
            }
        } else {
            if (linearLayoutOfflineData.getVisibility() == View.GONE) {
                if (buttonTransfer.getText().toString().equals(getString(R.string.transfer))) {
                    if (spinnerWarehouse.getSelectedItemPosition() > 0) {
                        if (NetworkChangeReceiver.isInternetConnected(TransferStillageActivity.this)) {
                            showProgress(this);
                            TransferInput transferInput = new TransferInput(editTextScanStillage.getText().toString().trim(), stickersList, warehouseId, userId, "", "", "");
                            iTransferInterface.callUpdateTransferStillage(transferInput);
                        } else {
                            showSuccessDialog(getString(R.string.no_internet));
                        }
                    } else {
                        showSuccessDialog("Select site and warehouse");
                    }
                } else if (buttonTransfer.getText().toString().equals(getString(R.string.ship))) {
                    if (NetworkChangeReceiver.isInternetConnected(TransferStillageActivity.this)) {
                        showProgress(this);
                        ShipInput shipInput = new ShipInput(editTextScanStillage.getText().toString().trim(), userId, transferId);
                        iTransferInterface.callShipStillage(shipInput);
                    } else {
                        showSuccessDialog(getString(R.string.no_internet));
                    }
                }
            } else {
                if (isOfflineValidated()) {
                    TransferInput transferInput = new TransferInput(editTextScanStillage.getText().toString().trim(), stickersList, warehouseId, userId, "", "", "");
                    saveDataOffline(transferInput);
                }
            }
        }
    }

    public void alertTransferWarehouseSelection(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_alert_warehouse_selection);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        CustomButton buttonTransfer = dialog.findViewById(R.id.buttonTransfer);
        CustomButton buttonCancel = dialog.findViewById(R.id.buttonCancel);
        Spinner spinnerSite = dialog.findViewById(R.id.spinnerSite);
        spinnerWarehouseDialog = dialog.findViewById(R.id.spinnerWarehouse);
        spinnerLocationDialog = dialog.findViewById(R.id.spinnerLocation);
        if (siteList != null) {
            SpinnerAdapter siteAdapter = new SpinnerAdapter(TransferStillageActivity.getInstance(), R.layout.spinner_layout, siteList);
            spinnerSite.setAdapter(siteAdapter);
        }

        spinnerSite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position <= 0) {
                    buttonTransfer.setEnabled(false);
                    warehouseList = new ArrayList<>();
                    spinnerWarehouseDialog.setAdapter(new SpinnerAdapter(TransferStillageActivity.this, R.layout.spinner_layout, warehouseList));
                    locationList = new ArrayList<>();
                    spinnerLocationDialog.setAdapter(new SpinnerAdapter(TransferStillageActivity.this, R.layout.spinner_layout, locationList));
                } else {
                    siteId = siteList.get(position).getId().trim();
                    if (NetworkChangeReceiver.isInternetConnected(TransferStillageActivity.this)) {
                        showProgress(TransferStillageActivity.getInstance());
                        iTransferInterface.callGetWareHouse(new WareHouseInput(siteId));
                    } else {
                        showSuccessDialog(getString(R.string.no_internet));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        spinnerWarehouseDialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position > 0) {
//                    warehouseId = warehouseList.get(position).getId().trim();
//                    if (warehouseId.equalsIgnoreCase(stillageWarehouseId)) {
//                        showSuccessDialog(getResources().getString(R.string.cannot_transfer));
//                        buttonTransfer.setEnabled(false);
//                    } else {
//                        buttonTransfer.setEnabled(true);
//                    }
//                } else {
//                    buttonTransfer.setEnabled(false);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        spinnerWarehouseDialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    warehouseId = warehouseList.get(position).getId().trim();
                    if (warehouseId.equalsIgnoreCase(stillageWarehouseId)) {
                        locationList = new ArrayList<>();
                        spinnerLocationDialog.setAdapter(new SpinnerAdapter(TransferStillageActivity.this, R.layout.spinner_layout, locationList));
                        showSuccessDialog(getResources().getString(R.string.cannot_transfer));
                        buttonTransfer.setEnabled(false);
                    } else {
                        if (NetworkChangeReceiver.isInternetConnected(TransferStillageActivity.this)) {
                            showProgress(TransferStillageActivity.getInstance());
                            iTransferInterface.callGetLocation(new LocationsInput(warehouseId, userId));
                        } else {
                            showSuccessDialog(getString(R.string.no_internet));
                        }
                    }
                } else {
                    locationList = new ArrayList<>();
                    spinnerLocationDialog.setAdapter(new SpinnerAdapter(TransferStillageActivity.this, R.layout.spinner_layout, locationList));
                    buttonTransfer.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerLocationDialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    locationId = locationList.get(position).getId().trim();
                    buttonTransfer.setEnabled(true);
                } else {
                    buttonTransfer.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        buttonTransfer.setOnClickListener(v -> {
            if (spinnerWarehouseDialog.getSelectedItemPosition() > 0) {
                if (NetworkChangeReceiver.isInternetConnected(TransferStillageActivity.this)) {
                    showProgress(this);
                    TransferInput transferInput = new TransferInput("", stickersList, warehouseId, userId, makeTJ, siteId, locationId);
                    String json= new Gson().toJson(transferInput);
                    iTransferInterface.callNewTranferStillage(transferInput);
                    dialog.cancel();
                } else {
                    showSuccessDialog(getString(R.string.no_internet));
                }
            } else {
                showSuccessDialog("Select site and warehouse");
            }
        });

        buttonCancel.setOnClickListener(v -> {
            dialog.cancel();
        });

        dialog.show();

    }

    public void showTransferAlert() {
        builder = new AlertDialog.Builder(this);
        builder.setMessage("These stillages will be transfered to " + toBeTransferWHID + " warehouse at " + toBeTransferLocationId + " location! ");
        builder.setCancelable(false)
                .setPositiveButton(getString(R.string.yes), (dialog, id) -> {
                    if (NetworkChangeReceiver.isInternetConnected(TransferStillageActivity.this)) {
                        showProgress(TransferStillageActivity.this);
                        TransferInput transferInput = new TransferInput("", stickersList, toBeTransferWHID, userId, makeTJ, siteId, toBeTransferLocationId);
                        iTransferInterface.callNewTranferStillage(transferInput);
                        dialog.cancel();
                    } else {
                        showSuccessDialog(getString(R.string.no_internet));
                    }
                })
                .setNegativeButton(getString(R.string.no), (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        showCancelAlert(5);
    }

    public void cancelClick() {
        toBeTransferWHID = "";
        toBeTransferLocationId= "";
        adapter.lockDelete = false;
        buttonTransfer.setVisibility(View.VISIBLE);
        stillageWarehouseId = "";
        warehouseId = "";
        siteId = "";
        siteList = new ArrayList<>();
        warehouseList = new ArrayList<>();
        setAdapter();
        isScanned = false;
        SpinnerAdapter wareHouseListAdapter;
        warehouseList = new ArrayList<>();
        wareHouseListAdapter = new SpinnerAdapter(TransferStillageActivity.this, R.layout.spinner_layout, warehouseList);
        spinnerWarehouse.setAdapter(wareHouseListAdapter);
        editTextScanStillage.setText("");
        editTextScanStillage.setEnabled(true);
        editTextScanStillage.requestFocus();
        relativeLayoutScanDetail.setVisibility(View.GONE);
        relativeLayoutScanDetail.setAnimation(fadeOut);
        linearLayoutOfflineData.setVisibility(View.GONE);
        linearLayoutOfflineData.setAnimation(fadeOut);
    }

    public void imageButtonHomeClick(View view) {
        if (isScanned) {
            showBackAlert(new Intent(TransferStillageActivity.this, DashBoardAcivity.class), true);
        } else {
            finishAffinity();
            startActivity(new Intent(TransferStillageActivity.this, DashBoardAcivity.class));
        }
    }

    public void imageButtonBackClick(View view) {
        if (isScanned) {
            showBackAlert(null, false);
        } else {
            finish();
        }
    }

    public void onBackPressed() {
        if (isScanned) {
            showBackAlert(null, false);
        } else {
            finish();
        }
    }

    void setDataOffline() {
        textViewNumberOffline.setText(editTextScanStillage.getText().toString());
        setVisibilityInOfflineMode();
//        initData();

    }

    void setVisibilityInOfflineMode() {
        editTextScanStillage.setEnabled(false);
        linearLayoutOfflineData.setVisibility(View.VISIBLE);
        stillageDetail.setVisibility(View.GONE);
        relativeLayoutScanDetail.setVisibility(View.VISIBLE);
    }

    void disableVisibility() {
        relativeLayoutScanDetail.setVisibility(View.GONE);
        editTextScanStillage.setEnabled(true);
        editTextScanStillage.setText("");
        linearLayoutOfflineData.setVisibility(View.GONE);
        stillageDetail.setVisibility(View.VISIBLE);
    }

    void saveDataOffline(TransferInput data) {
        ArrayList<TransferInput> transferList = new ArrayList<>();
        Gson gson = new Gson();
        String transferData = SharedPref.getTransferData();
        if (!transferData.equals("")) {
            Type type = new TypeToken<ArrayList<TransferInput>>() {
            }.getType();
            transferList = gson.fromJson(transferData, type);
        }
        transferList.add(data);
        String json = gson.toJson(transferList);
        SharedPref.saveTransferData(json);
        showSuccessDialog(getResources().getString(R.string.data_saved_offline));
//        CustomToast.showToast(this, getResources().getString(R.string.data_saved_offline));
        disableVisibility();
    }

    boolean isOfflineValidated() {
        if (spinnerWarehouse.getSelectedItemPosition() == 0) {
            TextView textView = (TextView) spinnerWarehouse.getSelectedView();
            textView.setError(getString(R.string.select_warehouse));
            textView.requestFocus();
            return false;
        }
        return true;
    }

    public void callService() {
        if (NetworkChangeReceiver.isInternetConnected(TransferStillageActivity.this)) {
            ArrayList<TransferInput> transferList = new ArrayList<>();
            Gson gson = new Gson();
            String transferData = SharedPref.getTransferData();
            if (!transferData.equals("")) {
                Type type = new TypeToken<ArrayList<TransferInput>>() {
                }.getType();
                transferList = gson.fromJson(transferData, type);

                for (TransferInput transferInput : transferList) {
                    iTransferInterface.callUpdateTransferStillage(transferInput);
                }
                String json = "";
                SharedPref.saveTransferData(json);
            }
        }
    }
}
