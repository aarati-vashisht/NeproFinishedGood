package com.neprofinishedgood.assign;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neprofinishedgood.R;
import com.neprofinishedgood.assign.adapter.SpinnerZoneAdapter;
import com.neprofinishedgood.assign.model.AisleInput;
import com.neprofinishedgood.assign.model.AssignedUnAssignedInput;
import com.neprofinishedgood.assign.presenter.AssignPresenter;
import com.neprofinishedgood.assign.presenter.IAssignInterFace;
import com.neprofinishedgood.assign.presenter.IAssignView;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.base.model.UniversalSpinner;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.dashboard.DashBoardAcivity;
import com.neprofinishedgood.move.adapter.SpinnerAdapter;
import com.neprofinishedgood.move.model.LocationData;
import com.neprofinishedgood.move.model.LocationInput;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
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
import butterknife.OnTextChanged;

public class AssignActivity extends BaseActivity implements IAssignView {

    @BindView(R.id.relativeLayoutScanDetail)
    RelativeLayout relativeLayoutScanDetail;

    @BindView(R.id.stillageDetail)
    View stillageDetail;

    StillageLayout stillageLayout;

    @BindView(R.id.editTextScanLocation)
    AppCompatEditText editTextScanLocation;

    @BindView(R.id.spinnerAssignFlt)
    Spinner spinnerAssignFlt;

    @BindView(R.id.spinnerAisle)
    Spinner spinnerAisle;

    @BindView(R.id.spinnerRack)
    Spinner spinnerRack;

    @BindView(R.id.spinnerBin)
    Spinner spinnerBin;

    @BindView(R.id.spinnerZone)
    Spinner spinnerZone;

    @BindView(R.id.buttonAssign)
    CustomButton buttonAssign;

    @BindView(R.id.buttonUnAssign)
    CustomButton buttonUnAssign;

    @BindView(R.id.editTextScanStillage)
    AppCompatEditText editTextScanStillage;

    @BindView(R.id.frameAssignFlt)
    FrameLayout frameAssignFlt;

    @BindView(R.id.frameAssignLocation)
    FrameLayout frameAssignLocation;

    @BindView(R.id.linearLayoutOfflineData)
    LinearLayout linearLayoutOfflineData;

    @BindView(R.id.linearLayoutAisle)
    LinearLayout linearLayoutAisle;

    @BindView(R.id.linearLayoutRack)
    LinearLayout linearLayoutRack;

    @BindView(R.id.linearLayoutBin)
    LinearLayout linearLayoutBin;

    @BindView(R.id.linearLayoutZone)
    LinearLayout linearLayoutZone;

    @BindView(R.id.linearLayoutAisleName)
    LinearLayout linearLayoutAisleName;

    @BindView(R.id.linearLayoutRackName)
    LinearLayout linearLayoutRackName;

    @BindView(R.id.linearLayoutBinName)
    LinearLayout linearLayoutBinName;

    @BindView(R.id.linearLayoutZoneName)
    LinearLayout linearLayoutZoneName;

    @BindView(R.id.linearLayoutAssignLocationButtons)
    LinearLayout linearLayoutAssignLocationButtons;

    @BindView(R.id.textViewOr)
    TextView textViewOr;

    @BindView(R.id.textViewAisle)
    TextView textViewAisle;

    @BindView(R.id.textViewRack)
    TextView textViewRack;

    @BindView(R.id.textViewBin)
    TextView textViewBin;

    @BindView(R.id.textViewZone)
    TextView textViewZone;

    @BindView(R.id.linearLayoutLocationScanDetail)
    LinearLayout linearLayoutLocationScanDetail;

    @BindView(R.id.textViewNumberOffline)
    TextView textViewNumberOffline;

    boolean isButtonInAssignLocation = true;
    private IAssignInterFace iAssAndUAssInterface;
    long scanStillageLastTexxt = 0;
    long delay = 1000;
    long dropLocationLastText = 0;
    String aisle = "", rack = "", bin = "", zone = "", flt = "", isAssignLocation;
    String wareHouseID = "";


    public List<UniversalSpinner> aisleList = new ArrayList<>();
    public List<UniversalSpinner> rackList = new ArrayList<>();
    public List<UniversalSpinner> binList = new ArrayList<>();
    public ArrayList<UniversalSpinner> zoneList = new ArrayList<>();
    public List<UniversalSpinner> fltList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign);
        ButterKnife.bind(this);
        stillageLayout = new StillageLayout();
        ButterKnife.bind(stillageLayout, stillageDetail);
        setTitle(getString(R.string.assignlocation_flt));
        iAssAndUAssInterface = new AssignPresenter(this, this);
        initData(null);
        editTextScanStillage.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        editTextScanLocation.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        callService();

    }

    void initData(LocationData response) {
        if (response == null) {
            setSpinnerAisleData("0");
            setSpinnerRackData("0");
            setSpinnerBinData("0");
            setSpinnerZoneData("0");
        } else {
            if (response.getZoneName().equals("")) {
                setSpinnerAisleData(response.getAisle());
                setSpinnerRackData(response.getRack());
                setSpinnerBinData(response.getBin());
            } else {
                setSpinnerZoneData(response.getZone());
            }
        }

    }

    void setSpinnerFLtData() {
        SpinnerAdapter aisleListAdapter = new SpinnerAdapter(AssignActivity.this, R.layout.spinner_layout, fltList);
        spinnerAssignFlt.setAdapter(aisleListAdapter);

    }

    void setSpinnerAisleData(String item) {
        SpinnerAdapter aisleListAdapter = new SpinnerAdapter(AssignActivity.this, R.layout.spinner_layout, aisleList) {
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = (TextView) super.getView(position, convertView, parent);

                if (position == 0) {
                    tv.setTextColor(Color.WHITE);
                    tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
                } else {
                    tv.setTextColor(Color.BLACK);
                    tv.setTypeface(tv.getTypeface(), Typeface.NORMAL);
                }

                return tv;
            }
        };

        spinnerAisle.setAdapter(aisleListAdapter);
        if (!item.equals("0")) {
            for (int j = 0; j < aisleList.size(); j++) {
                if (aisleList.get(j).getId().equals(item + "")) {
                    spinnerAisle.setSelection(j);
                    aisle = aisleList.get(j).getId() + "";
                }
            }
        }
    }

    void setSpinnerRackData(String item) {
        SpinnerAdapter rackListAdapter = new SpinnerAdapter(AssignActivity.this, R.layout.spinner_layout, rackList);
        spinnerRack.setAdapter(rackListAdapter);
        if (!item.equals("0")) {
            for (int j = 0; j < rackList.size(); j++) {
                if (rackList.get(j).getId().equals(item)) {
                    spinnerRack.setSelection(j);
                    rack = rackList.get(j).getId() + "";
                }
            }
        }
    }

    void setSpinnerBinData(String item) {
        SpinnerAdapter binListAdapter = new SpinnerAdapter(AssignActivity.this, R.layout.spinner_layout, binList);
        spinnerBin.setAdapter(binListAdapter);
        if (!item.equals("0")) {
            for (int j = 0; j < binList.size(); j++) {
                if (binList.get(j).getId().equals(item)) {
                    spinnerBin.setSelection(j);
                    bin = binList.get(j).getId();
                }
            }
        }
    }

    void setSpinnerZoneData(String item) {
        SpinnerZoneAdapter zoneListAdapter = new SpinnerZoneAdapter(AssignActivity.this, R.layout.spinner_layout, zoneList) {
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = (TextView) super.getView(position, convertView, parent);

                if (position == 0) {
                    tv.setTextColor(Color.WHITE);
                    tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
                } else {
                    tv.setTextColor(Color.BLACK);
                    tv.setTypeface(tv.getTypeface(), Typeface.NORMAL);
                }

                return tv;
            }
        };
        spinnerZone.setAdapter(zoneListAdapter);
        if (!item.equals("0")) {
            for (int j = 0; j < zoneList.size(); j++) {
                if (zoneList.get(j).getId().equals(item)) {
                    spinnerZone.setSelection(j);
                    zone = zoneList.get(j).getId();
                }
            }
        }
    }

//    @OnItemSelected(R.id.spinnerAssignFlt)
//    public void spinnerAssignFltSelected(Spinner spinner, int position) {
//        flt = fltList.get(position).getId();
//    }

    @OnItemSelected(R.id.spinnerAisle)
    public void spinnerAisleSelected(Spinner spinner, int position) {
        aisle = aisleList.get(position).getId();
        if (position > 0) {
            if (NetworkChangeReceiver.isInternetConnected(AssignActivity.this)) {
                zone = "";
                spinnerZone.setSelection(0);
                showProgress(this);
                iAssAndUAssInterface.callAisleSelectionService(new AisleInput(aisle, wareHouseID, ""));
            } else {
                showSuccessDialog(getString(R.string.no_internet));
            }
        }
        if (position == 0) {
            spinner.setBackgroundResource(R.drawable.first_spinner);
        } else {
            spinner.setBackgroundResource(R.drawable.spinner_background);
        }

    }

    @OnItemSelected(R.id.spinnerRack)
    public void spinnerRackSelected(Spinner spinner, int position) {
        rack = rackList.get(position).getId();
        if (position > 0) {
            if (NetworkChangeReceiver.isInternetConnected(AssignActivity.this)) {
                zone = "";
                spinnerZone.setSelection(0);
                showProgress(this);
                iAssAndUAssInterface.callRackSelectionService(new AisleInput(aisle, wareHouseID, rack));
            } else {
                showSuccessDialog(getString(R.string.no_internet));
            }
        }
    }

    @OnItemSelected(R.id.spinnerBin)
    public void spinnerBinSelected(Spinner spinner, int position) {
        bin = binList.get(position).getId();
        if (position > 0) {
            zone = "";
            spinnerZone.setSelection(0);
        }
    }

    @OnItemSelected(R.id.spinnerZone)
    public void spinnerZoneSelected(Spinner spinner, int position) {
        if (position > 0) {
            zone = zoneList.get(position).getId();
            aisle = "";
            rack = "";
            bin = "";
            spinnerAisle.setSelection(0);
            spinnerRack.setSelection(0);
            spinnerBin.setSelection(0);
            rackList = new ArrayList<>();
            binList = new ArrayList<>();
            setSpinnerRackData("0");
            setSpinnerBinData("0");
        } else {
            zone = "";
        }
        if (position == 0) {
            spinner.setBackgroundResource(R.drawable.first_spinner);
        } else {
            spinner.setBackgroundResource(R.drawable.spinner_background);
        }
    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanStillageChanged(Editable text) {
        if (!text.toString().trim().equals("")) {
//            scanStillagehandler.postDelayed(stillageRunnable, delay);
            if (text.toString().trim().length() == scanStillageLength) {
                if (NetworkChangeReceiver.isInternetConnected(AssignActivity.this)) {
                    showProgress(AssignActivity.this);
                    if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
                        iAssAndUAssInterface.callScanStillageService(new MoveInput(editTextScanStillage.getText().toString().trim(), userId));
                    }
                } else {
                    showSuccessDialog(getString(R.string.no_internet));
//                    setDataOffline();
                }
            }

        }

    }

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
//            if (NetworkChangeReceiver.isInternetConnected(AssignActivity.this)) {
//                showProgress(AssignActivity.this);
//                if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
//                    iAssAndUAssInterface.callScanStillageService(new AisleInput(editTextScanStillage.getText().toString().trim(), userId));
//                }
//            } else {
//                setDataOffline();
//            }
//        }
//    };

    @OnTextChanged(value = R.id.editTextScanLocation, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void oneditTextDropLocationChanged(Editable text) {
        if (!text.toString().trim().equals("")) {
//            dropLocationhandler.postDelayed(dropLocationRunnable, delay);
            if (text.toString().trim().length() == scanLocationLength) {
                if (NetworkChangeReceiver.isInternetConnected(AssignActivity.this)) {
                    showProgress(AssignActivity.this);
                    iAssAndUAssInterface.callLocationService(new LocationInput(editTextScanLocation.getText().toString(), userId, wareHouseID));
                } else {
                    showSuccessDialog(getString(R.string.no_internet));
//                    setLocationOffline();
                }
            }
        }
    }

//    @OnTextChanged(value = R.id.editTextScanLocation, callback = OnTextChanged.Callback.TEXT_CHANGED)
//    public void onEditTextDropLocationTEXTCHANGED(Editable text) {
//        dropLocationhandler.removeCallbacks(dropLocationRunnable);
//
//    }
//
//    //for call service on text change
//    Handler dropLocationhandler = new Handler();
//    private Runnable dropLocationRunnable = new Runnable() {
//        public void run() {
//            if (NetworkChangeReceiver.isInternetConnected(AssignActivity.this)) {
//                showProgress(AssignActivity.this);
//                if (System.currentTimeMillis() > (dropLocationLastText + delay - 500)) {
//                    iAssAndUAssInterface.callLocationService(new LocationInput(editTextScanLocation.getText().toString(), userId, wareHouseID));
//                }
//            } else {
//                setLocationOffline();
//            }
//        }
//    };

    void setLocationOffline() {
        String locationId = editTextScanLocation.getText().toString();
        for (int i = 0; i < locationList.size(); i++) {
            if (locationId.equals(locationList.get(i).getLocationID())) {
                try {
                    setSpinnerAisleData(locationList.get(i).getAisle());
                    setSpinnerRackData(locationList.get(i).getRack());
                    setSpinnerBinData(locationList.get(i).getBin());
                } catch (NumberFormatException numberFormatException) {
                    numberFormatException.printStackTrace();
                }
                break;
            }
        }
    }

    void setData(ScanStillageResponse body) {
        fltList = body.getfLTList();
        fltList.add(0, new UniversalSpinner("Select FLT", "000"));
        setSpinnerFLtData();
        relativeLayoutScanDetail.setVisibility(View.VISIBLE);
        linearLayoutAssignLocationButtons.setVisibility(View.VISIBLE);

        editTextScanStillage.setEnabled(false);
        stillageLayout.textViewitem.setText(body.getItemId());
        stillageLayout.textViewQuantity.setText(body.getStandardQty() + "");
        stillageLayout.textViewitemDesc.setText(body.getDescription());
        stillageLayout.textViewStdQuantity.setText(body.getItemStdQty() + "");
        stillageLayout.textViewNumber.setText(body.getStickerID());
        initData(null);
        aisleList = body.getAisleList();
//        rackList = body.getRackList();
//        binList = body.getBinList();
        zoneList = body.getZoneList();

        if (aisleList == null) {
            aisleList = new ArrayList<>();
        }
//            rackList = new ArrayList<>();
//            binList = new ArrayList<>();
        if (zoneList == null) {
            zoneList = new ArrayList<>();
        }

        aisleList.add(0, new UniversalSpinner("Select Aisle", ""));
//        rackList.add(0, new UniversalSpinner("Select Rack", ""));
//        binList.add(0, new UniversalSpinner("Select Bin", ""));
        zoneList.add(0, new UniversalSpinner("", "Select Zone"));

        buttonAssign.setEnabled(true);
        wareHouseID = body.getWareHouseID();
        setSpinnerAisleData("0");
//        setSpinnerRackData("0");
//        setSpinnerBinData("0");
        setSpinnerZoneData("0");
    }

    @OnClick(R.id.buttonAssign)
    public void onButtonAssignClick() {
        if (isValidated() && isButtonInAssignLocation) {
            //for location Assign
            isButtonInAssignLocation = false;
            frameAssignFlt.setVisibility(View.VISIBLE);
            frameAssignLocation.setVisibility(View.GONE);
            buttonAssign.setEnabled(false);
            spinnerAssignFlt.setSelection(0);
        } else if (isFLTValidated() && !isButtonInAssignLocation) {
            //for flt Assign
            if (linearLayoutOfflineData.getVisibility() == View.GONE) {
                if (NetworkChangeReceiver.isInternetConnected(AssignActivity.this)) {
                    showProgress(this);
                    AssignedUnAssignedInput assignedUnAssignedInput = new AssignedUnAssignedInput(editTextScanStillage.getText().toString().trim(), aisle, rack, bin, userId, flt, wareHouseID, zone);
                    iAssAndUAssInterface.callAssigneUnassignedServcie(assignedUnAssignedInput);

                    frameAssignFlt.setVisibility(View.GONE);
                    frameAssignLocation.setVisibility(View.VISIBLE);
                    isButtonInAssignLocation = true;
                } else {
                    showSuccessDialog(getString(R.string.no_internet));
                }
            } else {
                if (isOfflineValidated()) {
                    AssignedUnAssignedInput assignedUnAssignedInput = new AssignedUnAssignedInput(editTextScanStillage.getText().toString().trim(), aisle, rack, bin, userId, flt, wareHouseID, zone);
                    saveDataOffline(assignedUnAssignedInput);
                }
            }
        }

    }

    @OnClick(R.id.buttonUnAssign)
    public void onButtonUnAssignClick() {
        if (isButtonInAssignLocation) {
            //for location UnAssign
            aisle = "";
            rack = "";
            bin = "";
            zone = "";
            isButtonInAssignLocation = false;
            frameAssignFlt.setVisibility(View.VISIBLE);
            frameAssignLocation.setVisibility(View.GONE);
            buttonAssign.setEnabled(false);
            spinnerAssignFlt.setSelection(0);
        } else if (!isButtonInAssignLocation) {
            flt = "";
            if (linearLayoutOfflineData.getVisibility() == View.GONE) {
                if (NetworkChangeReceiver.isInternetConnected(AssignActivity.this)) {
                    showProgress(this);
                    AssignedUnAssignedInput assignedUnAssignedInput = new AssignedUnAssignedInput(editTextScanStillage.getText().toString().trim(), aisle, rack, bin, userId, flt, wareHouseID, zone);
                    iAssAndUAssInterface.callAssigneUnassignedServcie(assignedUnAssignedInput);

                    frameAssignFlt.setVisibility(View.GONE);
                    frameAssignLocation.setVisibility(View.VISIBLE);
                    isButtonInAssignLocation = true;
                } else {
                    showSuccessDialog(getString(R.string.no_internet));
                }
            } else {
                AssignedUnAssignedInput assignedUnAssignedInput = new AssignedUnAssignedInput(editTextScanStillage.getText().toString().trim(), aisle, rack, bin, userId, flt, wareHouseID, zone);
                saveDataOffline(assignedUnAssignedInput);
            }
        }


    }

    @OnClick(R.id.imageViewBackButton)
    public void onIimageViewBackButtonClick() {
        isButtonInAssignLocation = true;
        frameAssignFlt.setVisibility(View.GONE);
        frameAssignLocation.setVisibility(View.VISIBLE);
        buttonAssign.setEnabled(true);
    }

    @OnItemSelected(R.id.spinnerAssignFlt)
    void onItemSelected(int position) {
        if (position > 0) {
            buttonAssign.setEnabled(true);
        }
        flt = fltList.get(position).getId();
    }

    @OnClick(R.id.imageViewLoacationCancel)
    void onImageViewLoacationCancelClick() {
        linearLayoutAisle.setVisibility(View.VISIBLE);
        linearLayoutRack.setVisibility(View.VISIBLE);
        linearLayoutBin.setVisibility(View.VISIBLE);
        linearLayoutZone.setVisibility(View.VISIBLE);
        textViewOr.setVisibility(View.VISIBLE);
        linearLayoutLocationScanDetail.setVisibility(View.GONE);

        textViewAisle.setText("");
        textViewRack.setText("");
        textViewBin.setText("");
        textViewZone.setText("");

        aisle = "";
        rack = "";
        bin = "";
        zone = "";
        spinnerAisle.setSelection(0);

        editTextScanLocation.setEnabled(true);
        editTextScanLocation.setText("");
    }

    boolean isFLTValidated() {
        if (frameAssignFlt.getVisibility() == View.VISIBLE) {
            if (spinnerAssignFlt.getSelectedItemPosition() != 0) {
                return true;
            } else {
                TextView textView = (TextView) spinnerAssignFlt.getSelectedView();
                textView.setError(getString(R.string.select_flt));
                textView.requestFocus();
                return false;
            }
        } else {
            return true;
        }
    }

    boolean isValidated() {
        if (frameAssignLocation.getVisibility() == View.VISIBLE) {
            if (spinnerAisle.getSelectedItemPosition() != 0 || spinnerRack.getSelectedItemPosition() != 0 || spinnerBin.getSelectedItemPosition() != 0 || spinnerZone.getSelectedItemPosition() != 0 || !editTextScanLocation.getText().toString().equalsIgnoreCase("")) {
                return true;
            } else {
                TextView textView = (TextView) spinnerAisle.getSelectedView();
                textView.setError(getString(R.string.select_aisle));
                textView.requestFocus();
                return false;
            }
        } else {
            return true;
        }
    }

    void clearAllSpinnerData() {
        spinnerAisle.setAdapter(null);
        spinnerRack.setAdapter(null);
        spinnerBin.setAdapter(null);
        spinnerZone.setAdapter(null);
    }

    @Override
    public void onFailure(String message) {
        hideProgress();
        showSuccessDialog(message);
//        CustomToast.showToast(this, getString(R.string.no_data_found));
    }

    @Override
    public void onSuccess(ScanStillageResponse body) {
        hideProgress();
        // initData();
        if (body.getStatus().equals(getResources().getString(R.string.success))) {
            if (isLocationMatched(body.getWareHouseID())) {
                if (body.getStandardQty() > 0) {
                    isScanned = true;
                    setData(body);
                } else {
                    showSuccessDialog(getResources().getString(R.string.stillage_discarded));
                    editTextScanStillage.setText("");
                }
            } else {
                editTextScanStillage.setText("");
                showSuccessDialog(getResources().getString(R.string.stillage_not_found));
            }
        } else {
            showSuccessDialog(body.getMessage());
//            CustomToast.showToast(this, body.getMessage());
            editTextScanStillage.setText("");
        }
    }

    @Override
    public void onLocationFailure(String message) {
        hideProgress();
        editTextScanLocation.setEnabled(true);
        editTextScanLocation.setText("");
        showSuccessDialog(message);
//        CustomToast.showToast(this, message);
    }

    @Override
    public void onLocationSuccess(LocationData response) {
        hideProgress();
        if (response.getStatus().equals(getString(R.string.success))) {
            linearLayoutAisle.setVisibility(View.GONE);
            linearLayoutRack.setVisibility(View.GONE);
            linearLayoutBin.setVisibility(View.GONE);
            linearLayoutZone.setVisibility(View.GONE);
            textViewOr.setVisibility(View.GONE);
            linearLayoutLocationScanDetail.setVisibility(View.VISIBLE);

            if (response.getZoneName().equals("")) {
                textViewAisle.setText(response.getAisleName());
                textViewRack.setText(response.getRackName());
                textViewBin.setText(response.getBinName());
                linearLayoutZoneName.setVisibility(View.GONE);
                textViewZone.setText("");
                aisle = response.getAisle();
                rack = response.getRack();
                bin = response.getBin();

                linearLayoutAisleName.setVisibility(View.VISIBLE);
                linearLayoutRackName.setVisibility(View.VISIBLE);
                linearLayoutBinName.setVisibility(View.VISIBLE);
            } else {
                zone = response.getZone();
                textViewZone.setText(response.getZoneName());
                linearLayoutZoneName.setVisibility(View.VISIBLE);
                linearLayoutAisleName.setVisibility(View.GONE);
                linearLayoutRackName.setVisibility(View.GONE);
                linearLayoutBinName.setVisibility(View.GONE);
                textViewAisle.setText("");
                textViewRack.setText("");
                textViewBin.setText("");
            }

            rackList = new ArrayList<>();
            binList = new ArrayList<>();
            setSpinnerRackData("0");
            setSpinnerBinData("0");
            spinnerZone.setSelection(0);
            spinnerAisle.setSelection(0);
            editTextScanLocation.setEnabled(false);

//            initData(response);
        } else {
            editTextScanLocation.setEnabled(true);
            editTextScanLocation.setText("");
            showSuccessDialog(response.getMessage());
//            CustomToast.showToast(this, response.getMessage());
            editTextScanLocation.setText("");
        }
    }

    @Override
    public void onAssigneUnassignedFailure(String message) {
        hideProgress();
        if (relativeLayoutScanDetail.getVisibility() == View.VISIBLE) {
            showSuccessDialog(message);
//            CustomToast.showToast(this, message);
        }
    }

    @Override
    public void onAssigneUnassignedSuccess(UniversalResponse response) {
        hideProgress();
        if (relativeLayoutScanDetail.getVisibility() == View.VISIBLE) {
            if (response.getStatus().equals(getString(R.string.success))) {
                isScanned = false;
                relativeLayoutScanDetail.setVisibility(View.GONE);
                linearLayoutAssignLocationButtons.setVisibility(View.GONE);
                editTextScanStillage.setEnabled(true);
                editTextScanStillage.setText("");
                showSuccessDialog(response.getMessage());
//                CustomToast.showToast(this, response.getMessage());
                clearAllSpinnerData();
                onImageViewLoacationCancelClick();
                aisleList = new ArrayList<>();
                rackList = new ArrayList<>();
                binList = new ArrayList<>();
                zoneList = new ArrayList<>();
            } else {
                showSuccessDialog(response.getMessage());
//                CustomToast.showToast(this, response.getMessage());
            }
        }
    }

    @Override
    public void onAisleSelectionSuccess(ScanStillageResponse body) {
        hideProgress();
        if (body.getStatus().equals(getString(R.string.success))) {
            rackList = body.getRackList();
            if (rackList == null) {
                rackList = new ArrayList<>();
            }
            rackList.add(0, new UniversalSpinner("Select Rack", ""));
            setSpinnerRackData("0");
        } else {
            showSuccessDialog(body.getMessage());
        }
    }

    @Override
    public void onAisleSelectionFailure(String message) {
        hideProgress();
        showSuccessDialog(message);
    }

    @Override
    public void onRackSelectionSuccess(ScanStillageResponse body) {
        hideProgress();
        if (body.getStatus().equals(getString(R.string.success))) {
            binList = body.getBinList();
            if (binList == null) {
                binList = new ArrayList<>();
            }
            binList.add(0, new UniversalSpinner("Select Bin", ""));
            setSpinnerBinData("0");
        } else {
            showSuccessDialog(body.getMessage());
        }
    }

    @Override
    public void onRackSelectionFailure(String message) {
        hideProgress();
        showSuccessDialog(message);
    }

    public void imageButtonHomeClick(View view) {
        if (isScanned) {
            showBackAlert(new Intent(AssignActivity.this, DashBoardAcivity.class), true);
        } else {
            finishAffinity();
            startActivity(new Intent(AssignActivity.this, DashBoardAcivity.class));
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
        initData(null);
    }

    void setVisibilityInOfflineMode() {
        editTextScanStillage.setEnabled(false);
        linearLayoutOfflineData.setVisibility(View.VISIBLE);
        stillageDetail.setVisibility(View.GONE);
        relativeLayoutScanDetail.setVisibility(View.VISIBLE);
        linearLayoutAssignLocationButtons.setVisibility(View.VISIBLE);
    }

    void disableVisibility() {
        relativeLayoutScanDetail.setVisibility(View.GONE);
        linearLayoutAssignLocationButtons.setVisibility(View.GONE);
        editTextScanStillage.setEnabled(true);
        editTextScanStillage.setText("");
        linearLayoutOfflineData.setVisibility(View.GONE);
        stillageDetail.setVisibility(View.VISIBLE);
        clearAllSpinnerData();
        frameAssignFlt.setVisibility(View.GONE);
        frameAssignLocation.setVisibility(View.VISIBLE);
        isButtonInAssignLocation = true;
    }

    void saveDataOffline(AssignedUnAssignedInput data) {
        ArrayList<AssignedUnAssignedInput> assignedUnAssignedList = new ArrayList<>();
        Gson gson = new Gson();
        String assignedUnAssignedData = SharedPref.getAssignedUnAssignedData();
        if (!assignedUnAssignedData.equals("")) {
            Type type = new TypeToken<ArrayList<AssignedUnAssignedInput>>() {
            }.getType();
            assignedUnAssignedList = gson.fromJson(assignedUnAssignedData, type);
        }
        assignedUnAssignedList.add(data);
        String json = gson.toJson(assignedUnAssignedList);
        SharedPref.saveAssignedUnAssignedData(json);
        showSuccessDialog(getResources().getString(R.string.data_saved_offline));
//        CustomToast.showToast(this, getResources().getString(R.string.data_saved_offline));
        disableVisibility();
    }

    boolean isOfflineValidated() {
        if (spinnerAssignFlt.getSelectedItemPosition() == 0) {
            TextView textView = (TextView) spinnerAssignFlt.getSelectedView();
            textView.setError(getString(R.string.select_flt));
            textView.requestFocus();
            return false;
        }
        return true;
    }

    public void callService() {
        if (NetworkChangeReceiver.isInternetConnected(AssignActivity.this)) {
            ArrayList<AssignedUnAssignedInput> assignedUnAssignedList = new ArrayList<>();
            Gson gson = new Gson();
            String assignedUnAssignedData = SharedPref.getAssignedUnAssignedData();
            if (!assignedUnAssignedData.equals("")) {
                Type type = new TypeToken<ArrayList<AssignedUnAssignedInput>>() {
                }.getType();
                assignedUnAssignedList = gson.fromJson(assignedUnAssignedData, type);

                for (AssignedUnAssignedInput assignedUnAssignedInput : assignedUnAssignedList) {
                    iAssAndUAssInterface.callAssigneUnassignedServcie(assignedUnAssignedInput);
                }
                String json = "";
                SharedPref.saveAssignedUnAssignedData(json);
            }
        }
    }
}
