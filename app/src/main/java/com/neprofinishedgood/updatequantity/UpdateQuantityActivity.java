package com.neprofinishedgood.updatequantity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

import com.google.gson.Gson;
import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.dashboard.DashBoardAcivity;
import com.neprofinishedgood.move.adapter.SpinnerAdapter;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.qualitycheck.rejectquantity.RejectQuantityActivity;
import com.neprofinishedgood.raf.model.StillageDatum;
import com.neprofinishedgood.updatequantity.model.UpdateQtyInput;
import com.neprofinishedgood.updatequantity.presenter.IUpdateQtyInterface;
import com.neprofinishedgood.updatequantity.presenter.IUpdateQtyPresenter;
import com.neprofinishedgood.updatequantity.presenter.IUpdateQtyView;
import com.neprofinishedgood.utils.NetworkChangeReceiver;
import com.neprofinishedgood.utils.SharedPref;
import com.neprofinishedgood.utils.StillageLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;

public class UpdateQuantityActivity extends BaseActivity implements IUpdateQtyView {

    @BindView(R.id.linearLayoutScanDetail)
    LinearLayout linearLayoutScanDetail;
    @BindView(R.id.linearLayoutEnterQuantity)
    LinearLayout linearLayoutEnterQuantity;
    @BindView(R.id.linearLayoutReason)
    LinearLayout linearLayoutReason;
    @BindView(R.id.linearLayoutButtons)
    LinearLayout linearLayoutButtons;
    @BindView(R.id.editTextScanStillage)
    AppCompatEditText editTextScanStillage;
    @BindView(R.id.editTextQuantity)
    AppCompatEditText editTextQuantity;
    @BindView(R.id.buttonConfirm)
    CustomButton buttonConfirm;

    @BindView(R.id.spinnerReason)
    Spinner spinnerReason;

    @BindView(R.id.stillageDetail)
    View stillageDetail;

    StillageLayout stillageLayout;
    StillageDatum stillageDatum;

    @BindView(R.id.linearLayoutOfflineData)
    LinearLayout linearLayoutOfflineData;

    @BindView(R.id.textViewNumberOffline)
    TextView textViewNumberOffline;

    long delay = 1000;
    long scanStillageLastTexxt = 0;

    IUpdateQtyInterface iUpdateQtyInterface;
    private String reason;

    public static int i = 0;

    static UpdateQuantityActivity instance;

    public static UpdateQuantityActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_quantity);

        ButterKnife.bind(this);
        instance = this;
        setTitle(getString(R.string.update_quantity));
        iUpdateQtyInterface = new IUpdateQtyPresenter(this, this);
        editTextScanStillage.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        callService();
        initData();
    }

    void initData() {
        stillageLayout = new StillageLayout();
        ButterKnife.bind(stillageLayout, stillageDetail);
        SpinnerAdapter reasonListAdapter = new SpinnerAdapter(UpdateQuantityActivity.this, R.layout.spinner_layout, reasonList);
        spinnerReason.setAdapter(reasonListAdapter);
    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanStillageChanged(Editable text) {

        if (!text.toString().trim().equals("")) {
//            scanStillagehandler.postDelayed(stillageRunnable, delay);
            if (text.toString().trim().length() == scanStillageLength) {
                if (NetworkChangeReceiver.isInternetConnected(UpdateQuantityActivity.this)) {
                    showProgress(UpdateQuantityActivity.this);
                    iUpdateQtyInterface.callScanStillageService(new MoveInput(editTextScanStillage.getText().toString().trim(), userId));
                } else {
                    setDataOffline();
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
//            if (NetworkChangeReceiver.isInternetConnected(UpdateQuantityActivity.this)) {
//                showProgress(UpdateQuantityActivity.this);
//                if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
//                    iUpdateQtyInterface.callScanStillageService(new AisleInput(editTextScanStillage.getText().toString().trim(), userId));
//                }
//            } else {
//                setDataOffline();
//            }
//        }
//    };

    void setData(ScanStillageResponse body) {
        linearLayoutScanDetail.setVisibility(View.VISIBLE);
        linearLayoutScanDetail.setAnimation(fadeIn);
        linearLayoutReason.setVisibility(View.VISIBLE);
        linearLayoutReason.setAnimation(fadeIn);
        linearLayoutEnterQuantity.setVisibility(View.VISIBLE);
        linearLayoutEnterQuantity.setAnimation(fadeIn);
        linearLayoutButtons.setVisibility(View.VISIBLE);
        linearLayoutButtons.setAnimation(fadeIn);

        stillageLayout.textViewitem.setText(body.getItemId());
        stillageLayout.textViewNumber.setText(body.getStickerID());
        stillageLayout.textViewQuantity.setText(body.getStandardQty() + "");
        stillageLayout.textViewStdQuantity.setText(body.getItemStdQty() + "");
        stillageLayout.textViewitemDesc.setText(body.getDescription());

        editTextQuantity.setText(stillageLayout.textViewQuantity.getText().toString());
    }

    @OnTextChanged(value = R.id.editTextQuantity, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextQuantityChanged(Editable text) {
        float stillageQty, stillageStdQty, editQty;

        if (!text.toString().equals("") && !text.toString().equals(".")) {
            editQty = Float.parseFloat(text.toString());
            if (stillageLayout.textViewStdQuantity.getText().toString().length() > 0) {
                stillageStdQty = Float.parseFloat(stillageLayout.textViewStdQuantity.getText().toString());
                if (editQty > stillageStdQty) {
                    editTextQuantity.setError(getString(R.string.quantity_must_not_greater_than_stillage_std_qty));
                    editTextQuantity.requestFocus();
                    buttonConfirm.setEnabled(false);
                    editTextQuantity.setText("");
                }
            }
            if (editQty == 0) {
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

    @OnItemSelected(R.id.spinnerReason)
    public void spinnerBinSelected(Spinner spinner, int position) {
        reason = reasonList.get(position).getId();
    }

    @OnClick(R.id.buttonConfirm)
    public void onButtonConfirmClick() {
        if (NetworkChangeReceiver.isInternetConnected(UpdateQuantityActivity.this)) {
            if (isValidated()) {
                showProgress(this);
                UpdateQtyInput updateQtyInput = new UpdateQtyInput(editTextScanStillage.getText().toString(), editTextQuantity.getText().toString(), reason, userId);
                iUpdateQtyInterface.callUpdateQtyStillageService(updateQtyInput);
            }
        } else {
            if (isOfflineValidated()) {
                UpdateQtyInput updateQtyInput = new UpdateQtyInput(editTextScanStillage.getText().toString(), editTextQuantity.getText().toString(), reason, userId);
                saveDataOffline(updateQtyInput);
            }
        }
    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        showCancelAlert(7);
    }

    public void cancelClick(){
        isScanned = false;
        linearLayoutScanDetail.setVisibility(View.GONE);
        linearLayoutScanDetail.setAnimation(fadeOut);
        linearLayoutReason.setVisibility(View.GONE);
        linearLayoutReason.setAnimation(fadeOut);
        linearLayoutEnterQuantity.setVisibility(View.GONE);
        linearLayoutEnterQuantity.setAnimation(fadeOut);
        linearLayoutButtons.setVisibility(View.GONE);
        linearLayoutButtons.setAnimation(fadeOut);
        editTextScanStillage.setEnabled(true);
        editTextScanStillage.setText("");
        editTextScanStillage.requestFocus();
        spinnerReason.setSelection(0);
    }

    @Override
    public void onFailure(String message) {
        hideProgress();
        showSuccessDialog(message);
//        CustomToast.showToast(this, message);
    }

    @Override
    public void onSuccess(ScanStillageResponse body) {
        hideProgress();
        if (body.getStatus().equalsIgnoreCase(getString(R.string.success))) {

            if (body.getStandardQty() > 0) {
                isScanned = true;
                setData(body);
                editTextScanStillage.setEnabled(false);
            }
            else{
                showSuccessDialog(getResources().getString(R.string.stillage_discarded));
                editTextScanStillage.setText("");
            }

        } else {
            showSuccessDialog(body.getMessage());
//            CustomToast.showToast(this, body.getMessage());
            editTextScanStillage.setText("");
        }
    }

    boolean isValidated() {
        if (editTextQuantity.getText().toString().equals("0")) {
            editTextQuantity.setError(getResources().getString(R.string.enter_update_quantity));
            editTextQuantity.requestFocus();
            return false;
        }
        if (spinnerReason.getSelectedItemPosition() == 0) {
            TextView textView = (TextView) spinnerReason.getSelectedView();
            textView.setError(getString(R.string.select_reason));
            textView.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onUpdateQtyFailure(String message) {
        hideProgress();
        if (linearLayoutScanDetail.getVisibility() == View.VISIBLE) {
            showSuccessDialog(message);
            //            CustomToast.showToast(this, message);
            cancelClick();
        }
    }

    @Override
    public void onUpdateQtySuccess(UniversalResponse body) {
        hideProgress();
        Log.d("afghsd  " + i, new Gson().toJson(body));

        if (linearLayoutScanDetail.getVisibility() == View.VISIBLE) {
            if (body.getStatus().equals(getResources().getString(R.string.success))) {
                showSuccessDialog(body.getMessage());
                isScanned = false;
//                CustomToast.showToast(this, body.getMessage());
                cancelClick();
            } else {
                showSuccessDialog(body.getMessage());
//                CustomToast.showToast(getApplicationContext(), body.getMessage());
            }
        }
        spinnerReason.setSelection(0);
    }

    public void imageButtonHomeClick(View view) {
        if (isScanned) {
            showBackAlert(new Intent(UpdateQuantityActivity.this, DashBoardAcivity.class), true);
        } else {
            finishAffinity();
            startActivity(new Intent(UpdateQuantityActivity.this, DashBoardAcivity.class));
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


    void setDataOffline() {
        textViewNumberOffline.setText(editTextScanStillage.getText().toString());
        setVisibilityInOfflineMode();
        initData();
        editTextQuantity.setText("");
        stillageLayout.textViewStdQuantity.setText("");
    }

    void setVisibilityInOfflineMode() {
        editTextScanStillage.setEnabled(false);
        linearLayoutOfflineData.setVisibility(View.VISIBLE);
        stillageDetail.setVisibility(View.GONE);
        linearLayoutScanDetail.setVisibility(View.VISIBLE);

        linearLayoutReason.setVisibility(View.VISIBLE);
        linearLayoutReason.setAnimation(fadeIn);
        linearLayoutEnterQuantity.setVisibility(View.VISIBLE);
        linearLayoutEnterQuantity.setAnimation(fadeIn);
        linearLayoutButtons.setVisibility(View.VISIBLE);
        linearLayoutButtons.setAnimation(fadeIn);
    }

    void disableVisibility() {
        linearLayoutOfflineData.setVisibility(View.GONE);
        stillageDetail.setVisibility(View.VISIBLE);
        editTextQuantity.setText("");
    }

    void saveDataOffline(UpdateQtyInput data) {
        ArrayList<UpdateQtyInput> updateList = new ArrayList<>();
        Gson gson = new Gson();

        updateList = SharedPref.getUpdateData();
        updateList.add(data);
        String json = gson.toJson(updateList);
        SharedPref.saveUpdateData(json);
        showSuccessDialog(getResources().getString(R.string.data_saved_offline));
//        CustomToast.showToast(this, getResources().getString(R.string.data_saved_offline));
        cancelClick();
        disableVisibility();
    }

    boolean isOfflineValidated() {
        if (spinnerReason.getSelectedItemPosition() == 0) {
            TextView textView = (TextView) spinnerReason.getSelectedView();
            textView.setError(getString(R.string.select_reason));
            textView.requestFocus();
            return false;
        }
        if (editTextQuantity.getText().toString().equals("")) {
            editTextQuantity.setError(getString(R.string.enter_reject_quantity));
            editTextQuantity.requestFocus();
            return false;
        }
        return true;
    }

    public void callService() {
        if (NetworkChangeReceiver.isInternetConnected(UpdateQuantityActivity.this)) {
            ArrayList<UpdateQtyInput> updateList;
            updateList = SharedPref.getUpdateData();
            if (updateList.size() > 0) {
                showProgress(this);
                for (UpdateQtyInput updateQtyInput : updateList) {
                    iUpdateQtyInterface.callUpdateQtyStillageService(updateQtyInput);
                }
                String json = "";
                SharedPref.saveUpdateData(json);
            }
        }
    }
}
