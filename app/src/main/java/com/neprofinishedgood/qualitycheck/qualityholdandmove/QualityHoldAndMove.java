package com.neprofinishedgood.qualitycheck.qualityholdandmove;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatEditText;

import com.google.gson.Gson;
import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.qualitycheck.qualityholdandmove.presenter.IHoldPresenter;
import com.neprofinishedgood.qualitycheck.qualityholdandmove.presenter.IHoldView;
import com.neprofinishedgood.raf.model.ScanCountingResponse;
import com.neprofinishedgood.raf.model.StillageList;
import com.neprofinishedgood.utils.Constants;
import com.neprofinishedgood.utils.StillageLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class QualityHoldAndMove extends BaseActivity implements IHoldView {

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
    private StillageList body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_hold_and_move);
        ButterKnife.bind(this);
        stillageLayout = new StillageLayout();
        ButterKnife.bind(stillageLayout, stillageDetail);
        setTitle(getString(R.string.quality_hold_and_move));
        initData();
        iHoldPresenter = new IHoldPresenter(this);

    }

    void initData() {
        SELECTED_STILLAGE = getIntent().getStringExtra(Constants.SELECTED_STILLAGE);
        StillageList stillageList = new Gson().fromJson(SELECTED_STILLAGE, StillageList.class);
        if (stillageList != null)
            setData(stillageList);
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
            showProgress(QualityHoldAndMove.this);
            if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
                iHoldPresenter.callScanStillageService(new MoveInput(editTextScanStillage.getText().toString().trim(), userId));
            }
        }
    };


    @Override
    public void onFailure(String message) {
        hideProgress();
        CustomToast.showToast(this, getString(R.string.no_data_found));
    }

    @Override
    public void onSuccess(ScanCountingResponse body) {
        hideProgress();
        // initData();
        if (body.getStatus().equals(getResources().getString(R.string.success))) {
            setData(body.getStillageList().get(0));
        } else {
            CustomToast.showToast(this, getString(R.string.no_data_found));
            editTextScanStillage.setText("");
        }
    }

    @Override
    public void onHoldUnholdFailure(String message) {
        hideProgress();
        CustomToast.showToast(this, message);
    }

    @Override
    public void onHoldUnholdSuccess(UniversalResponse body) {
        hideProgress();
        linearLayoutScanDetail.setVisibility(View.GONE);
        editTextScanStillage.setEnabled(true);editTextScanStillage.setText("");
        CustomToast.showToast(this, body.getMessage());
    }

    void setData(StillageList body) {
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
        showProgress(this);
        MoveInput moveInput = new MoveInput(editTextScanStillage.getText().toString().trim(), userId);
        iHoldPresenter.callHoldUnholdService(moveInput);
    }

    @OnClick(R.id.buttonUnhold)
    public void onButtonUnholdClick() {
        showProgress(this);
        MoveInput moveInput = new MoveInput(editTextScanStillage.getText().toString().trim(), userId);
        iHoldPresenter.callHoldUnholdService(moveInput);
    }

}
