package com.neprofinishedgood.receivereturnstillage;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatEditText;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;
import com.neprofinishedgood.receivereturnstillage.presenter.IRecieveTransferInterface;
import com.neprofinishedgood.receivereturnstillage.presenter.IRecieveTransferView;
import com.neprofinishedgood.receivereturnstillage.presenter.RecieveTransferPresenter;
import com.neprofinishedgood.utils.StillageLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class ReceiveReturnStillageActivity extends BaseActivity implements IRecieveTransferView {

    @BindView(R.id.relativeLayoutScanDetail)
    RelativeLayout relativeLayoutScanDetail;

    @BindView(R.id.stillageDetail)
    View stillageDetail;

    @BindView(R.id.editTextScanStillage)
    AppCompatEditText editTextScanStillage;

    StillageLayout stillageLayout;

    long delay = 1000;
    long scanStillageLastTexxt = 0;
    private IRecieveTransferInterface iRecieveTransferInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_return_stillage);
        ButterKnife.bind(this);
        stillageLayout = new StillageLayout();
        ButterKnife.bind(stillageLayout, stillageDetail);
        setTitle(getString(R.string.recieve_return_stillage));
        iRecieveTransferInterface = new RecieveTransferPresenter(this);
        initData();
    }

    void initData() {

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
            showProgress(ReceiveReturnStillageActivity.this);
            if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
                iRecieveTransferInterface.callScanStillageService(new MoveInput(editTextScanStillage.getText().toString().trim(), userId));

            }
        }
    };

    @OnClick(R.id.buttonReceive)
    public void onButtonReceiveClick() {
        showProgress(this);
        iRecieveTransferInterface.callUpdateRecieveTransferStillage(new MoveInput(editTextScanStillage.getText().toString().trim(), userId));
        CustomToast.showToast(ReceiveReturnStillageActivity.this, getResources().getString(R.string.item_received_successfully));
        finish();
    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        editTextScanStillage.setText("");
        editTextScanStillage.setEnabled(true);
        relativeLayoutScanDetail.setVisibility(View.GONE);
        relativeLayoutScanDetail.setAnimation(fadeOut);
    }

    @Override
    public void onFailure(String message) {
        hideProgress();
        CustomToast.showToast(this, message);
    }

    @Override
    public void onSuccess(ScanStillageResponse body) {
        if (body.getStatus().equalsIgnoreCase(getString(R.string.success))) {
            hideProgress();
            setData(body);
        } else {
            hideProgress();
            CustomToast.showToast(this, body.getMessage());
            editTextScanStillage.setText("");
        }
    }

    void setData(ScanStillageResponse body) {
        relativeLayoutScanDetail.setVisibility(View.VISIBLE);
        stillageLayout.textViewitem.setText(body.getItemId());
        stillageLayout.textViewNumber.setText(body.getStickerID());
        stillageLayout.textViewQuantity.setText(body.getStandardQty() + "");
        stillageLayout.textViewStdQuantity.setText(body.getItemStdQty() + "");
        stillageLayout.textViewitemDesc.setText(body.getDescription());
    }

    @Override
    public void onUpdateRecieveTransferFailure(String message) {
        hideProgress();
        CustomToast.showToast(this, message);

    }

    @Override
    public void onUpdateRecieveTransferSuccess(UniversalResponse body) {
        hideProgress();
        CustomToast.showToast(this, body.getMessage());
        relativeLayoutScanDetail.setVisibility(View.GONE);
    }

}
