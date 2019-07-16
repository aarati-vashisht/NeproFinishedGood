package com.neprofinishedgood.assignlocationandflt;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalSpinner;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.plannedandunplannedmove.adapter.SpinnerAdapter;
import com.neprofinishedgood.raf.model.StillageDatum;
import com.neprofinishedgood.utils.StillageLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;

public class AssignPlannedAndUnplannedActivity extends BaseActivity {

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

    Animation fadeOut;
    Animation fadeIn;

    ArrayList<UniversalSpinner> assignFltList;
    ArrayList<UniversalSpinner> aisleList;
    ArrayList<UniversalSpinner> rackList;
    ArrayList<UniversalSpinner> binList;

    StillageDatum stillageDatum;

    boolean isButtonInAssignLocation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_planned_and_unplanned);

        ButterKnife.bind(this);
        setTitle(getString(R.string.assign_planned_and_unplanned));
        initData();
    }

    void initData() {
        stillageLayout = new StillageLayout();
        ButterKnife.bind(stillageLayout, stillageDetail);

        fadeOut = AnimationUtils.loadAnimation(AssignPlannedAndUnplannedActivity.this, R.anim.animate_fade_out);
        fadeIn = AnimationUtils.loadAnimation(AssignPlannedAndUnplannedActivity.this, R.anim.animate_fade_in);
    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanStillageChanged(Editable text) {
        if (text.toString().equalsIgnoreCase("S00001")) {
            relativeLayoutScanDetail.setVisibility(View.VISIBLE);
            relativeLayoutScanDetail.setAnimation(fadeIn);
            editTextScanStillage.setEnabled(false);
            editTextScanLocation.requestFocus();
            setData();
        } else {
            relativeLayoutScanDetail.setVisibility(View.GONE);
            relativeLayoutScanDetail.setAnimation(fadeOut);
        }
    }

    @OnTextChanged(value = R.id.editTextScanLocation, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanLocationChanged(Editable text) {
        String editQty;
        editQty = text.toString();

        if (editQty.equalsIgnoreCase("ABC")) {

            setSpinnerAisleData();
            setSpinnerRackData();
            setSpinnerBinData();

            buttonAssign.setEnabled(true);

        } else {
            buttonAssign.setEnabled(false);
            clearAllSpinnerData();
        }
    }

    void setData() {
//        Gson gson = new Gson();
//        LoadingPlanDatum stillageDatum = gson.fromJson(JsonString, LoadingPlanDatum.class);
        stillageDatum = new StillageDatum();
        stillageDatum.setItem("1");
        stillageDatum.setName("S00001");
        stillageDatum.setNumber("S00001");
        stillageDatum.setQuantity("100");
        stillageDatum.setStdQuantity("100");
        stillageDatum.setStillageId("");

        stillageLayout.textViewitem.setText(stillageDatum.getItem());
        stillageLayout.textViewNumber.setText(stillageDatum.getNumber());
        stillageLayout.textViewQuantity.setText(stillageDatum.getQuantity());
        stillageLayout.textViewStdQuantity.setText(stillageDatum.getStdQuantity());

    }

    @OnClick(R.id.buttonAssign)
    public void onButtonAssignClick() {
        if (isValidated() && isButtonInAssignLocation) {
            CustomToast.showToast(AssignPlannedAndUnplannedActivity.this, getResources().getString(R.string.item_location_assigned_successfully));
            isButtonInAssignLocation = false;
            frameAssignFlt.setVisibility(View.VISIBLE);
            frameAssignLocation.setVisibility(View.GONE);
            buttonAssign.setEnabled(false);
            setSpinnerAssignFltData();
        } else if (!isButtonInAssignLocation) {
            CustomToast.showToast(AssignPlannedAndUnplannedActivity.this, getResources().getString(R.string.item_flt_assigned_successfully));
            finish();
        }
    }

    @OnClick(R.id.buttonUnAssign)
    public void onButtonUnAssignClick() {
        if (isButtonInAssignLocation) {
            CustomToast.showToast(AssignPlannedAndUnplannedActivity.this, getResources().getString(R.string.item_location_unassigned_successfully));
            isButtonInAssignLocation = false;
            frameAssignFlt.setVisibility(View.VISIBLE);
            frameAssignLocation.setVisibility(View.GONE);
            buttonAssign.setEnabled(false);
            setSpinnerAssignFltData();
        } else if (!isButtonInAssignLocation) {
            CustomToast.showToast(AssignPlannedAndUnplannedActivity.this, getResources().getString(R.string.item_flt_unassigned_successfully));
            finish();
        }
    }

    @OnItemSelected(R.id.spinnerAssignFlt)
    void onItemSelected(int position) {
        if (position > 0) {
            buttonAssign.setEnabled(true);
        }
    }

    void setSpinnerAssignFltData() {
        assignFltList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            assignFltList.add(new UniversalSpinner("FLT " + i, i + ""));
        }
        assignFltList.add(0, new UniversalSpinner("Select FLT", "0"));
        SpinnerAdapter assignFltListAdapter = new SpinnerAdapter(AssignPlannedAndUnplannedActivity.this, R.layout.spinner_layout, assignFltList);
        spinnerAssignFlt.setAdapter(assignFltListAdapter);
    }

    void setSpinnerAisleData() {
        aisleList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            aisleList.add(new UniversalSpinner("Aisle " + i, i + ""));
        }
        aisleList.add(0, new UniversalSpinner("Select Aisle", "0"));
        SpinnerAdapter aisleListAdapter = new SpinnerAdapter(AssignPlannedAndUnplannedActivity.this, R.layout.spinner_layout, aisleList);
        spinnerAisle.setAdapter(aisleListAdapter);
    }

    void setSpinnerRackData() {
        rackList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            rackList.add(new UniversalSpinner("Rack " + i, i + ""));
        }
        rackList.add(0, new UniversalSpinner("Select Rack", "0"));
        SpinnerAdapter rackListAdapter = new SpinnerAdapter(AssignPlannedAndUnplannedActivity.this, R.layout.spinner_layout, rackList);
        spinnerRack.setAdapter(rackListAdapter);
    }

    void setSpinnerBinData() {
        binList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            binList.add(new UniversalSpinner("Bin " + i, i + ""));
        }
        binList.add(0, new UniversalSpinner("Select Bin", "0"));
        SpinnerAdapter binListAdapter = new SpinnerAdapter(AssignPlannedAndUnplannedActivity.this, R.layout.spinner_layout, binList);
        spinnerBin.setAdapter(binListAdapter);
    }

    boolean isValidated() {
        if (editTextScanLocation.getText().toString().equals("")) {
            editTextScanLocation.setError(getResources().getString(R.string.enter_scan_location));
            editTextScanLocation.requestFocus();
            return false;
        }
        if (spinnerAisle.getSelectedItemPosition() == 0) {
            TextView textView = (TextView) spinnerAisle.getSelectedView();
            textView.setError(getString(R.string.select_aisle));
            textView.requestFocus();
            return false;
        }

        if (spinnerRack.getSelectedItemPosition() == 0) {
            TextView textView = (TextView) spinnerRack.getSelectedView();
            textView.setError(getString(R.string.select_rack));
            textView.requestFocus();
            return false;
        }
        if (spinnerBin.getSelectedItemPosition() == 0) {
            TextView textView = (TextView) spinnerBin.getSelectedView();
            textView.setError(getString(R.string.select_bin));
            textView.requestFocus();
            return false;
        }
        return true;
    }

    void clearAllSpinnerData() {
        spinnerAisle.setAdapter(null);
        spinnerRack.setAdapter(null);
        spinnerBin.setAdapter(null);
    }

}
