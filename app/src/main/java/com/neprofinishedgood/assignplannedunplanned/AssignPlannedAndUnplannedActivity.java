package com.neprofinishedgood.assignplannedunplanned;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

import com.neprofinishedgood.R;
import com.neprofinishedgood.assignplannedunplanned.model.AssignedUnAssignedInput;
import com.neprofinishedgood.assignplannedunplanned.presenter.AssignPlannedAndUnplannedPresenter;
import com.neprofinishedgood.assignplannedunplanned.presenter.IAssignPlannedAndUnplannedInterFace;
import com.neprofinishedgood.assignplannedunplanned.presenter.IAssignePlannedUnplannedView;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.plannedandunplannedmove.adapter.SpinnerAdapter;
import com.neprofinishedgood.plannedandunplannedmove.model.LocationData;
import com.neprofinishedgood.plannedandunplannedmove.model.LocationInput;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;
import com.neprofinishedgood.utils.StillageLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;

public class AssignPlannedAndUnplannedActivity extends BaseActivity implements IAssignePlannedUnplannedView {

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
    boolean isButtonInAssignLocation = true;
    private IAssignPlannedAndUnplannedInterFace iAssAndUAssInterface;
    long scanStillageLastTexxt = 0;
    long delay = 1000;
    long dropLocationLastText = 0;
    String aisle, rack, bin, flt, isAssignLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_planned_and_unplanned);
        ButterKnife.bind(this);
        stillageLayout = new StillageLayout();
        ButterKnife.bind(stillageLayout, stillageDetail);
        setTitle(getString(R.string.assign_planned_and_unplanned));
        iAssAndUAssInterface = new AssignPlannedAndUnplannedPresenter(this);
        initData(null);

    }

    void initData(LocationData response) {
        if (response == null) {
            setSpinnerAisleData(0);
            setSpinnerRackData(0);
            setSpinnerBinData(0);
        } else {
            setSpinnerAisleData(response.getAisle());
            setSpinnerRackData(response.getRack());
            setSpinnerBinData(response.getBin());
        }
        setSpinnerFLtData();

    }

    void setSpinnerFLtData() {
        SpinnerAdapter aisleListAdapter = new SpinnerAdapter(AssignPlannedAndUnplannedActivity.this, R.layout.spinner_layout, fltList);
        spinnerAssignFlt.setAdapter(aisleListAdapter);

    }

    void setSpinnerAisleData(int item) {
        SpinnerAdapter aisleListAdapter = new SpinnerAdapter(AssignPlannedAndUnplannedActivity.this, R.layout.spinner_layout, aisleList);
        spinnerAisle.setAdapter(aisleListAdapter);
        if (item > 0) {
            for (int j = 0; j < aisleList.size(); j++) {
                if (aisleList.get(j).getId().equals(item + "")) {
                    spinnerAisle.setSelection(j);
                    aisle = aisleList.get(j).getId() + "";
                }
            }
        }
    }

    @OnItemSelected(R.id.spinnerAssignFlt)
    public void spinnerAssignFltSelected(Spinner spinner, int position) {
        flt = fltList.get(position).getId();
    }

    @OnItemSelected(R.id.spinnerAisle)
    public void spinnerAisleSelected(Spinner spinner, int position) {
        aisle = aisleList.get(position).getId();
    }

    @OnItemSelected(R.id.spinnerRack)
    public void spinnerRackSelected(Spinner spinner, int position) {
        rack = rackList.get(position).getId();
    }

    @OnItemSelected(R.id.spinnerBin)
    public void spinnerBinSelected(Spinner spinner, int position) {
        bin = binList.get(position).getId();
    }

    void setSpinnerRackData(int item) {
        SpinnerAdapter rackListAdapter = new SpinnerAdapter(AssignPlannedAndUnplannedActivity.this, R.layout.spinner_layout, rackList);
        spinnerRack.setAdapter(rackListAdapter);
        if (item > 0) {
            for (int j = 0; j < rackList.size(); j++) {
                if (rackList.get(j).getId().equals(item + "")) {
                    spinnerRack.setSelection(j);
                    rack = rackList.get(j).getId() + "";
                }
            }
        }
    }

    void setSpinnerBinData(int item) {
        SpinnerAdapter binListAdapter = new SpinnerAdapter(AssignPlannedAndUnplannedActivity.this, R.layout.spinner_layout, binList);
        spinnerBin.setAdapter(binListAdapter);
        if (item > 0) {
            for (int j = 0; j < binList.size(); j++) {
                if (binList.get(j).getId().equals(item + "")) {
                    spinnerBin.setSelection(j);
                    bin = binList.get(j).getId() + "";
                }
            }
        }
    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanStillageChanged(Editable text) {
        if (!text.toString().trim().equals("")) {
            scanStillagehandler.postDelayed(stillageRunnable, delay);
        }

    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void onEditTextScanStillageTEXTCHANGED(Editable text) {
        scanStillagehandler.removeCallbacks(stillageRunnable);

    }

    //for call service on text change
    Handler scanStillagehandler = new Handler();
    private Runnable stillageRunnable = new Runnable() {
        public void run() {
            showProgress(AssignPlannedAndUnplannedActivity.this);
            if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
                iAssAndUAssInterface.callScanStillageService(new MoveInput(editTextScanStillage.getText().toString().trim(), userId));
            }
        }
    };

    @OnTextChanged(value = R.id.editTextScanLocation, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void oneditTextDropLocationChanged(Editable text) {
        if (!text.toString().trim().equals("")) {
            dropLocationhandler.postDelayed(dropLocationRunnable, delay);
        }

    }

    @OnTextChanged(value = R.id.editTextScanLocation, callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void onEditTextDropLocationTEXTCHANGED(Editable text) {
        dropLocationhandler.removeCallbacks(dropLocationRunnable);

    }

    //for call service on text change
    Handler dropLocationhandler = new Handler();
    private Runnable dropLocationRunnable = new Runnable() {
        public void run() {
            showProgress(AssignPlannedAndUnplannedActivity.this);
            if (System.currentTimeMillis() > (dropLocationLastText + delay - 500)) {
                iAssAndUAssInterface.callLocationService(new LocationInput(editTextScanLocation.getText().toString(), userId));
            }
        }
    };


    void setData(ScanStillageResponse body) {
        relativeLayoutScanDetail.setVisibility(View.VISIBLE);
        editTextScanStillage.setEnabled(false);
        stillageLayout.textViewitem.setText(body.getItemId());
        stillageLayout.textViewQuantity.setText(body.getStandardQty() + "");
        stillageLayout.textViewitemDesc.setText(body.getDescription());
        stillageLayout.textViewStdQuantity.setText(body.getItemStdQty() + "");
        stillageLayout.textViewNumber.setText(body.getStickerID());
    }

    @OnClick(R.id.buttonAssign)
    public void onButtonAssignClick() {
        if (isValidated() && isButtonInAssignLocation) {
            //for location Assign
            isButtonInAssignLocation = false;
            frameAssignFlt.setVisibility(View.VISIBLE);
            frameAssignLocation.setVisibility(View.GONE);
            buttonAssign.setEnabled(false);
        } else if (isFLTValidated() && !isButtonInAssignLocation) {
            //for flt Assign
            showProgress(this);
            AssignedUnAssignedInput assignedUnAssignedInput = new AssignedUnAssignedInput(editTextScanStillage.getText().toString().trim(), aisle, rack, bin, userId, flt);
            iAssAndUAssInterface.callAssigneUnassignedServcie(assignedUnAssignedInput);
        }

    }

    @OnClick(R.id.buttonUnAssign)
    public void onButtonUnAssignClick() {
        if (isButtonInAssignLocation) {
            //for location UnAssign
            aisle = "";
            rack = "";
            bin = "";
            isButtonInAssignLocation = false;
            frameAssignFlt.setVisibility(View.VISIBLE);
            frameAssignLocation.setVisibility(View.GONE);
            buttonAssign.setEnabled(false);
        } else if (!isButtonInAssignLocation) {
            flt = "";
            showProgress(this);
            AssignedUnAssignedInput assignedUnAssignedInput = new AssignedUnAssignedInput(editTextScanStillage.getText().toString().trim(), aisle, rack, bin, userId, flt);
            iAssAndUAssInterface.callAssigneUnassignedServcie(assignedUnAssignedInput);
        }


    }

    @OnItemSelected(R.id.spinnerAssignFlt)
    void onItemSelected(int position) {
        if (position > 0) {
            buttonAssign.setEnabled(true);
        }
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
            if (spinnerAisle.getSelectedItemPosition() != 0 || spinnerRack.getSelectedItemPosition() == 0 || spinnerBin.getSelectedItemPosition() == 0) {
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
    }

    @Override
    public void onFailure(String message) {
        hideProgress();
        CustomToast.showToast(this, getString(R.string.no_data_found));
    }

    @Override
    public void onSuccess(ScanStillageResponse body) {
        hideProgress();
        // initData();
        if (body.getStatus().equals(getResources().getString(R.string.success))) {
            setData(body);
        } else {
            CustomToast.showToast(this, getString(R.string.no_data_found));
            editTextScanStillage.setText("");
        }
    }

    @Override
    public void onLocationFailure(String message) {
        hideProgress();
        editTextScanLocation.setText("");
        CustomToast.showToast(this, message);
    }

    @Override
    public void onLocationSuccess(LocationData response) {
        hideProgress();
        if (response.getStatus().equals(getString(R.string.success))) {
            initData(response);
        } else {
            CustomToast.showToast(this, response.getMessage());
        }
    }

    @Override
    public void onAssigneUnassignedFailure(String message) {
        hideProgress();
        CustomToast.showToast(this, message);
    }

    @Override
    public void onAssigneUnassignedSuccess(UniversalResponse response) {
        hideProgress();
        if (response.getStatus().equals(getString(R.string.success))) {
            relativeLayoutScanDetail.setVisibility(View.GONE);
            editTextScanStillage.setEnabled(true);
            editTextScanStillage.setText("");
            clearAllSpinnerData();
        } else {
            CustomToast.showToast(this, response.getMessage());
        }
    }
}