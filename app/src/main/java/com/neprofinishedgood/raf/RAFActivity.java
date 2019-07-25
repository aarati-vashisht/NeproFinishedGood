package com.neprofinishedgood.raf;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.plannedandunplannedmove.PlannedAndUnPlannedMoveActivity;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.UpdateMoveLocationInput;
import com.neprofinishedgood.raf.model.RafInput;
import com.neprofinishedgood.raf.presenter.IRAFInterface;
import com.neprofinishedgood.raf.presenter.IRAFPresenter;
import com.neprofinishedgood.raf.presenter.IRAFView;
import com.neprofinishedgood.utils.Constants;
import com.neprofinishedgood.utils.NetworkChangeReceiver;
import com.neprofinishedgood.utils.SharedPref;
import com.neprofinishedgood.utils.StillageLayout;
import com.neprofinishedgood.utils.Utils;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;

public class RAFActivity extends BaseActivity implements IRAFView {
    @BindView(R.id.editTextScanStillage)
    AppCompatEditText editTextScanStillage;
    @BindView(R.id.editTextQuantity)
    AppCompatEditText editTextQuantity;
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

    long scanStillageLastTexxt = 0;
    long delay = 1500;
    IRAFInterface irafInterface;
    StillageLayout stillageLayout;
    private ArrayList<String> shiftList;
    private String shift = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raf);
        ButterKnife.bind(this);
        stillageLayout = new StillageLayout();
        ButterKnife.bind(stillageLayout, stillageDetail);

        Utils.hideSoftKeyboard(this);
        setTitle(getString(R.string.reportasfinished));
        irafInterface = new IRAFPresenter(this, this);

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
            if (NetworkChangeReceiver.isInternetConnected(RAFActivity.this)) {
                showProgress(RAFActivity.this);
                if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
                    irafInterface.callScanStillageService(new MoveInput(editTextScanStillage.getText().toString().trim(), userId));
                }
            } else {
                setDataOffline();
            }
        }
    };


    @Override
    public void onFailure(String message) {
        hideProgress();
        CustomToast.showToast(this, message);
    }

    @Override
    public void onSuccess(ScanStillageResponse body) {
        hideProgress();
        // initData();
        if (body.getStatus().equals(getResources().getString(R.string.success))) {
            setData(body);
        } else {
            CustomToast.showToast(getApplicationContext(), body.getMessage());
            editTextScanStillage.setText("");
        }
    }

    @Override
    public void onUpdateRAFFailure(String message) {
        hideProgress();
        if (linearLayoutScanDetail.getVisibility() == View.VISIBLE) {
            CustomToast.showToast(this, message);
        }
    }

    @Override
    public void onUpdateRAFSuccess(UniversalResponse body) {
        hideProgress();
        if (linearLayoutScanDetail.getVisibility() == View.VISIBLE) {
            CustomToast.showToast(this, body.getMessage());
            linearLayoutScanDetail.setVisibility(View.GONE);
            editTextScanStillage.setEnabled(true);
            editTextScanStillage.setText("");
        }
    }

    private void setData(ScanStillageResponse body) {
        setSpinnerShiftData();
        linearLayoutOfflineData.setVisibility(View.GONE);
        linearLayoutScanDetail.setVisibility(View.VISIBLE);
        editTextScanStillage.setEnabled(false);
        stillageLayout.textViewitem.setText(body.getItemId());
        stillageLayout.textViewQuantity.setText(body.getStandardQty() + "");
        stillageLayout.textViewitemDesc.setText(body.getDescription());
        stillageLayout.textViewStdQuantity.setText(body.getItemStdQty() + "");
        stillageLayout.textViewNumber.setText(body.getStickerID());
        editTextQuantity.setText(body.getStandardQty() + "");
        editTextQuantity.setSelection(body.getStandardQty().toString().length());
        editTextQuantity.requestFocus();
    }

    @OnItemSelected(R.id.spinnerShift)
    public void spinnerBinSelected(Spinner spinner, int position) {
        shift = shiftList.get(position);
    }

    @OnClick(R.id.buttonConfirm)
    public void onButtonConfirmClick() {
        if (linearLayoutOfflineData.getVisibility() == View.GONE) {
            if (spinnerShift.getSelectedItemPosition() == 0) {
                TextView textView = (TextView) spinnerShift.getSelectedView();
                textView.setError(getString(R.string.select_shift));
            } else {
                showProgress(this);
                RafInput rafInput = new RafInput(editTextScanStillage.getText().toString().trim(), userId, shift, editTextQuantity.getText().toString().trim());
                irafInterface.callRAFServcie(rafInput);
            }
        } else {
            if (isOfflineValidated()) {
                RafInput rafInput = new RafInput(editTextScanStillage.getText().toString().trim(), userId, shift, editTextQuantity.getText().toString().trim());
                saveDataOffline(rafInput);
            }
        }

    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        linearLayoutScanDetail.setVisibility(View.GONE);
        editTextScanStillage.setEnabled(true);
        editTextScanStillage.setText("");
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
    }

    void disableVisibility() {
        linearLayoutScanDetail.setVisibility(View.GONE);
        editTextScanStillage.setEnabled(true);
        editTextScanStillage.setText("");
        linearLayoutOfflineData.setVisibility(View.GONE);
        stillageDetail.setVisibility(View.VISIBLE);
        editTextQuantity.setText("");
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
        CustomToast.showToast(this, getResources().getString(R.string.data_saved_offline));
        onButtonCancelClick();
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
        if (editTextQuantity.getText().toString().equals("")) {
            editTextQuantity.setError(getString(R.string.enter_quantity));
            editTextQuantity.requestFocus();
            return false;
        }
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
