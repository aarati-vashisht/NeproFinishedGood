package com.neprofinishedgood.receivestillage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatEditText;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.dashboard.DashBoardAcivity;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.qualitycheck.rejectquantity.RejectQuantityActivity;
import com.neprofinishedgood.receivestillage.presenter.IRecieveTransferInterface;
import com.neprofinishedgood.receivestillage.presenter.IRecieveTransferView;
import com.neprofinishedgood.receivestillage.presenter.RecieveTransferPresenter;
import com.neprofinishedgood.utils.NetworkChangeReceiver;
import com.neprofinishedgood.utils.StillageLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class ReceiveStillageActivity extends BaseActivity implements IRecieveTransferView {

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

    static ReceiveStillageActivity instance;

    public static ReceiveStillageActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_return_stillage);
        ButterKnife.bind(this);
        instance = this;
        stillageLayout = new StillageLayout();
        ButterKnife.bind(stillageLayout, stillageDetail);
        setTitle(getString(R.string.recieve_return_stillage));
        iRecieveTransferInterface = new RecieveTransferPresenter(this, this);
        editTextScanStillage.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        initData();
    }

    void initData() {

    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanStillageChanged(Editable text) {
        if (!text.toString().trim().equals("")) {
//            scanStillagehandler.postDelayed(stillageRunnable, delay);
            if (text.toString().trim().length() == scanStillageLength) {
                if (NetworkChangeReceiver.isInternetConnected(ReceiveStillageActivity.this)) {
                    showProgress(ReceiveStillageActivity.this);
                    iRecieveTransferInterface.callScanStillageService(new MoveInput(editTextScanStillage.getText().toString().trim(), userId));
                } else {
                    showSuccessDialog(getString(R.string.no_internet));
//                    CustomToast.showToast(ReceiveStillageActivity.this, getString(R.string.no_internet));
                    editTextScanStillage.setText("");
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
//            if (NetworkChangeReceiver.isInternetConnected(ReceiveStillageActivity.this)) {
//                showProgress(ReceiveStillageActivity.this);
//                if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
//                    iRecieveTransferInterface.callScanStillageService(new AisleInput(editTextScanStillage.getText().toString().trim(), userId));
//
//                }
//            } else {
//                CustomToast.showToast(ReceiveStillageActivity.this, getString(R.string.no_internet));
//                editTextScanStillage.setText("");
//            }
//        }
//    };

    @OnClick(R.id.buttonReceive)
    public void onButtonReceiveClick() {
        showProgress(this);
        iRecieveTransferInterface.callUpdateRecieveTransferStillage(new MoveInput(editTextScanStillage.getText().toString().trim(), userId));
        //CustomToast.showToast(ReceiveStillageActivity.this, getResources().getString(R.string.item_received_successfully));

    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        showCancelAlert(6);
    }

    public void cancelClick(){
        isScanned = false;
        editTextScanStillage.setText("");
        editTextScanStillage.setEnabled(true);
        relativeLayoutScanDetail.setVisibility(View.GONE);
        relativeLayoutScanDetail.setAnimation(fadeOut);
    }

    @Override
    public void onFailure(String message) {
        hideProgress();
        editTextScanStillage.setText("");
        editTextScanStillage.setEnabled(true);
        showSuccessDialog(message);
//        CustomToast.showToast(this, message);
    }

    @Override
    public void onSuccess(ScanStillageResponse body) {
        if (body.getStatus().equalsIgnoreCase(getString(R.string.success))) {
            hideProgress();
            if (body.getIsRecieved() == 1) {
                editTextScanStillage.setText("");
                editTextScanStillage.setEnabled(true);
                showSuccessDialog(getString(R.string.this_stillage_already_recieved));
//                CustomToast.showToast(this, getString(R.string.this_stillage_already_recieved));
            } else {
                if (body.getStandardQty() > 0) {
                    isScanned = true;
                    setData(body);
                    editTextScanStillage.setEnabled(false);
                }
                else{
                    showSuccessDialog(getResources().getString(R.string.stillage_discarded));
                    editTextScanStillage.setText("");
                    editTextScanStillage.setEnabled(true);
                }
            }
        } else {
            hideProgress();
            showSuccessDialog(body.getMessage());
//            CustomToast.showToast(this, body.getMessage());
            editTextScanStillage.setText("");
            editTextScanStillage.setEnabled(true);
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
        showSuccessDialog(message);
//        CustomToast.showToast(this, message);

    }

    @Override
    public void onUpdateRecieveTransferSuccess(UniversalResponse body) {
        hideProgress();
        isScanned = false;
        showSuccessDialog(body.getMessage());
//        CustomToast.showToast(this, body.getMessage());
        relativeLayoutScanDetail.setVisibility(View.GONE);
        editTextScanStillage.setText("");
        editTextScanStillage.setEnabled(true);
    }

    public void imageButtonHomeClick(View view) {
        if (isScanned) {
            showBackAlert(new Intent(ReceiveStillageActivity.this, DashBoardAcivity.class), true);
        } else {
            finishAffinity();
            startActivity(new Intent(ReceiveStillageActivity.this, DashBoardAcivity.class));
        }
    }

    public void imageButtonBackClick(View view) {
        if (isScanned) {
            showBackAlert(null, false);
        } else {
            finish();
        }
    }

    public void onBackPressed(){
        if (isScanned) {
            showBackAlert(null, false);
        } else {
            finish();
        }
    }

}
