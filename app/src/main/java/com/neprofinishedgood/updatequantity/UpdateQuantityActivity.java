package com.neprofinishedgood.updatequantity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatEditText;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;
import com.neprofinishedgood.raf.model.StillageDatum;
import com.neprofinishedgood.updatequantity.model.UpdateQtyInput;
import com.neprofinishedgood.updatequantity.presenter.IUpdateQtyInterface;
import com.neprofinishedgood.updatequantity.presenter.IUpdateQtyView;
import com.neprofinishedgood.utils.StillageLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class UpdateQuantityActivity extends BaseActivity implements IUpdateQtyView {

    @BindView(R.id.linearLayoutScanDetail)
    LinearLayout linearLayoutScanDetail;
    @BindView(R.id.linearLayoutEnterQuantity)
    LinearLayout linearLayoutEnterQuantity;
    @BindView(R.id.linearLayoutRejectReason)
    LinearLayout linearLayoutRejectReason;
    @BindView(R.id.linearLayoutButtons)
    LinearLayout linearLayoutButtons;
    @BindView(R.id.editTextScanStillage)
    AppCompatEditText editTextScanStillage;
    @BindView(R.id.editTextQuantity)
    AppCompatEditText editTextQuantity;
    @BindView(R.id.buttonConfirm)
    CustomButton buttonConfirm;

    @BindView(R.id.stillageDetail)
    View stillageDetail;

    StillageLayout stillageLayout;
    StillageDatum stillageDatum;


    long delay = 1000;
    long scanStillageLastTexxt = 0;

    IUpdateQtyInterface iUpdateQtyInterface;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_quantity);

        ButterKnife.bind(this);
        setTitle(getString(R.string.update_quantity));
        initData();
    }

    void initData() {
        stillageLayout = new StillageLayout();
        ButterKnife.bind(stillageLayout, stillageDetail);

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
            showProgress(UpdateQuantityActivity.this);
            if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
             //   iUpdateQtyInterface.callScanStillageService(new MoveInput(editTextScanStillage.getText().toString().trim(), userId));

            }
        }
    };

    void setData(ScanStillageResponse body) {
        linearLayoutScanDetail.setVisibility(View.VISIBLE);
        linearLayoutScanDetail.setAnimation(fadeIn);
        linearLayoutRejectReason.setVisibility(View.VISIBLE);
        linearLayoutRejectReason.setAnimation(fadeIn);
        linearLayoutEnterQuantity.setVisibility(View.VISIBLE);
        linearLayoutEnterQuantity.setAnimation(fadeIn);
        linearLayoutButtons.setVisibility(View.VISIBLE);
        linearLayoutButtons.setAnimation(fadeIn);

        editTextQuantity.setText(stillageLayout.textViewQuantity.getText().toString());

        stillageLayout.textViewitem.setText(body.getItemId());
        stillageLayout.textViewNumber.setText(body.getStickerID());
        stillageLayout.textViewQuantity.setText(body.getStandardQty() + "");
        stillageLayout.textViewStdQuantity.setText(body.getItemStdQty() + "");
        stillageLayout.textViewitemDesc.setText(body.getDescription());
    }

    @OnTextChanged(value = R.id.editTextQuantity, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextQuantityChanged(Editable text) {
        int stillageQty, stillageStdQty, editQty;

        if (!text.toString().equals("")) {
            stillageStdQty = Integer.parseInt(stillageLayout.textViewStdQuantity.getText().toString());
            editQty = Integer.parseInt(text.toString());

            if (editQty > stillageStdQty) {
                editTextQuantity.setError(getString(R.string.quantity_must_not_greater_than_stillage_std_qty));
                editTextQuantity.requestFocus();
                buttonConfirm.setEnabled(false);
            } else if (editQty == 0) {
                editTextQuantity.setError(getString(R.string.quantity_must_not_be_zero));
                editTextQuantity.requestFocus();
                buttonConfirm.setEnabled(false);
            } else {
                buttonConfirm.setEnabled(true);
            }
        } else {
            buttonConfirm.setEnabled(false);
        }
    }

    @OnClick(R.id.buttonConfirm)
    public void onButtonConfirmClick() {
        UpdateQtyInput updateQtyInput = new UpdateQtyInput();
        iUpdateQtyInterface.callUpdateQtyStillageService(updateQtyInput);
    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        linearLayoutScanDetail.setVisibility(View.GONE);
        linearLayoutScanDetail.setAnimation(fadeOut);
        linearLayoutRejectReason.setVisibility(View.GONE);
        linearLayoutRejectReason.setAnimation(fadeOut);
        linearLayoutEnterQuantity.setVisibility(View.GONE);
        linearLayoutEnterQuantity.setAnimation(fadeOut);
        linearLayoutButtons.setVisibility(View.GONE);
        linearLayoutButtons.setAnimation(fadeOut);
        editTextScanStillage.setEnabled(true);
        editTextScanStillage.setText("");
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
            editTextScanStillage.setEnabled(false);
        } else {
            hideProgress();
            CustomToast.showToast(this, body.getMessage());
            editTextScanStillage.setText("");
        }
    }

    @Override
    public void onUpdateQtyFailure(String message) {
        hideProgress();
        CustomToast.showToast(this, message);
        onButtonCancelClick();
    }

    @Override
    public void onUpdateQtySuccess(UniversalResponse body) {
        hideProgress();
        CustomToast.showToast(this, body.getMessage());
        editTextScanStillage.setEnabled(true);
        editTextScanStillage.setText("");
        editTextScanStillage.requestFocus();

        linearLayoutScanDetail.setVisibility(View.GONE);
        linearLayoutScanDetail.setAnimation(fadeOut);
        linearLayoutEnterQuantity.setVisibility(View.GONE);
        linearLayoutEnterQuantity.setAnimation(fadeOut);
        linearLayoutButtons.setVisibility(View.GONE);
        linearLayoutButtons.setAnimation(fadeOut);

    }
}
