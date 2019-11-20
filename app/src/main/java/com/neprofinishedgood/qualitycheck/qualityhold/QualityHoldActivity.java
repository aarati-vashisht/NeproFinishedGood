package com.neprofinishedgood.qualitycheck.qualityhold;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatEditText;

import com.google.gson.Gson;
import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.dashboard.DashBoardAcivity;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.qualitycheck.model.QualityInput;
import com.neprofinishedgood.qualitycheck.qualityhold.presenter.IHoldPresenter;
import com.neprofinishedgood.qualitycheck.qualityhold.presenter.IHoldView;
import com.neprofinishedgood.utils.Constants;
import com.neprofinishedgood.utils.NetworkChangeReceiver;
import com.neprofinishedgood.utils.StillageLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class QualityHoldActivity extends BaseActivity implements IHoldView {

    @BindView(R.id.linearLayoutScanDetail)
    LinearLayout linearLayoutScanDetail;

    @BindView(R.id.stillageDetail)
    View stillageDetail;

    StillageLayout stillageLayout;

    @BindView(R.id.buttonHold)
    CustomButton buttonHold;

    @BindView(R.id.buttonUnhold)
    CustomButton buttonUnhold;

    @BindView(R.id.editTextScanStillage)
    AppCompatEditText editTextScanStillage;

    long delay = 1500;
    private String SELECTED_STILLAGE = "";
    long scanStillageLastTexxt = 0;
    private IHoldPresenter iHoldPresenter;
    private ScanStillageResponse body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_hold);
        ButterKnife.bind(this);
        stillageLayout = new StillageLayout();
        ButterKnife.bind(stillageLayout, stillageDetail);
        setTitle(getString(R.string.quality_hold_and_move));
        initData();
        editTextScanStillage.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        iHoldPresenter = new IHoldPresenter(this, this);

    }

    void initData() {
        SELECTED_STILLAGE = getIntent().getStringExtra(Constants.SELECTED_STILLAGE);
        ScanStillageResponse stillageList = new Gson().fromJson(SELECTED_STILLAGE, ScanStillageResponse.class);
        if (stillageList != null)
            setData(stillageList);
    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanStillageChanged(Editable text) {
        if (!text.toString().trim().equals("")) {
//            scanStillagehandler.postDelayed(stillageRunnable, delay);
            if (text.toString().trim().length() == scanStillageLength) {
                if (NetworkChangeReceiver.isInternetConnected(QualityHoldActivity.this)) {
                    showProgress(QualityHoldActivity.this);
                    if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
                        iHoldPresenter.callScanStillageService(new MoveInput(editTextScanStillage.getText().toString().trim(), userId));
                    }
                } else {
                    editTextScanStillage.setText("");
                    showSuccessDialog(getString(R.string.no_internet));
//                    CustomToast.showToast(QualityHoldActivity.this, getString(R.string.no_internet));
                }
            }
        }

    }

//    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.TEXT_CHANGED)
//    public void onEditTextScanStillageTEXTCHANGED(Editable text) {
//        scanStillagehandler.removeCallbacks(stillageRunnable);
//
//
//    }
//
//    //for call service on text change
//    Handler scanStillagehandler = new Handler();
//    private Runnable stillageRunnable = new Runnable() {
//        public void run() {
//            if (NetworkChangeReceiver.isInternetConnected(QualityHoldActivity.this)) {
//                showProgress(QualityHoldActivity.this);
//                if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
//                    iHoldPresenter.callScanStillageService(new AisleInput(editTextScanStillage.getText().toString().trim(), userId));
//                }
//            } else {
//                editTextScanStillage.setText("");
//                CustomToast.showToast(QualityHoldActivity.this, getString(R.string.no_internet));
//            }
//        }
//    };


    @Override
    public void onFailure(String message) {
        hideProgress();
        showSuccessDialog(message);
//        CustomToast.showToast(this, getString(R.string.no_data_found));
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
//            CustomToast.showToast(this, getString(R.string.no_data_found));
            editTextScanStillage.setText("");
        }
    }

    @Override
    public void onHoldUnholdFailure(String message) {
        hideProgress();
        showSuccessDialog(message);
//        CustomToast.showToast(this, message);
    }

    @Override
    public void onHoldUnholdSuccess(UniversalResponse body) {
        hideProgress();
        isScanned = false;
        linearLayoutScanDetail.setVisibility(View.GONE);
        editTextScanStillage.setEnabled(true);
        editTextScanStillage.setText("");
        showSuccessDialog(body.getMessage());
//        CustomToast.showToast(this, body.getMessage());
    }

    void setData(ScanStillageResponse body) {
        this.body = body;
        linearLayoutScanDetail.setVisibility(View.VISIBLE);
        editTextScanStillage.setEnabled(false);
        stillageLayout.textViewitem.setText(body.getItemId());
        stillageLayout.textViewQuantity.setText(body.getStandardQty() + "");
        stillageLayout.textViewitemDesc.setText(body.getDescription());
        stillageLayout.textViewStdQuantity.setText(body.getItemStdQty() + "");
        stillageLayout.textViewNumber.setText(body.getStickerID());
        if (body.getIsHold().equals("0")) {
            buttonHold.setEnabled(true);
            buttonUnhold.setEnabled(false);
        } else {
            buttonHold.setEnabled(false);
            buttonUnhold.setEnabled(true);
        }

    }


    @OnClick(R.id.buttonHold)
    public void onButtonHoldClick() {
        if (NetworkChangeReceiver.isInternetConnected(QualityHoldActivity.this)) {
            showProgress(this);
            QualityInput qualityInput = new QualityInput(stillageLayout.textViewNumber.getText().toString().trim(), userId, "1");
            iHoldPresenter.callHoldUnholdService(qualityInput);
        } else {
            showSuccessDialog(getString(R.string.no_internet));
        }
    }

    @OnClick(R.id.buttonUnhold)
    public void onButtonUnholdClick() {
        if (NetworkChangeReceiver.isInternetConnected(QualityHoldActivity.this)) {
            showProgress(this);
            QualityInput qualityInput = new QualityInput(stillageLayout.textViewNumber.getText().toString().trim(), userId, "0");
            iHoldPresenter.callHoldUnholdService(qualityInput);
        } else {
            showSuccessDialog(getString(R.string.no_internet));
        }
    }

    public void imageButtonHomeClick(View view) {
        if (isScanned) {
            showBackAlert(new Intent(QualityHoldActivity.this, DashBoardAcivity.class), true);
        } else {
            finishAffinity();
            startActivity(new Intent(QualityHoldActivity.this, DashBoardAcivity.class));
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
}
