package com.neprofinishedgood.qualitycheck.rejectcompletestillage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.view.animation.Animation;
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
import com.neprofinishedgood.base.model.UniversalSpinner;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.move.adapter.SpinnerAdapter;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.qualitycheck.model.RejectedCompleteInput;
import com.neprofinishedgood.qualitycheck.model.RejectedInput;
import com.neprofinishedgood.qualitycheck.rejectcompletestillage.presenter.IQACompletePresenter;
import com.neprofinishedgood.qualitycheck.rejectcompletestillage.presenter.IQACompleteView;
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

public class RejectCompleteStillage extends BaseActivity implements IQACompleteView{

    @BindView(R.id.linearLayoutScanDetail)
    LinearLayout linearLayoutScanDetail;


    @BindView(R.id.stillageDetail)
    View stillageDetail;

    StillageLayout stillageLayout;

    @BindView(R.id.spinnerReason)
    Spinner spinnerRejectReason;

    @BindView(R.id.buttonReject)
    CustomButton buttonReject;

    @BindView(R.id.editTextScanStillage)
    AppCompatEditText editTextScanStillage;

    @BindView(R.id.linearLayoutOfflineData)
    LinearLayout linearLayoutOfflineData;

    @BindView(R.id.textViewNumberOffline)
    TextView textViewNumberOffline;

    @BindView(R.id.spinnerShift)
    Spinner spinnerShift;

    Animation fadeOut;
    Animation fadeIn;

    ArrayList<UniversalSpinner> reasons;

    long scanStillageLastTexxt = 0;
    private String reason, isHold, shift;
    private IQACompletePresenter iQACompletePresenter;
    long delay = 1500;

    private ArrayList<String> shiftList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reject_complete_stillage);
        ButterKnife.bind(this);
        stillageLayout = new StillageLayout();
        ButterKnife.bind(stillageLayout, stillageDetail);
        setTitle(getString(R.string.reject_complete_stillage));
        iQACompletePresenter = new IQACompletePresenter(this, this);
        initData();
        callService();
    }

    private void initData() {
        SpinnerAdapter reasonListAdapter = new SpinnerAdapter(RejectCompleteStillage.this, R.layout.spinner_layout, reasonList);
        spinnerRejectReason.setAdapter(reasonListAdapter);
        setSpinnerShiftData();
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
            if (NetworkChangeReceiver.isInternetConnected(RejectCompleteStillage.this)) {
                showProgress(RejectCompleteStillage.this);
                if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
                    iQACompletePresenter.callScanStillageService(new MoveInput(editTextScanStillage.getText().toString().trim(), userId));
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
                    String SELECTED_STILLAGE = new Gson().toJson(this.body, ScanStillageResponse.class);
                    startActivity(new Intent(this, QualityHoldActivity.class).putExtra(Constants.SELECTED_STILLAGE, SELECTED_STILLAGE));
                    finish();
                }
            } else {
                CustomToast.showToast(getApplicationContext(), body.getMessage());
            }
        }
        spinnerRejectReason.setSelection(0);
    }

//    @Override
//    public void onLotScanFailure(String message) {
//        hideProgress();
//        CustomToast.showToast(this, message);
//    }
//
//    @Override
//    public void onLotScanSuccess(UniversalResponse body) {
//        hideProgress();
//        if (body.getStatus().equals(getResources().getString(R.string.success))) {
////            linearLayoutShift.setVisibility(View.VISIBLE);
////            frameEnterQuantity.setVisibility(View.VISIBLE);
//        } else {
//            CustomToast.showToast(getApplicationContext(), body.getMessage());
//            editTextScanStillage.setText("");
//        }
//    }


    ScanStillageResponse body;

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
        setSpinnerShiftData();

    }

    @OnClick(R.id.buttonReject)
    public void onButtonRejectClick() {
        if (linearLayoutOfflineData.getVisibility() == View.GONE) {
            if (isValidated()) {
                showProgress(this);
                RejectedCompleteInput rejectedCompleteInput = new RejectedCompleteInput(editTextScanStillage.getText().toString().trim(), userId, reason, shift);
                iQACompletePresenter.callUpdateRejectedService(rejectedCompleteInput);
            }
        } else {
            if (isOfflineValidated()) {
                RejectedCompleteInput rejectedCompleteInput = new RejectedCompleteInput(editTextScanStillage.getText().toString().trim(), userId, reason, shift);
                saveDataOffline(rejectedCompleteInput);
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
        if (spinnerRejectReason.getSelectedItemPosition() == 0) {
            TextView textView = (TextView) spinnerRejectReason.getSelectedView();
            textView.setError(getString(R.string.select_reason));
            textView.requestFocus();
            return false;
        }
        if (spinnerShift.getSelectedItemPosition() == 0) {
            TextView textView = (TextView) spinnerShift.getSelectedView();
            textView.setError(getString(R.string.select_shift));
            textView.requestFocus();
            return false;
        }
        return true;
    }


    @OnItemSelected(R.id.spinnerShift)
    public void spinnerShiftSelected(Spinner spinner, int position) {
        shift = shiftList.get(position);
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


    void setDataOffline() {
        textViewNumberOffline.setText(editTextScanStillage.getText().toString());
        setVisibilityInOfflineMode();
        initData();
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
    }

    void saveDataOffline(RejectedCompleteInput data) {
        ArrayList<RejectedCompleteInput> rejectList = new ArrayList<>();
        Gson gson = new Gson();
        String rejectData = SharedPref.getCompleteRejectData();
        if (!rejectData.equals("")) {
            Type type = new TypeToken<ArrayList<RejectedCompleteInput>>() {
            }.getType();
            rejectList = gson.fromJson(rejectData, type);
        }
        rejectList.add(data);
        String json = gson.toJson(rejectList);
        SharedPref.saveCompleteRejectData(json);
        CustomToast.showToast(this, getResources().getString(R.string.data_saved_offline));
        onButtonCancelClick();
        editTextScanStillage.setEnabled(true);
        disableVisibility();
    }

    boolean isOfflineValidated() {
        if (spinnerRejectReason.getSelectedItemPosition() == 0) {
            TextView textView = (TextView) spinnerRejectReason.getSelectedView();
            textView.setError(getString(R.string.select_shift));
            textView.requestFocus();
            return false;
        }
        return true;
    }

    public void callService() {
        if (NetworkChangeReceiver.isInternetConnected(RejectCompleteStillage.this)) {
            ArrayList<RejectedCompleteInput> rejectList = new ArrayList<>();
            Gson gson = new Gson();
            String rejectData = SharedPref.getCompleteRejectData();
            if (!rejectData.equals("")) {
                Type type = new TypeToken<ArrayList<RejectedCompleteInput>>() {
                }.getType();
                rejectList = gson.fromJson(rejectData, type);

                for (RejectedCompleteInput rejectedInput : rejectList) {
                    iQACompletePresenter.callUpdateRejectedService(rejectedInput);
                }
                String json = "";
                SharedPref.saveCompleteRejectData(json);
            }
        }
    }
}
