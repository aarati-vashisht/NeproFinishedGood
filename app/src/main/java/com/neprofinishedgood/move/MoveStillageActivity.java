package com.neprofinishedgood.move;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neprofinishedgood.R;
import com.neprofinishedgood.assign.AssignActivity;
import com.neprofinishedgood.assign.adapter.SpinnerZoneAdapter;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.base.model.UniversalSpinner;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.move.adapter.MoveAdapter;
import com.neprofinishedgood.move.adapter.SpinnerAdapter;
import com.neprofinishedgood.move.model.LocationData;
import com.neprofinishedgood.move.model.LocationInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.move.model.UpdateMoveLocationInput;
import com.neprofinishedgood.move.presenter.IMovePresenter;
import com.neprofinishedgood.move.presenter.IMoveView;
import com.neprofinishedgood.utils.Constants;
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

public class MoveStillageActivity extends BaseActivity implements IMoveView {
    @BindView(R.id.buttonConfirm)
    AppCompatButton buttonPutAway;
    @BindView(R.id.buttonCancel)
    AppCompatButton buttonCancel;
    @BindView(R.id.linearLayoutScanDetail)
    LinearLayout linearLayoutScanDetail;
    @BindView(R.id.linearLayoutPutAwayLocation)
    LinearLayout linearLayoutPutAwayLocation;
    @BindView(R.id.linearLayoutCurrentLocation)
    LinearLayout linearLayoutCurrentLocation;
    @BindView(R.id.linearLayoutMovingLocation)
    LinearLayout linearLayoutMovingLocation;
    @BindView(R.id.linearLayoutAssignedLocation)
    LinearLayout linearLayoutAssignedLocation;
    @BindView(R.id.textViewAssignedLocation)
    AppCompatTextView textViewAssignedLocation;
    @BindView(R.id.editTextDropLocation)
    EditText editTextDropLocation;
    @BindView(R.id.spinnerAisle)
    Spinner spinnerAisle;
    @BindView(R.id.spinnerRack)
    Spinner spinnerRack;
    @BindView(R.id.spinnerBin)
    Spinner spinnerBin;
    @BindView(R.id.stillageDetail)
    View stillageDetail;

    @BindView(R.id.textViewNumberOffline)
    TextView textViewNumberOffline;
    @BindView(R.id.textViewCurrentLocation)
    TextView textViewCurrentLocation;
    @BindView(R.id.textViewMovingLocation)
    TextView textViewMovingLocation;

    @BindView(R.id.linearLayoutOfflineData)
    LinearLayout linearLayoutOfflineData;

    @BindView(R.id.spinnerZone)
    Spinner spinnerZone;

    StillageLayout stillageLayout;

    private static IMovePresenter movePresenter;
    long delay = 1000;
    long dropLocationLastText = 0;
    private MoveAdapter adapter;
    String aisle = "", rack = "", bin = "", zone = "", stillageData, loadingAreaId = "", wareHouseID = "";

    public List<UniversalSpinner> aisleList = new ArrayList<>();
    public List<UniversalSpinner> rackList = new ArrayList<>();
    public List<UniversalSpinner> binList = new ArrayList<>();
    public ArrayList<UniversalSpinner> zoneList = new ArrayList<>();

    String stillageNumber;

    String offlineData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_stillage);
        ButterKnife.bind(this);
        setTitle(getString(R.string.move));
        movePresenter = new IMovePresenter(this, this);
        stillageLayout = new StillageLayout();
        ButterKnife.bind(stillageLayout, stillageDetail);
        getIntentData();
    }

    private void getIntentData() {
        offlineData = getIntent().getStringExtra(Constants.SELECTED_STILLAGE_OFFLINE);
        if (offlineData == null) {
            initData(null);

            Gson gson = new Gson();
            stillageData = getIntent().getStringExtra(Constants.SELECTED_STILLAGE);
            ScanStillageResponse scanStillageResponse = gson.fromJson(stillageData, ScanStillageResponse.class);
            setData(scanStillageResponse);
        } else {
            setDataOffline();
        }
    }

    //data initialization
    void initData(LocationData response) {
        if (response == null) {
            setSpinnerAisleData("0");
            setSpinnerRackData("0");
            setSpinnerBinData("0");
            setSpinnerZoneData("0");
        } else {
            if (response.getZone().equals("")) {
                setSpinnerAisleData(response.getAisle());
                setSpinnerRackData(response.getRack());
                setSpinnerBinData(response.getBin());
            } else {
                setSpinnerZoneData(response.getZone());
            }
        }

    }


    @OnClick(R.id.buttonConfirm)
    public void onButtonConfirmClick() {
        UpdateMoveLocationInput updateMoveLocationInput;
        if (offlineData == null) {
            if (isValidated()) {
                if (linearLayoutPutAwayLocation.getVisibility() != View.VISIBLE) {
                    updateMoveLocationInput = new UpdateMoveLocationInput(stillageLayout.textViewNumber.getText().toString(), "", "", "", userId, loadingAreaId, wareHouseID, zone);
                } else {
                    updateMoveLocationInput = new UpdateMoveLocationInput(stillageLayout.textViewNumber.getText().toString(), aisle, rack, bin, userId, loadingAreaId, wareHouseID, zone);
                }
                movePresenter.callMoveServcie(updateMoveLocationInput);
            }
        } else {
            if (isOfflineValidated()) {
                updateMoveLocationInput = new UpdateMoveLocationInput(textViewNumberOffline.getText().toString(), aisle, rack, bin, userId, loadingAreaId, wareHouseID, zone);
                saveDataOffline(updateMoveLocationInput);
            }
        }

    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        finish();
        overridePendingTransition(0, 0);

    }

    @OnTextChanged(value = R.id.editTextDropLocation, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void oneditTextDropLocationChanged(Editable text) {
        if (!text.toString().trim().equals("")) {
            dropLocationhandler.postDelayed(dropLocationRunnable, delay);
        }

    }

    @OnTextChanged(value = R.id.editTextDropLocation, callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void onEditTextDropLocationTEXTCHANGED(Editable text) {
        dropLocationhandler.removeCallbacks(dropLocationRunnable);

    }

    //for call service on text change
    Handler dropLocationhandler = new Handler();
    private Runnable dropLocationRunnable = new Runnable() {
        public void run() {
            if (NetworkChangeReceiver.isInternetConnected(MoveStillageActivity.this)) {
                showProgress(MoveStillageActivity.this);
                if (System.currentTimeMillis() > (dropLocationLastText + delay - 500)) {
                    movePresenter.callLocationService(new LocationInput(editTextDropLocation.getText().toString(), userId, wareHouseID));
                }
            } else {
                setLocationOffline();
            }
        }
    };

    void setLocationOffline() {
        String locationId = editTextDropLocation.getText().toString();
        if (locationList != null) {
            for (int i = 0; i < locationList.size(); i++) {
                if (locationId.equals(locationList.get(i).getLocationID())) {
                    try {
                        setSpinnerAisleData(locationList.get(i).getAisle());
                        setSpinnerRackData(locationList.get(i).getRack());
                        setSpinnerBinData(locationList.get(i).getBin());
                    } catch (NumberFormatException numberFormatException) {
                        Log.d("NumberFormatException", numberFormatException.toString());
                        numberFormatException.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    @OnItemSelected(R.id.spinnerAisle)
    public void spinnerAisleSelected(Spinner spinner, int position) {
        aisle = aisleList.get(position).getId();
        if (position > 0) {
            zone = "";
            spinnerZone.setSelection(0);
        }
    }

    @OnItemSelected(R.id.spinnerRack)
    public void spinnerRackSelected(Spinner spinner, int position) {
        rack = rackList.get(position).getId();
        if (position > 0) {
            zone = "";
            spinnerZone.setSelection(0);
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
        } else {
            zone = "";
        }
    }


    void setSpinnerAisleData(String item) {
        SpinnerAdapter aisleListAdapter = new SpinnerAdapter(MoveStillageActivity.this, R.layout.spinner_layout, aisleList);
        spinnerAisle.setAdapter(aisleListAdapter);
        if (!item.equals("0")) {
            for (int j = 0; j < aisleList.size(); j++) {
                if (aisleList.get(j).getId().equals(item)) {
                    spinnerAisle.setSelection(j);
                    aisle = aisleList.get(j).getId();
                }
            }
        }
    }

    void setSpinnerRackData(String item) {
        SpinnerAdapter rackListAdapter = new SpinnerAdapter(MoveStillageActivity.this, R.layout.spinner_layout, rackList);
        spinnerRack.setAdapter(rackListAdapter);
        if (!item.equals("0")) {
            for (int j = 0; j < rackList.size(); j++) {
                if (rackList.get(j).getId().equals(item)) {
                    spinnerRack.setSelection(j);
                    rack = rackList.get(j).getId();
                }
            }
        }
    }

    void setSpinnerBinData(String item) {
        SpinnerAdapter binListAdapter = new SpinnerAdapter(MoveStillageActivity.this, R.layout.spinner_layout, binList);
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
        SpinnerZoneAdapter zoneListAdapter = new SpinnerZoneAdapter(MoveStillageActivity.this, R.layout.spinner_picking_item_layout, zoneList);
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


    @Override
    public void onUpdateMoveSuccess(UniversalResponse response) {
        hideProgress();
        if (response.getStatus().equals(getString(R.string.success))) {
            showSuccessDialog(response.getMessage());
//            CustomToast.showToast(this, response.getMessage());
            onButtonCancelClick();
            clearAllSpinnerData();
            finish();
            MoveActivity.getInstance().getAllAssignedData();
        } else {
            showSuccessDialog(response.getMessage());
//            CustomToast.showToast(this, response.getMessage());
        }
    }

    @Override
    public void onUpdateMoveFailure(String message) {
        hideProgress();
        showSuccessDialog(message);
//        CustomToast.showToast(this, message);
    }

    @Override
    public void onLocationSuccess(LocationData response) {
        hideProgress();
        if (response.getStatus().equals(getString(R.string.success))) {
            initData(response);
        } else {
            editTextDropLocation.setText("");
            showSuccessDialog(response.getMessage());
//            CustomToast.showToast(this, response.getMessage());
        }
    }

    @Override
    public void onLocationFailure(String message) {
        hideProgress();
        editTextDropLocation.setText("");
        showSuccessDialog(message);
//        CustomToast.showToast(this, message);

    }

    //set response data
    void setData(ScanStillageResponse body) {
        stillageLayout.textViewitem.setText(body.getItemId());
        stillageLayout.textViewQuantity.setText(body.getStandardQty() + "");
        stillageLayout.textViewitemDesc.setText(body.getDescription());
        stillageLayout.textViewStdQuantity.setText(body.getItemStdQty() + "");
        stillageLayout.textViewNumber.setText(body.getStickerID());

        zoneList = body.getZoneList();
        aisleList = body.getAisleList();
        rackList = body.getRackList();
        binList = body.getBinList();

        if (aisleList == null || rackList == null || binList == null || zoneList == null) {
            aisleList = new ArrayList<>();
            rackList = new ArrayList<>();
            binList = new ArrayList<>();
            zoneList = new ArrayList<>();
        }

        aisleList.add(0, new UniversalSpinner("Select Aisle", ""));
        rackList.add(0, new UniversalSpinner("Select Rack", ""));
        binList.add(0, new UniversalSpinner("Select Bin", ""));
        zoneList.add(0, new UniversalSpinner("", "Select Zone"));

        initData(null);

        wareHouseID = body.getWareHouseID();
        if (body.getAssignedLocation().equals("")) {
            linearLayoutAssignedLocation.setVisibility(View.GONE);
        } else {
            linearLayoutAssignedLocation.setVisibility(View.VISIBLE);
            textViewAssignedLocation.setText(body.getAssignedLocation());
            setSpinnerAisleData(body.getAssignedAisleId());
            setSpinnerRackData(body.getAssignedRackId());
            setSpinnerBinData(body.getAssignedBinId());
            linearLayoutCurrentLocation.setVisibility(View.GONE);
            linearLayoutMovingLocation.setVisibility(View.GONE);
        }

        if (body.getStillageLocationName().equals(getResources().getString(R.string.prduction_line_))) {
            linearLayoutPutAwayLocation.setVisibility(View.GONE);
            linearLayoutCurrentLocation.setVisibility(View.VISIBLE);
            linearLayoutMovingLocation.setVisibility(View.VISIBLE);
            aisle = "";
            rack = "";
            bin = "";
            textViewMovingLocation.setText(textViewMovingLocation.getText().toString() + ": " + body.getLoadingAreaId());
            loadingAreaId = body.getLoadingAreaId();
        } else {
            linearLayoutPutAwayLocation.setVisibility(View.VISIBLE);
            linearLayoutCurrentLocation.setVisibility(View.GONE);
            linearLayoutMovingLocation.setVisibility(View.GONE);
            loadingAreaId = "";
        }

    }

    boolean isValidated() {
        if (linearLayoutPutAwayLocation.getVisibility() == View.VISIBLE) {
            if (spinnerAisle.getSelectedItemPosition() != 0 || spinnerRack.getSelectedItemPosition() != 0 || spinnerBin.getSelectedItemPosition() != 0 || !editTextDropLocation.getText().toString().equalsIgnoreCase("")) {
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
        editTextDropLocation.setText("");
    }

    public void imageButtonCloseClick(View view) {
        spinnerAisle.setVisibility(View.VISIBLE);
        spinnerBin.setVisibility(View.VISIBLE);
        spinnerRack.setVisibility(View.VISIBLE);
        editTextDropLocation.setText("");
    }

    void setDataOffline() {
        stillageNumber = getIntent().getStringExtra(Constants.SELECTED_STILLAGE_OFFLINE);
        initData(null);
        textViewNumberOffline.setText(stillageNumber);
        setVisibilityInOfflineMode();
    }

    void setVisibilityInOfflineMode() {
        linearLayoutOfflineData.setVisibility(View.VISIBLE);
        stillageDetail.setVisibility(View.GONE);
        linearLayoutAssignedLocation.setVisibility(View.GONE);
    }

    void saveDataOffline(UpdateMoveLocationInput data) {
        ArrayList<UpdateMoveLocationInput> moveList = new ArrayList<>();
        Gson gson = new Gson();
        String moveData = SharedPref.getMoveData();
        if (!moveData.equals("")) {
            Type type = new TypeToken<ArrayList<UpdateMoveLocationInput>>() {
            }.getType();
            moveList = gson.fromJson(moveData, type);
        }
        moveList.add(data);
        String json = gson.toJson(moveList);
        SharedPref.saveMoveData(json);
        showSuccessDialog(getResources().getString(R.string.data_saved_offline));
//        CustomToast.showToast(this, getResources().getString(R.string.data_saved_offline));
        onButtonCancelClick();
        clearAllSpinnerData();
        finish();
    }

    boolean isOfflineValidated() {
        if (spinnerAisle.getSelectedItemPosition() != 0 || spinnerRack.getSelectedItemPosition() != 0 || spinnerBin.getSelectedItemPosition() != 0) {
            return true;
        } else {
            TextView textView = (TextView) spinnerAisle.getSelectedView();
            textView.setError(getString(R.string.select_aisle));
            textView.requestFocus();
            return false;
        }

    }

}
