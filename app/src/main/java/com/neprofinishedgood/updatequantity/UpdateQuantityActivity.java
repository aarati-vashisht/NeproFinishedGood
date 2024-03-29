package com.neprofinishedgood.updatequantity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.gson.Gson;
import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.base.model.UniversalSpinner;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.dashboard.DashBoardAcivity;
import com.neprofinishedgood.move.adapter.SpinnerAdapter;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.raf.model.StillageDatum;
import com.neprofinishedgood.updatequantity.model.UpdateQtyInput;
import com.neprofinishedgood.updatequantity.presenter.IUpdateQtyInterface;
import com.neprofinishedgood.updatequantity.presenter.IUpdateQtyPresenter;
import com.neprofinishedgood.updatequantity.presenter.IUpdateQtyView;
import com.neprofinishedgood.utils.NetworkChangeReceiver;
import com.neprofinishedgood.utils.SharedPref;
import com.neprofinishedgood.utils.StillageLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
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
    @BindView(R.id.linearLayoutVariance)
    LinearLayout linearLayoutVariance;
    @BindView(R.id.textViewVariance)
    AppCompatTextView textViewVariance;
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

    @BindView(R.id.linearLayoutAutoPicking)
    LinearLayout linearLayoutAutoPicking;

    @BindView(R.id.linearLayoutAutoRoute)
    LinearLayout linearLayoutAutoRoute;

    @BindView(R.id.textViewNumberOffline)
    TextView textViewNumberOffline;

    @BindView(R.id.checkBoxAutoPicking)
    CheckBox checkBoxAutoPicking;

    @BindView(R.id.checkBoxAutoRoute)
    CheckBox checkBoxAutoRoute;

    String autoRoute = "0", autoPick = "0";

    long delay = 1000;
    long scanStillageLastTexxt = 0;

    IUpdateQtyInterface iUpdateQtyInterface;
    private String reason;
    public List<UniversalSpinner> reasonList = new ArrayList<>();

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
        String title = getIntent().getStringExtra("REJECT_TITLE");
        setTitle(title);
//        setTitle(getString(R.string.update_quantity));
        iUpdateQtyInterface = new IUpdateQtyPresenter(this, this);
        editTextScanStillage.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        callService();
        initData();
    }

    void initData() {
        stillageLayout = new StillageLayout();
        ButterKnife.bind(stillageLayout, stillageDetail);
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
        try {
            reasonList = body.getReasonList();
            reasonList.add(0, new UniversalSpinner("Select Reason", "000"));
            SpinnerAdapter reasonListAdapter = new SpinnerAdapter(UpdateQuantityActivity.this, R.layout.spinner_layout, reasonList);
            spinnerReason.setAdapter(reasonListAdapter);
            linearLayoutScanDetail.setVisibility(View.VISIBLE);
            linearLayoutScanDetail.setAnimation(fadeIn);
            linearLayoutReason.setVisibility(View.VISIBLE);
            linearLayoutReason.setAnimation(fadeIn);
            linearLayoutVariance.setVisibility(View.VISIBLE);
            linearLayoutVariance.setAnimation(fadeIn);
            linearLayoutEnterQuantity.setVisibility(View.VISIBLE);
            linearLayoutEnterQuantity.setAnimation(fadeIn);
            linearLayoutButtons.setVisibility(View.VISIBLE);
            linearLayoutButtons.setAnimation(fadeIn);
            if (body.getwOStatusId()!= 7) {
                linearLayoutAutoPicking.setVisibility(View.VISIBLE);
                linearLayoutAutoPicking.setAnimation(fadeIn);
                linearLayoutAutoRoute.setVisibility(View.VISIBLE);
                linearLayoutAutoRoute.setAnimation(fadeIn);
            }
            stillageLayout.linearLayoutStatus.setVisibility(View.VISIBLE);
            stillageLayout.textViewWorkOrderStatus.setText(body.getWoStatus());
            if (body.getIsCounted().equals("1")) {
                stillageLayout.checkboxRafStatus.setChecked(true);
            } else {
                stillageLayout.checkboxRafStatus.setChecked(false);
            }

            stillageLayout.textViewitem.setText(body.getItemId());
            stillageLayout.textViewNumber.setText(body.getStickerID());
            stillageLayout.textViewQuantity.setText(body.getStandardQty() + "");
            stillageLayout.textViewStdQuantity.setText(body.getItemStdQty() + "");
            stillageLayout.textViewitemDesc.setText(body.getDescription());

            editTextQuantity.setText(stillageLayout.textViewQuantity.getText().toString());
        } catch (Exception e) {
            showSuccessDialog(e.getMessage());
            cancelClick();
        }
    }

    @OnTextChanged(value = R.id.editTextQuantity, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextQuantityChanged(Editable text) {
        float stillageQty = 0, stillageStdQty, editQty;

        if (!text.toString().equals("") && !text.toString().equals(".")) {
            editQty = Float.parseFloat(text.toString());
            if (stillageLayout.textViewQuantity.getText().toString().length() > 0) {
                stillageQty = Float.parseFloat(stillageLayout.textViewQuantity.getText().toString());
            }
            textViewVariance.setText(editQty - stillageQty + "");
            buttonConfirm.setEnabled(true);
//            if (stillageLayout.textViewStdQuantity.getText().toString().length() > 0) {
//                stillageStdQty = Float.parseFloat(stillageLayout.textViewStdQuantity.getText().toString());
//                if (editQty > stillageStdQty) {
//                    editTextQuantity.setError(getString(R.string.quantity_must_not_greater_than_stillage_std_qty));
//                    editTextQuantity.requestFocus();
//                    buttonConfirm.setEnabled(false);
//                    editTextQuantity.setText("");
//                }
//            }
//            if (editQty == 0) {
//                editTextQuantity.setError(getString(R.string.quantity_must_not_be_zero));
//                editTextQuantity.requestFocus();
//                buttonConfirm.setEnabled(false);
//            } else {
//                buttonConfirm.setEnabled(true);
//            }
        } else {
            buttonConfirm.setEnabled(false);
            textViewVariance.setText("");
        }
    }

    @OnItemSelected(R.id.spinnerReason)
    public void spinnerBinSelected(Spinner spinner, int position) {
        reason = reasonList.get(position).getId();
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
        String variance = textViewVariance.getText().toString();
//        if (NetworkChangeReceiver.isInternetConnected(UpdateQuantityActivity.this)) {
        if (isValidated()) {
            if (NetworkChangeReceiver.isInternetConnected(UpdateQuantityActivity.this)) {
                showProgress(this);
                UpdateQtyInput updateQtyInput = new UpdateQtyInput(editTextScanStillage.getText().toString(), editTextQuantity.getText().toString(), reason, userId, variance, autoPick, autoRoute);
                iUpdateQtyInterface.callUpdateQtyStillageService(updateQtyInput);
            } else {
                showSuccessDialog(getString(R.string.no_internet));
            }
        }
//        } else {
//            if (isOfflineValidated()) {
//                UpdateQtyInput updateQtyInput = new UpdateQtyInput(editTextScanStillage.getText().toString(), editTextQuantity.getText().toString(), reason, userId, variance);
//                saveDataOffline(updateQtyInput);
//            }
//        }
    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        showCancelAlert(7);
    }

    public void cancelClick() {
        isScanned = false;
        linearLayoutScanDetail.setVisibility(View.GONE);
        linearLayoutScanDetail.setAnimation(fadeOut);
        linearLayoutReason.setVisibility(View.GONE);
        linearLayoutReason.setAnimation(fadeOut);
        linearLayoutVariance.setVisibility(View.GONE);
        linearLayoutVariance.setAnimation(fadeOut);
        linearLayoutEnterQuantity.setVisibility(View.GONE);
        linearLayoutEnterQuantity.setAnimation(fadeOut);
        linearLayoutButtons.setVisibility(View.GONE);
        linearLayoutButtons.setAnimation(fadeOut);

        linearLayoutAutoPicking.setVisibility(View.GONE);
        linearLayoutAutoPicking.setAnimation(fadeOut);
        linearLayoutAutoRoute.setVisibility(View.GONE);
        linearLayoutAutoRoute.setAnimation(fadeOut);

        editTextScanStillage.setEnabled(true);
        editTextScanStillage.setText("");
        editTextScanStillage.requestFocus();
        spinnerReason.setSelection(0);
    }

    @Override
    public void onFailure(String message) {
        hideProgress();
        showSuccessDialog(message);
    }

    @Override
    public void onSuccess(ScanStillageResponse body) {
        hideProgress();
        if (body.getStatus().equalsIgnoreCase(getString(R.string.success))) {
            if (isLocationMatched(body.getWareHouseID())) {
                if (body.getStandardQty() > 0) {
                    isScanned = true;
                    editTextScanStillage.setEnabled(false);
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
            editTextScanStillage.setText("");
        }
    }

    boolean isValidated() {
        if (/*editTextQuantity.getText().toString().equals("0") ||*/ editTextQuantity.getText().toString().equals(".")) {
            editTextQuantity.setError(getResources().getString(R.string.enter_update_quantity));
            editTextQuantity.requestFocus();
            return false;
        }
        if (spinnerReason.getSelectedItemPosition() == 0) {
            showSuccessDialog(getString(R.string.select_reason));
            return false;
        }
        return true;
    }

    @Override
    public void onUpdateQtyFailure(String message) {
        hideProgress();
        if (linearLayoutScanDetail.getVisibility() == View.VISIBLE) {
            showSuccessDialog(message);
            cancelClick();
        }
    }

    @Override
    public void onUpdateQtySuccess(UniversalResponse body) {
        hideProgress();
        if (linearLayoutScanDetail.getVisibility() == View.VISIBLE) {
            if (body.getStatus().equals(getResources().getString(R.string.success))) {
                showSuccessDialog(body.getMessage());
                isScanned = false;
                cancelClick();
            } else {
                showSuccessDialog(body.getMessage());
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
//        linearLayoutVariance.setVisibility(View.VISIBLE);
//        linearLayoutVariance.setAnimation(fadeIn);
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
