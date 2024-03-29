package com.neprofinishedgood.assigntransfer;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.neprofinishedgood.R;
import com.neprofinishedgood.assigntransfer.model.AssignTransInput;
import com.neprofinishedgood.assigntransfer.presenter.IAssignTransInterface;
import com.neprofinishedgood.assigntransfer.presenter.IAssignTransPresenter;
import com.neprofinishedgood.assigntransfer.presenter.IAssignTransView;
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
import com.neprofinishedgood.transferstillage.model.TransferStillageDetail;
import com.neprofinishedgood.transferstillage.model.WareHouseInput;
import com.neprofinishedgood.transferstillage.model.WareHouseResponse;
import com.neprofinishedgood.utils.NetworkChangeReceiver;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class AssignTransferActivity extends BaseActivity implements IAssignTransView {

    @BindView(R.id.editTextScanStillage)
    EditText editTextScanStillage;

    @BindView(R.id.recyclerViewAssignList)
    RecyclerView recyclerViewAssignList;

    @BindView(R.id.buttonAssign)
    CustomButton buttonAssign;

    @BindView(R.id.buttonCancel)
    CustomButton buttonCancel;

    @BindView(R.id.textViewTransType)
    TextView textViewTransType;

    @BindView(R.id.textViewToSite)
    TextView textViewToSite;

    @BindView(R.id.textViewToWarehouse)
    TextView textViewToWarehouse;

    @BindView(R.id.textViewToLocation)
    TextView textViewToLocation;

    @BindView(R.id.imgReset)
    ImageView imgReset;

    private String makeTJ = "1";
    IAssignTransInterface iAssignTransInterface;
    private String toSiteId = "", toWareHouseId = "", toLocationId = "", fromWareHouseId = "", flt = "";

    ArrayList<ScanStillageResponse> assignTransList = new ArrayList<>();
    private ArrayList<TransferStillageDetail> stickersList = new ArrayList<>();
    private TransferAdapter adapter;
    boolean isReset = false;

    Spinner spinnerWarehouseDialog;
    Spinner spinnerLocationDialog;
    ArrayList<UniversalSpinner> warehouseList;
    List<UniversalSpinner> locationList;
    List<UniversalSpinner> fltList;

    static AssignTransferActivity instance;

    public static AssignTransferActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_transfer);

        ButterKnife.bind(this);

        instance = this;
        setTitle(getString(R.string.assign_planned_transfer));
        iAssignTransInterface = new IAssignTransPresenter(this, this);
        editTextScanStillage.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        fltList = new ArrayList<>();
        isReset = false;
        initAlert(this);
        setRecyclerViewAdapter();
    }

    @OnClick(R.id.imgReset)
    void onImgResetClick() {
        isReset = true;
        initAlert(this);
    }

    public void initAlert(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_alert_to_tj_selection);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        CustomButton buttonOk = dialog.findViewById(R.id.buttonOk);
        RadioGroup radioGroupTransferType = dialog.findViewById(R.id.radioGroupTransferType);
        RadioButton radioButtonTJ = dialog.findViewById(R.id.radioButtonTJ);
        RadioButton radioButtonTO = dialog.findViewById(R.id.radioButtonTO);
        LinearLayout linearLayoutTransType = dialog.findViewById(R.id.linearLayoutTransType);
        LinearLayout linearLayoutLocationSelection = dialog.findViewById(R.id.linearLayoutLocationSelection);
        TextView textViewHead = dialog.findViewById(R.id.textViewHead);
        ImageView imgBackButton = dialog.findViewById(R.id.imgBackButton);
        if (isReset) {
            imgBackButton.setVisibility(View.GONE);
        }

        radioGroupTransferType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == radioButtonTJ.getId()) {
                makeTJ = "1";
                textViewTransType.setText(getString(R.string.transfer_journal_process));
            } else if (checkedId == radioButtonTO.getId()) {
                makeTJ = "0";
                textViewTransType.setText(getString(R.string.transfer_order_process));
            }
        });

        radioButtonTJ.setChecked(true);

        Spinner spinnerSite = dialog.findViewById(R.id.spinnerSite);
        spinnerWarehouseDialog = dialog.findViewById(R.id.spinnerWarehouse);
        spinnerLocationDialog = dialog.findViewById(R.id.spinnerLocation);

        if (siteList != null) {
            SpinnerAdapter siteAdapter = new SpinnerAdapter(AssignTransferActivity.getInstance(), R.layout.spinner_layout, siteList);
            spinnerSite.setAdapter(siteAdapter);
        }

        spinnerSite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position <= 0) {
                    warehouseList = new ArrayList<>();
                    spinnerWarehouseDialog.setAdapter(new SpinnerAdapter(AssignTransferActivity.this, R.layout.spinner_layout, warehouseList));
                    locationList = new ArrayList<>();
                    spinnerLocationDialog.setAdapter(new SpinnerAdapter(AssignTransferActivity.this, R.layout.spinner_layout, locationList));
                    textViewToSite.setText("");
                    buttonOk.setEnabled(false);
                } else {
                    toSiteId = siteList.get(position).getId().trim();
                    textViewToSite.setText(siteList.get(position).getId() + " | " + siteList.get(position).getName());
                    if (NetworkChangeReceiver.isInternetConnected(AssignTransferActivity.this)) {
                        showProgress(AssignTransferActivity.getInstance());
                        iAssignTransInterface.callGetWareHouse(new WareHouseInput(toSiteId));
                    } else {
                        showSuccessDialog(getString(R.string.no_internet));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerWarehouseDialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    toWareHouseId = warehouseList.get(position).getId().trim();
                    textViewToWarehouse.setText(warehouseList.get(position).getName());
                    if (NetworkChangeReceiver.isInternetConnected(AssignTransferActivity.this)) {
                        showProgress(AssignTransferActivity.getInstance());
                        iAssignTransInterface.callGetLocation(new LocationsInput(toWareHouseId, userId));
                    } else {
                        showSuccessDialog(getString(R.string.no_internet));
                    }
                } else {
                    locationList = new ArrayList<>();
                    spinnerLocationDialog.setAdapter(new SpinnerAdapter(AssignTransferActivity.this, R.layout.spinner_layout, locationList));
                    textViewToWarehouse.setText("");
                    buttonOk.setEnabled(false);
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
                    toLocationId = locationList.get(position).getId().trim();
                    textViewToLocation.setText(locationList.get(position).getId() + " | " + locationList.get(position).getName());
                    buttonOk.setEnabled(true);
                } else {
                    textViewToLocation.setText("");
                    buttonOk.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        buttonOk.setOnClickListener(v -> {
            if (linearLayoutTransType.getVisibility() == View.VISIBLE) {
                linearLayoutTransType.setVisibility(View.GONE);
                linearLayoutLocationSelection.setVisibility(View.VISIBLE);
                textViewHead.setText(getResources().getString(R.string.select_site_warehouse));
                if (spinnerWarehouseDialog.getSelectedItemPosition() > 0) {
                    buttonOk.setEnabled(true);
                } else {
                    buttonOk.setEnabled(false);
                }
                imgBackButton.setVisibility(View.VISIBLE);
            } else {
                dialog.cancel();
            }
        });

        imgBackButton.setOnClickListener(v -> {
            if (linearLayoutTransType.getVisibility() == View.VISIBLE) {
                if (!isReset) {
                    dialog.cancel();
                    finish();
                }
            } else if (linearLayoutTransType.getVisibility() == View.GONE) {
                if (!isReset) {
                    imgBackButton.setVisibility(View.VISIBLE);
                } else {
                    imgBackButton.setVisibility(View.GONE);
                }
                buttonOk.setEnabled(true);
                linearLayoutTransType.setVisibility(View.VISIBLE);
                linearLayoutLocationSelection.setVisibility(View.GONE);
            }
        });

        dialog.show();
    }

    private void setRecyclerViewAdapter() {
        stickersList = new ArrayList<>();
        assignTransList = new ArrayList<>();

        recyclerViewAssignList.setVisibility(View.VISIBLE);
        adapter = new TransferAdapter(assignTransList, true);
        recyclerViewAssignList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewAssignList.setAdapter(adapter);
        recyclerViewAssignList.setHasFixedSize(true);
    }

    public void deleteAddedData(int position) {
        stickersList.remove(position);
        assignTransList.remove(position);
        adapter.notifyDataSetChanged();
        if (stickersList.isEmpty()) {
            cancelClick();
        }
    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanStillageChanged(Editable text) {
        if (!text.toString().trim().equals("")) {
            if (text.toString().trim().length() == scanStillageLength) {
                if (NetworkChangeReceiver.isInternetConnected(AssignTransferActivity.this)) {
                    if (!isStillageExists(editTextScanStillage.getText().toString().trim())) {
                        showProgress(AssignTransferActivity.this);
                        iAssignTransInterface.callScanStillageService(new MoveInput(editTextScanStillage.getText().toString().trim(), userId));
                    }
                } else {
                    showSuccessDialog(getString(R.string.no_internet));
                    editTextScanStillage.setText("");
                }
            }
        }
    }

    boolean isStillageExists(String stillageId) {
        for (TransferStillageDetail transferStillageDetail : stickersList) {
            if (stillageId.equalsIgnoreCase(transferStillageDetail.getStillageID())) {
                showSuccessDialog(getString(R.string.already_scanned));
                editTextScanStillage.setText("");
                return true;
            }
        }
        return false;
    }

    @Override
    public void onFailure(String message) {
        hideProgress();
        showSuccessDialog(message);
        editTextScanStillage.setText("");
    }

    @Override
    public void onSuccess(ScanStillageResponse body) {
        try {
            hideProgress();
            if (body.getStatus().equalsIgnoreCase(getString(R.string.success))) {
                if (isLocationMatched(body.getWareHouseID())) {
                    if (!body.getTransferId().equals("") || body.getIsShiped().equals("1")) {
                        showSuccessDialog(getString(R.string.already_transfred));
                        editTextScanStillage.setText("");
                    } else if (body.getIsCounted().equals("0")) {
                        showSuccessDialog(getString(R.string.raf_not_posted));
                        editTextScanStillage.setText("");
                    } else {
                        if (body.getStandardQty() > 0) {
                            if (fromWareHouseId == "") {
                                fromWareHouseId = body.getWareHouseID().trim();
                            }
                            if (!fromWareHouseId.equals(toWareHouseId)) {
                                if (fromWareHouseId.equals(body.getWareHouseID().trim())) {
                                    isScanned = true;
                                    setData(body);
                                } else {
                                    showSuccessDialog("Can't scan stillage other than " + fromWareHouseId + " warehouse.");
                                    editTextScanStillage.setText("");
                                }
                            } else {
                                showSuccessDialog("Can't transfer to same warehouse.");
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

    void setData(ScanStillageResponse body) {
        imgReset.setEnabled(false);
        buttonAssign.setEnabled(true);
        buttonCancel.setEnabled(true);
        editTextScanStillage.setText("");
        if (fltList.isEmpty()) {
            fltList = body.getfLTList();
            if (fltList != null) {
                fltList.add(0, new UniversalSpinner("Select Flt", "000"));
            } else {
                fltList = new ArrayList<>();
            }
        }
        assignTransList.add(body);
        stickersList.add(new TransferStillageDetail(body.getStickerID(), ""));
        adapter.notifyDataSetChanged();
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
            wareHouseListAdapter = new SpinnerAdapter(AssignTransferActivity.this, R.layout.spinner_layout, warehouseList);
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
            locationListAdapter = new SpinnerAdapter(AssignTransferActivity.this, R.layout.spinner_layout, locationList);
            spinnerLocationDialog.setAdapter(locationListAdapter);

        } else {
            showSuccessDialog(body.getMessage());
        }
    }

    @Override
    public void onUpdateAssignTransFailure(String message) {
        hideProgress();
        showSuccessDialog(message);
    }

    @Override
    public void onUpdateAssignTransSuccess(UniversalResponse body) {
        hideProgress();
        showSuccessDialog(body.getMessage());
        if (body.getStatus().equals(getString(R.string.success))) {
            cancelClick();
        }
    }

    @OnClick(R.id.buttonAssign)
    public void onButtonAssignClick() {
        if (isValidated()) {
            fltSelectionAlert(this);
        }
    }

    boolean isValidated() {
        if (stickersList.isEmpty()) {
            return false;
        }
        if (toSiteId.isEmpty() || toWareHouseId.isEmpty() || toLocationId.isEmpty() || fromWareHouseId.isEmpty()) {
            showSuccessDialog("Please reset transfer details.");
            return false;
        }
        return true;
    }

    public void fltSelectionAlert(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_alert_flt_selection);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        CustomButton buttonOk = dialog.findViewById(R.id.buttonOk);
        ImageView imgCloseList = dialog.findViewById(R.id.imgCloseList);
        Spinner spinnerFlt = dialog.findViewById(R.id.spinnerFlt);

        if (fltList != null) {
            SpinnerAdapter siteAdapter = new SpinnerAdapter(AssignTransferActivity.getInstance(), R.layout.spinner_layout, fltList);
            spinnerFlt.setAdapter(siteAdapter);
        } else {
            fltList = new ArrayList<>();
            SpinnerAdapter siteAdapter = new SpinnerAdapter(AssignTransferActivity.getInstance(), R.layout.spinner_layout, fltList);
            spinnerFlt.setAdapter(siteAdapter);
        }

        buttonOk.setEnabled(false);

        spinnerFlt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position <= 0) {
                    buttonOk.setEnabled(false);
                } else {
                    flt = fltList.get(position).getId().trim();
                    buttonOk.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        buttonOk.setOnClickListener(v -> {
            if (spinnerFlt.getSelectedItemPosition() > 0) {
                showProgress(this);
                if (NetworkChangeReceiver.isInternetConnected(AssignTransferActivity.this)) {
                    AssignTransInput assignTransInput = new AssignTransInput(userId, makeTJ, toSiteId, toWareHouseId, toLocationId, flt, fromWareHouseId, stickersList);
                    iAssignTransInterface.callUpdateAssignTransfer(assignTransInput);
                    Gson gson = new Gson();
                    String jsonData = gson.toJson(assignTransInput);
                    Log.d("json", jsonData);
                    dialog.cancel();
                } else {
                    showSuccessDialog(getString(R.string.no_internet));
                }
            }
        });

        imgCloseList.setOnClickListener(v -> {
            dialog.cancel();
        });

        dialog.show();
    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        showCancelAlert(9);
    }

    public void cancelClick() {
        imgReset.setEnabled(true);
        flt = "";
//        toSiteId = "";
//        toWareHouseId = "";
        fromWareHouseId = "";
        warehouseList = new ArrayList<>();
        fltList = new ArrayList<>();
        adapter.lockDelete = false;
        buttonAssign.setVisibility(View.VISIBLE);
        warehouseList = new ArrayList<>();
        setRecyclerViewAdapter();
        isScanned = false;
        warehouseList = new ArrayList<>();
        buttonCancel.setEnabled(false);
        buttonAssign.setEnabled(false);
        editTextScanStillage.setText("");
        editTextScanStillage.setEnabled(true);
        editTextScanStillage.requestFocus();
    }

    public void imageButtonHomeClick(View view) {
        if (isScanned) {
            showBackAlert(new Intent(AssignTransferActivity.this, DashBoardAcivity.class), true);
        } else {
            finishAffinity();
            startActivity(new Intent(AssignTransferActivity.this, DashBoardAcivity.class));
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

}
