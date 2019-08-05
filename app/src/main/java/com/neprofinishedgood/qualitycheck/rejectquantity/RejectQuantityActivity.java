package com.neprofinishedgood.qualitycheck.rejectquantity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.plannedandunplannedmove.adapter.SpinnerAdapter;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;
import com.neprofinishedgood.qualitycheck.model.RejectedInput;
import com.neprofinishedgood.qualitycheck.model.ScanLotInput;
import com.neprofinishedgood.qualitycheck.presenter.IQAPresenter;
import com.neprofinishedgood.qualitycheck.presenter.IQAView;
import com.neprofinishedgood.qualitycheck.qualityhold.QualityHoldActivity;
import com.neprofinishedgood.raf.model.StillageList;
import com.neprofinishedgood.utils.Constants;
import com.neprofinishedgood.utils.NetworkChangeReceiver;
import com.neprofinishedgood.utils.SharedPref;
import com.neprofinishedgood.utils.StillageLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;

public class RejectQuantityActivity extends BaseActivity implements IQAView {

    @BindView(R.id.linearLayoutScanDetail)
    LinearLayout linearLayoutScanDetail;

    @BindView(R.id.stillageDetail)
    View stillageDetail;

    StillageLayout stillageLayout;

    @BindView(R.id.editTextRejectQuantity)
    AppCompatEditText editTextRejectQuantity;

   @BindView(R.id.editTextScanLot)
    AppCompatEditText editTextScanLot;

    @BindView(R.id.spinnerReason)
    Spinner spinnerRejectReason;

    @BindView(R.id.buttonReject)
    CustomButton buttonReject;

    @BindView(R.id.editTextScanStillage)
    AppCompatEditText editTextScanStillage;

    @BindView(R.id.linearLayoutOfflineData)
    LinearLayout linearLayoutOfflineData;

    @BindView(R.id.linearLayoutShift)
    LinearLayout linearLayoutShift;

    @BindView(R.id.frameEnterQuantity)
    FrameLayout frameEnterQuantity;

    @BindView(R.id.textViewNumberOffline)
    TextView textViewNumberOffline;

    long scanStillageLastTexxt = 0;
    long delay = 1500;
    private IQAPresenter iqaInterface;
    private String isHold, reason;
    ScanStillageResponse body;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reject_quantity);
        ButterKnife.bind(this);
        stillageLayout = new StillageLayout();
        ButterKnife.bind(stillageLayout, stillageDetail);
        String title = getIntent().getStringExtra("REJECT_TITLE");
        setTitle(title);
        iqaInterface = new IQAPresenter(this, this);
        initData();
        callService();
    }

    private void initData() {
        SpinnerAdapter reasonListAdapter = new SpinnerAdapter(RejectQuantityActivity.this, R.layout.spinner_layout, reasonList);
        spinnerRejectReason.setAdapter(reasonListAdapter);
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
            if (NetworkChangeReceiver.isInternetConnected(RejectQuantityActivity.this)) {
                showProgress(RejectQuantityActivity.this);
                if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
                    iqaInterface.callScanStillageService(new MoveInput(editTextScanStillage.getText().toString().trim(), userId));
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
    public void onUpdateRejectedFailure(String message) {
        hideProgress();
        if (linearLayoutScanDetail.getVisibility() == View.VISIBLE) {
            CustomToast.showToast(this, message);
        }
    }

    @Override
    public void onUpdateRejectedSuccess(UniversalResponse body) {
        hideProgress();
        // initData();
        if (linearLayoutScanDetail.getVisibility() == View.VISIBLE) {
            if (body.getStatus().equals(getResources().getString(R.string.success))) {
                CustomToast.showToast(getApplicationContext(), getString(R.string.items_rejected_successfully));
                linearLayoutScanDetail.setVisibility(View.GONE);
                editTextScanStillage.setEnabled(true);
                editTextScanStillage.setText("");
                if (isHold.equals("1")) {
                    String SELECTED_STILLAGE = new Gson().toJson(body, StillageList.class);
                    startActivity(new Intent(this, QualityHoldActivity.class).putExtra(Constants.SELECTED_STILLAGE, SELECTED_STILLAGE));
                    finish();
                }
            } else {
                CustomToast.showToast(getApplicationContext(), body.getMessage());
            }
        }
        spinnerRejectReason.setSelection(0);
    }

    @OnItemSelected(R.id.spinnerReason)
    public void spinnerBinSelected(Spinner spinner, int position) {
        reason = reasonList.get(position).getId();
    }

    void setData(ScanStillageResponse body) {
        this.body = body;
        isHold = body.getIsHold();
        linearLayoutScanDetail.setVisibility(View.VISIBLE);
        editTextScanStillage.setEnabled(false);
        stillageLayout.textViewitem.setText(body.getItemId());
        stillageLayout.textViewQuantity.setText(body.getStandardQty() + "");
        stillageLayout.textViewitemDesc.setText(body.getDescription());
        stillageLayout.textViewStdQuantity.setText(body.getItemStdQty() + "");
        stillageLayout.textViewNumber.setText(body.getStickerID());
        editTextRejectQuantity.setText(body.getStandardQty() + "");
        editTextRejectQuantity.setSelection(body.getStandardQty().toString().length());
        editTextRejectQuantity.requestFocus();

    }

    @OnClick(R.id.buttonReject)
    public void onButtonRejectClick() {
        if (linearLayoutOfflineData.getVisibility() == View.GONE) {
            if (isValidated()) {
                showProgress(this);
                RejectedInput rejectedInput = new RejectedInput(editTextScanStillage.getText().toString().trim(), userId, editTextRejectQuantity.getText().toString().trim(), reason);
                iqaInterface.callUpdateRejectedService(rejectedInput);
            }
        } else {
            if (isOfflineValidated()) {
                RejectedInput rejectedInput = new RejectedInput(editTextScanStillage.getText().toString().trim(), userId, editTextRejectQuantity.getText().toString().trim(), reason);
                saveDataOffline(rejectedInput);
            }
        }

    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        linearLayoutScanDetail.setVisibility(View.GONE);
        linearLayoutScanDetail.setAnimation(fadeOut);
        editTextScanStillage.setText("");
        editTextScanStillage.setEnabled(true);
        spinnerRejectReason.setSelection(0);
    }

    boolean isValidated() {
        if (editTextRejectQuantity.getText().toString().equals("0")) {
            editTextRejectQuantity.setError(getResources().getString(R.string.enter_reject_quantity));
            editTextRejectQuantity.requestFocus();
            return false;
        }
        if (spinnerRejectReason.getSelectedItemPosition() == 0) {
            TextView textView = (TextView) spinnerRejectReason.getSelectedView();
            textView.setError(getString(R.string.select_reason));
            textView.requestFocus();
            return false;
        }
        return true;
    }



    @OnTextChanged(value = R.id.editTextScanLot, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanLotChanged(Editable text) {
        if (!text.toString().trim().equals("")) {
            scanLothandler.postDelayed(lotRunnable, delay);
        }

    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void onEditTextScanLotTEXTCHANGED(Editable text) {
        scanLothandler.removeCallbacks(lotRunnable);

    }

    //for call service on text change
    Handler scanLothandler = new Handler();
    private Runnable lotRunnable = new Runnable() {
        public void run() {
            if (NetworkChangeReceiver.isInternetConnected(RejectQuantityActivity.this)) {
                showProgress(RejectQuantityActivity.this);
                if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
                    iqaInterface.callScanLotService(new ScanLotInput(body.getWorkOrderNo(),editTextScanLot.getText().toString().trim(), userId));
                }
            } else {
                setDataOffline();
            }
        }
    };

    @Override
    public void onLotScanFailure(String message) {
        hideProgress();
        CustomToast.showToast(this, message);
    }

    @Override
    public void onLotScanSuccess(UniversalResponse body) {
        hideProgress();
        if (body.getStatus().equals(getResources().getString(R.string.success))) {
            linearLayoutShift.setVisibility(View.VISIBLE);
            frameEnterQuantity.setVisibility(View.VISIBLE);
        } else {
            CustomToast.showToast(getApplicationContext(), body.getMessage());
            editTextScanLot.setText("");
        }
    }



    void setDataOffline() {
        textViewNumberOffline.setText(editTextScanStillage.getText().toString());
        setVisibilityInOfflineMode();
        initData();
        editTextRejectQuantity.setText("");

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
        editTextRejectQuantity.setText("");
    }

    void saveDataOffline(RejectedInput data) {
        ArrayList<RejectedInput> rejectList = new ArrayList<>();
        Gson gson = new Gson();
        String rejectData = SharedPref.getRejectData();
        if (!rejectData.equals("")) {
            Type type = new TypeToken<ArrayList<RejectedInput>>() {
            }.getType();
            rejectList = gson.fromJson(rejectData, type);
        }
        rejectList.add(data);
        String json = gson.toJson(rejectList);
        SharedPref.saveRejectData(json);
        CustomToast.showToast(this, getResources().getString(R.string.data_saved_offline));
        onButtonCancelClick();
        disableVisibility();
    }

    boolean isOfflineValidated() {
        if (spinnerRejectReason.getSelectedItemPosition() == 0) {
            TextView textView = (TextView) spinnerRejectReason.getSelectedView();
            textView.setError(getString(R.string.select_shift));
            textView.requestFocus();
            return false;
        }
        if (editTextRejectQuantity.getText().toString().equals("")) {
            editTextRejectQuantity.setError(getString(R.string.enter_reject_quantity));
            editTextRejectQuantity.requestFocus();
            return false;
        }
        return true;
    }

    public void callService() {
        if (NetworkChangeReceiver.isInternetConnected(RejectQuantityActivity.this)) {
            ArrayList<RejectedInput> rejectList = new ArrayList<>();
            Gson gson = new Gson();
            String rejectData = SharedPref.getRejectData();
            if (!rejectData.equals("")) {
                Type type = new TypeToken<ArrayList<RejectedInput>>() {
                }.getType();
                rejectList = gson.fromJson(rejectData, type);

                for (RejectedInput rejectedInput : rejectList) {
                    iqaInterface.callUpdateRejectedService(rejectedInput);
                }
                String json = "";
                SharedPref.saveRejectData(json);
            }
        }
    }
}
