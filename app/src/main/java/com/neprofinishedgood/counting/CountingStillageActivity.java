package com.neprofinishedgood.counting;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;

import com.google.gson.Gson;
import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalSpinner;
import com.neprofinishedgood.counting.model.StillageDatum;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.dashboard.DashBoardAcivity;
import com.neprofinishedgood.putaway.Adapter.SpinnerAdapter;
import com.neprofinishedgood.utils.Constants;
import com.neprofinishedgood.utils.StillageLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;

public class CountingStillageActivity extends BaseActivity {
    @BindView(R.id.frameEnterQuantity)
    FrameLayout frameEnterQuantity;

    @BindView(R.id.frameAssignFlt)
    FrameLayout frameAssignFlt;

    @BindView(R.id.frameEnterLocation)
    FrameLayout frameEnterLocation;

    @BindView(R.id.spinnerShift)
    Spinner spinnerShift;

    @BindView(R.id.spinnerReason)
    Spinner spinnerReason;

    @BindView(R.id.spinnerAisle)
    Spinner spinnerAisle;

    @BindView(R.id.spinnerRack)
    Spinner spinnerRack;

    @BindView(R.id.spinnerBin)
    Spinner spinnerBin;
    @BindView(R.id.spinnerAssignFlt)
    Spinner spinnerAssignFlt;

    @BindView(R.id.editTextQuantity)
    AppCompatEditText editTextQuantity;

    @BindView(R.id.cardView)
    CardView cardView;

    @BindView(R.id.linearLayoutReason)
    LinearLayout linearLayoutReason;

    @BindView(R.id.linearLayoutEnterLocationButtons)
    LinearLayout linearLayoutEnterLocationButtons;
    @BindView(R.id.buttonLocationConfirm)
    CustomButton buttonLocationConfirm;
    @BindView(R.id.editTextScanLocation)
    AppCompatEditText editTextScanLocation;
    @BindView(R.id.buttonLocationCancel)
    CustomButton buttonLocationCancel;
    @BindView(R.id.buttonConfirm)
    CustomButton buttonConfirm;

    @BindView(R.id.buttonCancel)
    CustomButton buttonCancel;

    @BindView(R.id.buttonAssign)
    CustomButton buttonAssign;

    @BindView(R.id.buttonUnAssign)
    CustomButton buttonUnAssign;

    @BindView(R.id.linearLayoutScanDetail)
    LinearLayout linearLayoutScanDetail;

    @BindView(R.id.stillageDetail)
    View stillageDetail;

    StillageLayout stillageLayout;

    float stillageQtyNo = 0;
    float editQtyNo = 0;

    Animation fadeOut;
    Animation fadeIn;
    String SELECTED_STILLAGE;
    ArrayList<UniversalSpinner> aisleList;
    ArrayList<UniversalSpinner> rackList;
    ArrayList<UniversalSpinner> binList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counting_stillage);
        ButterKnife.bind(this);
        initData();
    }

    void initData() {
        stillageLayout = new StillageLayout();
        ButterKnife.bind(stillageLayout, stillageDetail);

        fadeOut = AnimationUtils.loadAnimation(CountingStillageActivity.this, R.anim.animate_fade_out);
        fadeIn = AnimationUtils.loadAnimation(CountingStillageActivity.this, R.anim.animate_fade_in);
        SELECTED_STILLAGE = getIntent().getStringExtra(Constants.SELECTED_STILLAGE);
        Gson gson = new Gson();
        StillageDatum stillageDatum = gson.fromJson(SELECTED_STILLAGE, StillageDatum.class);
        setTitle(stillageDatum.getNumber());

        stillageLayout.textViewitem.setText(stillageDatum.getItem());
        stillageLayout.textViewNumber.setText(stillageDatum.getNumber());
        stillageLayout.textViewQuantity.setText(stillageDatum.getQuantity());
        stillageLayout.textViewStdQuantity.setText(stillageDatum.getStdQuantity());
        editTextQuantity.setText(stillageDatum.getQuantity());
        editTextQuantity.setSelection(stillageDatum.getQuantity().length());
    }

    @OnTextChanged(value = R.id.editTextQuantity,
            callback = OnTextChanged.Callback.TEXT_CHANGED)
    protected void afterEditTextChanged(Editable editable) {
        String stillageQty, editQty;
        stillageQty = stillageLayout.textViewQuantity.getText().toString();
        editQty = editable.toString();
        stillageQtyNo = Float.parseFloat(stillageQty);
        if (!editQty.equals("")) {
            editQtyNo = Float.parseFloat(editQty);
            if (editQtyNo < stillageQtyNo) {
                linearLayoutReason.setVisibility(View.VISIBLE);
                buttonConfirm.setEnabled(true);
            } else if (editQtyNo > stillageQtyNo) {
                linearLayoutReason.setVisibility(View.GONE);
                editTextQuantity.setError(getResources().getString(R.string.quantity_must_not_greater_than_stillage_qty), getDrawable(R.drawable.error_icon));
                editTextQuantity.requestFocus();
                buttonConfirm.setEnabled(false);
            } else {
                linearLayoutReason.setVisibility(View.GONE);
                buttonConfirm.setEnabled(true);
            }
        } else {
            editTextQuantity.setError(getResources().getString(R.string.quantiy_must_not_be_blank));
            editTextQuantity.requestFocus();
            buttonConfirm.setEnabled(false);
        }
        setData();
    }

    void setData() {

        String[] shiftList = {"Select Shift", "A", "B", "C"};
        ArrayAdapter<String> shiftAdapter = new ArrayAdapter(this, R.layout.spinner_layout, shiftList);
        spinnerShift.setAdapter(shiftAdapter);

        String[] reasonsList = {"Select Reason", "Wrong Product", "Product Damaged", "Other"};
        ArrayAdapter<String> reasonAdapter = new ArrayAdapter(this, R.layout.spinner_layout, reasonsList);
        spinnerReason.setAdapter(reasonAdapter);

        String[] assignFltList = {"Select FLT", "FLT 1", "FLT 2", "FLT 3"};
        ArrayAdapter<String> assignFltAdapter = new ArrayAdapter(this, R.layout.spinner_layout, assignFltList);
        spinnerAssignFlt.setAdapter(assignFltAdapter);

    }

    @OnClick(R.id.buttonConfirm)
    public void onButtonConfirmClick() {
        if (editQtyNo < stillageQtyNo) {
            if (!spinnerReason.getSelectedItem().toString().equals("Select Reason")) {
                frameEnterQuantity.setVisibility(View.GONE);
                frameEnterQuantity.setAnimation(fadeOut);
                frameEnterLocation.setVisibility(View.VISIBLE);
                frameEnterLocation.setAnimation(fadeIn);

            } else {
                TextView textView = (TextView) spinnerReason.getSelectedView();
                textView.setError("Select reason");
                textView.requestFocus();
            }
        } else {
            frameEnterQuantity.setVisibility(View.GONE);
            frameEnterQuantity.setAnimation(fadeOut);
            frameEnterLocation.setVisibility(View.VISIBLE);
            frameEnterLocation.setAnimation(fadeIn);


        }

    }

    @OnClick(R.id.buttonLocationConfirm)
    public void onbuttonLocationConfirmClick() {
        if (isValidated()) {
            CustomToast.showToast(CountingStillageActivity.this, getResources().getString(R.string.item_location_assigned_successfully));
            frameAssignFlt.setVisibility(View.VISIBLE);
            frameEnterLocation.setVisibility(View.GONE);
            buttonAssign.setEnabled(false);
        }
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

    @OnClick(R.id.buttonLocationCancel)
    public void onbuttonLocationCancelClick() {
        CustomToast.showToast(CountingStillageActivity.this, getResources().getString(R.string.item_location_unassigned_successfully));
        frameAssignFlt.setVisibility(View.VISIBLE);
        frameEnterLocation.setVisibility(View.GONE);
        buttonAssign.setEnabled(false);

    }

    @OnItemSelected(R.id.spinnerAssignFlt)
    void onItemSelected(int position) {
        if (position > 0) {
            buttonAssign.setEnabled(true);
        }
    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        startActivity(new Intent(CountingStillageActivity.this, DashBoardAcivity.class));
        finish();
    }

    @OnClick(R.id.buttonAssign)
    public void onButtonAssignClick() {
        CustomToast.showToast(this, getString(R.string.item_flt_assigned_successfully));
        finish();
    }

    @OnClick(R.id.buttonUnAssign)
    public void onButtonUnAssignClick() {
        CustomToast.showToast(this, getString(R.string.item_flt_unassigned_successfully));
        finish();

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

    void setSpinnerAisleData() {
        aisleList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            aisleList.add(new UniversalSpinner("Aisle " + i, i + ""));
        }
        aisleList.add(0, new UniversalSpinner("Select Aisle", "0"));
        SpinnerAdapter aisleListAdapter = new SpinnerAdapter(CountingStillageActivity.this, R.layout.spinner_layout, aisleList);
        spinnerAisle.setAdapter(aisleListAdapter);
    }

    void setSpinnerRackData() {
        rackList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            rackList.add(new UniversalSpinner("Rack " + i, i + ""));
        }
        rackList.add(0, new UniversalSpinner("Select Rack", "0"));
        SpinnerAdapter rackListAdapter = new SpinnerAdapter(CountingStillageActivity.this, R.layout.spinner_layout, rackList);
        spinnerRack.setAdapter(rackListAdapter);
    }

    void setSpinnerBinData() {
        binList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            binList.add(new UniversalSpinner("Bin " + i, i + ""));
        }
        binList.add(0, new UniversalSpinner("Select Bin", "0"));
        SpinnerAdapter binListAdapter = new SpinnerAdapter(CountingStillageActivity.this, R.layout.spinner_layout, binList);
        spinnerBin.setAdapter(binListAdapter);
    }

    void clearAllSpinnerData() {
        spinnerAisle.setAdapter(null);
        spinnerRack.setAdapter(null);
        spinnerBin.setAdapter(null);
    }

}
