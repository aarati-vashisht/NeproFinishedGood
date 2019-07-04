package com.neprofinishedgood.putaway;


import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.gson.Gson;
import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalSpinner;
import com.neprofinishedgood.putaway.Adapter.SpinnerAdapter;
import com.neprofinishedgood.putaway.model.LocationData;
import com.neprofinishedgood.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class PutAwayActivity extends BaseActivity {

    @BindView(R.id.buttonPutAway)
    AppCompatButton buttonPutAway;
    @BindView(R.id.buttonCancel)
    AppCompatButton buttonCancel;
    @BindView(R.id.linearLayoutScanDetail)
    LinearLayout linearLayoutScanDetail;
    @BindView(R.id.linearLayoutPutAwayLocation)
    LinearLayout linearLayoutPutAwayLocation;
    @BindView(R.id.editTextScanStillage)
    AppCompatEditText editTextScanStillage;
    @BindView(R.id.textViewScanAisle)
    AppCompatTextView textViewScanAisle;
    @BindView(R.id.editTextDropLocation)
    EditText editTextDropLocation;
    @BindView(R.id.textViewtRack)
    AppCompatTextView textViewtRack;
    @BindView(R.id.textViewBin)
    AppCompatTextView textViewBin;
    @BindView(R.id.spinnerAisle)
    Spinner spinnerAisle;
    @BindView(R.id.spinnerRack)
    Spinner spinnerRack;
    @BindView(R.id.spinnerBin)
    Spinner spinnerBin;

    Animation fadeOut;
    Animation fadeIn;
    ArrayList<UniversalSpinner> aisleList;
    ArrayList<UniversalSpinner> rackList;
    ArrayList<UniversalSpinner> binList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_away);
        ButterKnife.bind(this);
        setTitle(getString(R.string.put_away));
        initData();

    }

    void initData() {
        fadeOut = AnimationUtils.loadAnimation(PutAwayActivity.this, R.anim.animate_fade_out);
        fadeIn = AnimationUtils.loadAnimation(PutAwayActivity.this, R.anim.animate_fade_in);
    }


    @OnClick(R.id.buttonPutAway)
    public void onButtonConfirmClick() {
        if (editTextScanStillage.getText().toString().trim().length() > 0) {
            finish();
        } else {
            editTextScanStillage.setError(getResources().getString(R.string.please_scan_stillage));
        }


    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanStillageChanged(Editable text) {
        if (text.toString().equalsIgnoreCase("S000001")) {
            if (linearLayoutPutAwayLocation.getVisibility() == View.VISIBLE) {
                linearLayoutPutAwayLocation.setVisibility(View.GONE);
                linearLayoutPutAwayLocation.startAnimation(fadeOut);
            }
            linearLayoutScanDetail.setVisibility(View.VISIBLE);
            linearLayoutScanDetail.startAnimation(fadeIn);
            editTextScanStillage.setEnabled(false);
            buttonPutAway.setVisibility(View.VISIBLE);
            buttonCancel.setVisibility(View.VISIBLE);
        } else if (text.toString().equalsIgnoreCase("S000002")) {
            linearLayoutScanDetail.setVisibility(View.VISIBLE);
            linearLayoutPutAwayLocation.setVisibility(View.VISIBLE);
            linearLayoutScanDetail.startAnimation(fadeIn);
            linearLayoutPutAwayLocation.startAnimation(fadeIn);
            editTextScanStillage.setEnabled(false);
            buttonPutAway.setVisibility(View.VISIBLE);
            buttonCancel.setVisibility(View.VISIBLE);
        }


    }

    @OnTextChanged(value = R.id.editTextDropLocation, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void oneditTextDropLocationChanged(Editable text) {
        Gson gson = new Gson();
        LocationData locationData = null;
        try {
            locationData = gson.fromJson(text.toString(), LocationData.class);
        } catch (Exception e) {
            locationData = null;
        }
        if (locationData == null) {
            spinnerAisle.setVisibility(View.VISIBLE);
            textViewScanAisle.setVisibility(View.GONE);
            spinnerRack.setVisibility(View.VISIBLE);
            textViewtRack.setVisibility(View.GONE);
            spinnerBin.setVisibility(View.VISIBLE);
            textViewBin.setVisibility(View.GONE);
            if (editTextDropLocation.getText().toString().trim().equalsIgnoreCase("ABC")) {
                setSpinnerAisleData();
                setSpinnerRackData();
                setSpinnerBinData();
                buttonPutAway.setEnabled(true);
            } else {
                buttonPutAway.setEnabled(false);
            }
        } else {
            Utils.hideSoftKeyboard(this);
            spinnerAisle.setVisibility(View.GONE);
            textViewScanAisle.setVisibility(View.VISIBLE);
            spinnerRack.setVisibility(View.GONE);
            textViewtRack.setVisibility(View.VISIBLE);
            spinnerBin.setVisibility(View.GONE);
            textViewBin.setVisibility(View.VISIBLE);

        }
    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        linearLayoutScanDetail.setVisibility(View.GONE);
        linearLayoutPutAwayLocation.setVisibility(View.GONE);
        if (linearLayoutPutAwayLocation.getVisibility() == View.VISIBLE)
            linearLayoutPutAwayLocation.startAnimation(fadeOut);
        if (linearLayoutScanDetail.getVisibility() == View.VISIBLE)
            linearLayoutScanDetail.startAnimation(fadeOut);

        editTextScanStillage.setText("");
        editTextScanStillage.setEnabled(true);
        buttonPutAway.setVisibility(View.GONE);
        buttonCancel.setVisibility(View.GONE);

    }

    void setSpinnerAisleData() {
        aisleList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            aisleList.add(new UniversalSpinner("Aisle " + i, i + ""));
        }
        aisleList.add(0, new UniversalSpinner("Select Aisle", "0"));
        SpinnerAdapter aisleListAdapter = new SpinnerAdapter(PutAwayActivity.this, R.layout.spinner_layout, aisleList);
        spinnerAisle.setAdapter(aisleListAdapter);
    }

    void setSpinnerRackData() {
        rackList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            rackList.add(new UniversalSpinner("Rack " + i, i + ""));
        }
        rackList.add(0, new UniversalSpinner("Select Rack", "0"));
        SpinnerAdapter rackListAdapter = new SpinnerAdapter(PutAwayActivity.this, R.layout.spinner_layout, rackList);
        spinnerRack.setAdapter(rackListAdapter);
    }

    void setSpinnerBinData() {
        binList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            binList.add(new UniversalSpinner("Bin " + i, i + ""));
        }
        binList.add(0, new UniversalSpinner("Select Bin", "0"));
        SpinnerAdapter binListAdapter = new SpinnerAdapter(PutAwayActivity.this, R.layout.spinner_layout, binList);
        spinnerBin.setAdapter(binListAdapter);
    }

    void clearAllSpinnerData() {
        spinnerAisle.setAdapter(null);
        spinnerRack.setAdapter(null);
        spinnerBin.setAdapter(null);
    }

    boolean isValidated() {
        if (editTextDropLocation.getText().toString().equals("")) {
            editTextDropLocation.setError(getResources().getString(R.string.enter_scan_location));
            editTextDropLocation.requestFocus();
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
}
