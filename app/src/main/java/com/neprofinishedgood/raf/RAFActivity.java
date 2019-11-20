package com.neprofinishedgood.raf;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.dashboard.DashBoardAcivity;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.raf.model.RafInput;
import com.neprofinishedgood.raf.presenter.IRAFInterface;
import com.neprofinishedgood.raf.presenter.IRAFPresenter;
import com.neprofinishedgood.raf.presenter.IRAFView;
import com.neprofinishedgood.utils.NetworkChangeReceiver;
import com.neprofinishedgood.utils.SharedPref;
import com.neprofinishedgood.utils.StillageLayout;
import com.neprofinishedgood.utils.Utils;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;

public class RAFActivity extends BaseActivity implements IRAFView {
    @BindView(R.id.editTextScanStillage)
    AppCompatEditText editTextScanStillage;
    @BindView(R.id.stillageDetail)
    View stillageDetail;
    @BindView(R.id.linearLayoutScanDetail)
    LinearLayout linearLayoutScanDetail;
    @BindView(R.id.spinnerShift)
    Spinner spinnerShift;

    @BindView(R.id.textViewNumberOffline)
    TextView textViewNumberOffline;
    @BindView(R.id.linearLayoutOfflineData)
    LinearLayout linearLayoutOfflineData;

    @BindView(R.id.linearLayoutEnterQtyButtons)
    LinearLayout linearLayoutEnterQtyButtons;

    @BindView(R.id.checkBoxAutoRoute)
    CheckBox checkBoxAutoRoute;

    @BindView(R.id.checkBoxAutoPicking)
    CheckBox checkBoxAutoPicking;

    long scanStillageLastTexxt = 0;
    long delay = 1500;
    IRAFInterface irafInterface;
    StillageLayout stillageLayout;
    private ArrayList<String> shiftList;
    private String shift = "";
    private String autoRoute = "0";
    private String autoPick = "0";
    private String quantity = "";

    static RAFActivity instance;

    public static RAFActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raf);
        ButterKnife.bind(this);
        instance = this;
        stillageLayout = new StillageLayout();
        ButterKnife.bind(stillageLayout, stillageDetail);

        Utils.hideSoftKeyboard(this);
        setTitle(getString(R.string.reportasfinished));
        irafInterface = new IRAFPresenter(this, this);
        editTextScanStillage.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        callService();

    }

    void setSpinnerShiftData() {
        shiftList = new ArrayList<>();
        shiftList.add("Select Shift");
        shiftList.add("A");
        shiftList.add("B");
        shiftList.add("C");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.text1, shiftList);
        spinnerShift.setAdapter(arrayAdapter);

    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanStillageChanged(Editable text) {
        if (!text.toString().trim().equals("")) {
//            scanStillagehandler.postDelayed(stillageRunnable, delay);
            if (text.toString().trim().length() == scanStillageLength) {
                if (NetworkChangeReceiver.isInternetConnected(RAFActivity.this)) {
                    showProgress(RAFActivity.this);
                    if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
                        irafInterface.callScanStillageService(new MoveInput(editTextScanStillage.getText().toString().trim(), userId));
                    }
                } else {
                    showSuccessDialog(getString(R.string.no_internet));
                    editTextScanStillage.setText("");
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
//            if (NetworkChangeReceiver.isInternetConnected(RAFActivity.this)) {
//                showProgress(RAFActivity.this);
//                if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
//                    irafInterface.callScanStillageService(new AisleInput(editTextScanStillage.getText().toString().trim(), userId));
//                }
//            } else {
//                setDataOffline();
//            }
//        }
//    };

    @Override
    public void onFailure(String message) {
        hideProgress();
        showSuccessDialog(message);
//        CustomToast.showToast(this, message);
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
//            CustomToast.showToast(getApplicationContext(), body.getMessage());
            editTextScanStillage.setText("");
        }
    }

    @Override
    public void onUpdateRAFFailure(String message) {
        hideProgress();
        if (linearLayoutScanDetail.getVisibility() == View.VISIBLE) {
            showSuccessDialog(message);
//            CustomToast.showToast(this, message);
        }
    }

    @Override
    public void onUpdateRAFSuccess(UniversalResponse body) {
        hideProgress();
        if (linearLayoutScanDetail.getVisibility() == View.VISIBLE) {
            if (body.getStatus().equals(getString(R.string.success))) {
                isScanned = false;
                showSuccessDialog(body.getMessage());
                cancelClick();
            } else {
                showSuccessDialog(body.getMessage());
//                CustomToast.showToast(this, getString(R.string.something_went_wrong_please_try_again));
            }
        }
    }

    private void setData(ScanStillageResponse body) {
        setSpinnerShiftData();
        linearLayoutOfflineData.setVisibility(View.GONE);
        linearLayoutScanDetail.setVisibility(View.VISIBLE);
        linearLayoutEnterQtyButtons.setVisibility(View.VISIBLE);
        editTextScanStillage.setEnabled(false);
        stillageLayout.textViewitem.setText(body.getItemId());
        stillageLayout.textViewQuantity.setText(body.getStandardQty() + "");
        stillageLayout.textViewitemDesc.setText(body.getDescription());
        stillageLayout.textViewStdQuantity.setText(body.getItemStdQty() + "");
        stillageLayout.textViewNumber.setText(body.getStickerID());

        stillageLayout.linearLayoutWorkOrderNo.setVisibility(View.VISIBLE);
        stillageLayout.textViewWorkOrderNumber.setText(body.getWorkOrderNo());

//        editTextQuantity.setText(body.getStandardQty() + "");
//        editTextQuantity.setSelection(body.getStandardQty().toString().length());
//        editTextQuantity.requestFocus();
        quantity = body.getStandardQty() + "";
        checkBoxAutoRoute.setChecked(true);
        checkBoxAutoPicking.setChecked(true);
    }

    @OnItemSelected(R.id.spinnerShift)
    public void spinnerShiftSelected(Spinner spinner, int position) {
        shift = shiftList.get(position);
    }

    @OnCheckedChanged(R.id.checkBoxAutoPicking)
    public void onCheckBoxAutoPickingChanged() {
        if (checkBoxAutoPicking.isChecked()) {
            autoPick = "1";
        } else {
            autoPick = "0";
        }
    }

    @OnCheckedChanged(R.id.checkBoxAutoRoute)
    public void onCheckBoxAutoRouteChanged() {
        if (checkBoxAutoRoute.isChecked()) {
            autoRoute = "1";
        } else {
            autoRoute = "0";
        }
    }

    @OnClick(R.id.buttonConfirm)
    public void onButtonConfirmClick() {
        if (linearLayoutOfflineData.getVisibility() == View.GONE) {
            if (spinnerShift.getSelectedItemPosition() == 0) {
                TextView textView = (TextView) spinnerShift.getSelectedView();
                textView.setError(getString(R.string.select_shift));
                textView.requestFocus();
            } else {
                if (NetworkChangeReceiver.isInternetConnected(RAFActivity.this)) {
                    showProgress(this);
                    RafInput rafInput = new RafInput(editTextScanStillage.getText().toString().trim(), userId, shift, quantity, autoPick, autoRoute);
                    irafInterface.callRAFServcie(rafInput);
                } else {
                    showSuccessDialog(getString(R.string.no_internet));
                }
            }
        } else {
            if (isOfflineValidated()) {
                RafInput rafInput = new RafInput(editTextScanStillage.getText().toString().trim(), userId, shift, quantity, autoPick, autoRoute);
                saveDataOffline(rafInput);
            }
        }

    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        showCancelAlert(1);
    }

    public void cancelClick() {
        isScanned = false;
        linearLayoutScanDetail.setVisibility(View.GONE);
        linearLayoutEnterQtyButtons.setVisibility(View.GONE);
        editTextScanStillage.setEnabled(true);
        editTextScanStillage.setText("");
        checkBoxAutoRoute.setChecked(false);
        checkBoxAutoPicking.setChecked(false);
    }

    public void imageButtonHomeClick(View view) {
        if (isScanned) {
            showBackAlert(new Intent(RAFActivity.this, DashBoardAcivity.class), true);
        } else {
            finishAffinity();
            startActivity(new Intent(RAFActivity.this, DashBoardAcivity.class));
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
        setSpinnerShiftData();

    }

    void setVisibilityInOfflineMode() {
        editTextScanStillage.setEnabled(false);
        linearLayoutOfflineData.setVisibility(View.VISIBLE);
        stillageDetail.setVisibility(View.GONE);
        linearLayoutScanDetail.setVisibility(View.VISIBLE);
        linearLayoutEnterQtyButtons.setVisibility(View.VISIBLE);
    }

    void disableVisibility() {
        linearLayoutScanDetail.setVisibility(View.GONE);
        linearLayoutEnterQtyButtons.setVisibility(View.VISIBLE);
        editTextScanStillage.setEnabled(true);
        editTextScanStillage.setText("");
        linearLayoutOfflineData.setVisibility(View.GONE);
        stillageDetail.setVisibility(View.VISIBLE);
//        editTextQuantity.setText("");
    }

    void saveDataOffline(RafInput data) {
        ArrayList<RafInput> rafList = new ArrayList<>();
        Gson gson = new Gson();
        String rafData = SharedPref.getRafData();
        if (!rafData.equals("")) {
            Type type = new TypeToken<ArrayList<RafInput>>() {
            }.getType();
            rafList = gson.fromJson(rafData, type);
        }
        rafList.add(data);
        String json = gson.toJson(rafList);
        SharedPref.saveRafData(json);
        showSuccessDialog(getResources().getString(R.string.data_saved_offline));
//        CustomToast.showToast(this, getResources().getString(R.string.data_saved_offline));
        cancelClick();
        editTextScanStillage.setEnabled(true);
        disableVisibility();
    }

    boolean isOfflineValidated() {
        if (spinnerShift.getSelectedItemPosition() == 0) {
            TextView textView = (TextView) spinnerShift.getSelectedView();
            textView.setError(getString(R.string.select_shift));
            textView.requestFocus();
            return false;
        }
//        if (editTextQuantity.getText().toString().equals("")) {
//            editTextQuantity.setError(getString(R.string.enter_quantity));
//            editTextQuantity.requestFocus();
//            return false;
//        }
        return true;
    }

    public void callService() {
        if (NetworkChangeReceiver.isInternetConnected(RAFActivity.this)) {
            ArrayList<RafInput> rafList = new ArrayList<>();
            Gson gson = new Gson();
            String rafData = SharedPref.getRafData();
            if (!rafData.equals("")) {
                Type type = new TypeToken<ArrayList<RafInput>>() {
                }.getType();
                rafList = gson.fromJson(rafData, type);
                showProgress(this);
                for (RafInput rafInput : rafList) {
                    irafInterface.callRAFServcie(rafInput);
                }
                String json = "";
                SharedPref.saveRafData(json);
            }
        }
    }
}
