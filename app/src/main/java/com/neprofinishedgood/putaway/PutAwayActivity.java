package com.neprofinishedgood.putaway;


import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.widget.AppCompatButton;

import com.google.gson.Gson;
import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.putaway.model.LocationData;
import com.neprofinishedgood.utils.Utils;

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
    EditText editTextScanStillage;
    @BindView(R.id.editTextScanAisle)
    EditText editTextScanAisle;
    @BindView(R.id.editTextDropLocation)
    EditText editTextDropLocation;
    @BindView(R.id.editTextRack)
    EditText editTextRack;
    @BindView(R.id.editTextBin)
    EditText editTextBin;
    @BindView(R.id.spinnerAisle)
    Spinner spinnerAisle;
    @BindView(R.id.spinnerRack)
    Spinner spinnerRack;
    @BindView(R.id.spinnerBin)
    Spinner spinnerBin;

    Animation fadeOut;
    Animation fadeIn;
    String aisleString = "", rackString = "", binString = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_away);
        ButterKnife.bind(this);
        setTitle(getString(R.string.put_away));
        initData();
        buttonPutAway.setEnabled(false);

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
            }
            linearLayoutScanDetail.setVisibility(View.VISIBLE);
            editTextScanStillage.setEnabled(false);
        } else if (text.toString().equalsIgnoreCase("S000002")) {
            linearLayoutScanDetail.setVisibility(View.VISIBLE);
            linearLayoutPutAwayLocation.setVisibility(View.VISIBLE);
            editTextScanStillage.setEnabled(false);
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
            editTextScanAisle.setVisibility(View.GONE);
            spinnerRack.setVisibility(View.VISIBLE);
            editTextRack.setVisibility(View.GONE);
            spinnerBin.setVisibility(View.VISIBLE);
            editTextBin.setVisibility(View.GONE);
        } else {
            Utils.hideSoftKeyboard(this);
            spinnerAisle.setVisibility(View.GONE);
            editTextScanAisle.setVisibility(View.VISIBLE);
            spinnerRack.setVisibility(View.GONE);
            editTextRack.setVisibility(View.VISIBLE);
            spinnerBin.setVisibility(View.GONE);
            editTextBin.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        linearLayoutScanDetail.setVisibility(View.GONE);
        linearLayoutPutAwayLocation.setVisibility(View.GONE);
        editTextScanStillage.setText("");
        editTextScanStillage.setEnabled(true);
    }


}
