package com.neprofinishedgood.plannedandunplannedmove;


import android.os.Bundle;
import android.os.Handler;
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
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.plannedandunplannedmove.Adapter.SpinnerAdapter;
import com.neprofinishedgood.plannedandunplannedmove.model.LocationData;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveResponse;
import com.neprofinishedgood.plannedandunplannedmove.presenter.IMovePresenter;
import com.neprofinishedgood.plannedandunplannedmove.presenter.IMoveView;
import com.neprofinishedgood.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class PlannedAndUnPlannedMoveActivity extends BaseActivity implements IMoveView {

    @BindView(R.id.buttonConfirm)
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
    private IMovePresenter movePresenter;
    long delay = 1000;
    long last_text_edit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planned_unplanned_move);
        ButterKnife.bind(this);
        setTitle(getString(R.string.move));
        initData();
        movePresenter = new IMovePresenter(this);

    }

    //data initiallization
    void initData() {
        fadeOut = AnimationUtils.loadAnimation(PlannedAndUnPlannedMoveActivity.this, R.anim.animate_fade_out);
        fadeIn = AnimationUtils.loadAnimation(PlannedAndUnPlannedMoveActivity.this, R.anim.animate_fade_in);
        setSpinnerAisleData();
        setSpinnerRackData();
        setSpinnerBinData();
    }


    @OnClick(R.id.buttonConfirm)
    public void onButtonConfirmClick() {
        if (editTextScanStillage.getText().toString().trim().equalsIgnoreCase("s00001")) {
            CustomToast.showToast(PlannedAndUnPlannedMoveActivity.this, getResources().getString(R.string.data_saved_successfully));
            finish();
        } else {
            if (isValidated()) {
                CustomToast.showToast(PlannedAndUnPlannedMoveActivity.this, getResources().getString(R.string.data_saved_successfully));
                finish();
            }
        }
    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanStillageChanged(Editable text) {
        if (!text.toString().trim().equals("")) {
            handler.postDelayed(input_finish_checker, delay);
        }

    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void onEditTextScanStillageTEXTCHANGED(Editable text) {
        handler.removeCallbacks(input_finish_checker);

    }

    //for call service on text change
    Handler handler = new Handler();
    private Runnable input_finish_checker = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                showProgress(PlannedAndUnPlannedMoveActivity.this);
                movePresenter.callMoveService(new MoveInput(editTextScanStillage.getText().toString().trim()));

            }
        }
    };

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
        SpinnerAdapter aisleListAdapter = new SpinnerAdapter(PlannedAndUnPlannedMoveActivity.this, R.layout.spinner_layout, aisleList);
        spinnerAisle.setAdapter(aisleListAdapter);
    }

    void setSpinnerRackData() {
        rackList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            rackList.add(new UniversalSpinner("Rack " + i, i + ""));
        }
        rackList.add(0, new UniversalSpinner("Select Rack", "0"));
        SpinnerAdapter rackListAdapter = new SpinnerAdapter(PlannedAndUnPlannedMoveActivity.this, R.layout.spinner_layout, rackList);
        spinnerRack.setAdapter(rackListAdapter);
    }

    void setSpinnerBinData() {
        binList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            binList.add(new UniversalSpinner("Bin " + i, i + ""));
        }
        binList.add(0, new UniversalSpinner("Select Bin", "0"));
        SpinnerAdapter binListAdapter = new SpinnerAdapter(PlannedAndUnPlannedMoveActivity.this, R.layout.spinner_layout, binList);
        spinnerBin.setAdapter(binListAdapter);
    }

    void clearAllSpinnerData() {
        spinnerAisle.setAdapter(null);
        spinnerRack.setAdapter(null);
        spinnerBin.setAdapter(null);
    }

    boolean isValidated() {
        if (linearLayoutPutAwayLocation.getVisibility() == View.VISIBLE) {
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
        } else {
            return true;
        }
    }

    @Override
    public void onSuccess(MoveResponse body) {
        hideProgress();
        if (body.getStatus().equals(getResources().getString(R.string.success))) {
            if (body.getIsMovedFromProdLine() == 0) {
                if (linearLayoutPutAwayLocation.getVisibility() == View.VISIBLE) {
                    linearLayoutPutAwayLocation.setVisibility(View.GONE);
                    linearLayoutPutAwayLocation.startAnimation(fadeOut);
                }
                linearLayoutScanDetail.setVisibility(View.VISIBLE);
                linearLayoutScanDetail.startAnimation(fadeIn);
                editTextScanStillage.setEnabled(false);
                buttonPutAway.setVisibility(View.VISIBLE);
                buttonCancel.setVisibility(View.VISIBLE);
            } else {
                linearLayoutScanDetail.setVisibility(View.VISIBLE);
                linearLayoutPutAwayLocation.setVisibility(View.VISIBLE);
                linearLayoutScanDetail.startAnimation(fadeIn);
                linearLayoutPutAwayLocation.startAnimation(fadeIn);
                editTextScanStillage.setEnabled(false);
                buttonPutAway.setVisibility(View.VISIBLE);
                buttonCancel.setVisibility(View.VISIBLE);
                editTextDropLocation.requestFocus();
            }
        } else {
            CustomToast.showToast(getApplicationContext(), getString(R.string.something_went_wrong_please_try_again));

        }

    }


    @Override
    public void onFailure() {
        hideProgress();
        CustomToast.showToast(this, getString(R.string.something_went_wrong_please_try_again));
    }
}
