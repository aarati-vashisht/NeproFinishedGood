package com.neprofinishedgood.transferstillage;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.dashboard.DashBoardAcivity;
import com.neprofinishedgood.move.adapter.SpinnerAdapter;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.transferstillage.model.ShipInput;
import com.neprofinishedgood.transferstillage.model.TransferInput;
import com.neprofinishedgood.transferstillage.presenter.ITransferInterface;
import com.neprofinishedgood.transferstillage.presenter.ITransferView;
import com.neprofinishedgood.transferstillage.presenter.TransferPresenter;
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

public class TransferStillageActivity extends BaseActivity implements ITransferView {

    @BindView(R.id.relativeLayoutScanDetail)
    RelativeLayout relativeLayoutScanDetail;


    @BindView(R.id.buttonTransfer)
    CustomButton buttonTransfer;

    @BindView(R.id.editTextScanStillage)
    AppCompatEditText editTextScanStillage;

    @BindView(R.id.stillageDetail)
    View stillageDetail;

    @BindView(R.id.spinnerWarehouse)
    Spinner spinnerWarehouse;

    @BindView(R.id.linearLayoutOfflineData)
    LinearLayout linearLayoutOfflineData;

    @BindView(R.id.linearLayoutToWarehouse)
    LinearLayout linearLayoutToWarehouse;

    @BindView(R.id.textViewToWarehouse)
    TextView textViewToWarehouse;

    @BindView(R.id.textViewNumberOffline)
    TextView textViewNumberOffline;

    StillageLayout stillageLayout;


    long delay = 1000;
    long scanStillageLastTexxt = 0;

    private ITransferInterface iTransferInterface;
    private String warehouse;
    private String stillageWarehouse;

    static TransferStillageActivity instance;
    private String transferId;

    public static TransferStillageActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_stillage);
        ButterKnife.bind(this);
        instance = this;
        stillageLayout = new StillageLayout();
        ButterKnife.bind(stillageLayout, stillageDetail);
        setTitle(getString(R.string.transfer));
        iTransferInterface = new TransferPresenter(this, this);
        initData();
        editTextScanStillage.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        callService();

    }

    private void initData() {
        SpinnerAdapter reasonListAdapter = new SpinnerAdapter(TransferStillageActivity.this, R.layout.spinner_layout, warehouseList);
        spinnerWarehouse.setAdapter(reasonListAdapter);
    }

    @OnItemSelected(R.id.spinnerWarehouse)
    public void spinnerBinSelected(Spinner spinner, int position) {
        warehouse = warehouseList.get(position).getId().trim();
        if (warehouse.equalsIgnoreCase(stillageWarehouse)) {
            buttonTransfer.setEnabled(false);
        } else {
            buttonTransfer.setEnabled(true);
        }
    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanStillageChanged(Editable text) {
        if (!text.toString().trim().equals("")) {
//            scanStillagehandler.postDelayed(stillageRunnable, delay);
            if (text.toString().trim().length() == scanStillageLength) {
                if (NetworkChangeReceiver.isInternetConnected(TransferStillageActivity.this)) {
                    showProgress(TransferStillageActivity.this);
                    iTransferInterface.callScanStillageService(new MoveInput(editTextScanStillage.getText().toString().trim(), userId));
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
//            if (NetworkChangeReceiver.isInternetConnected(TransferStillageActivity.this)) {
//                showProgress(TransferStillageActivity.this);
//                if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
//                    iTransferInterface.callScanStillageService(new AisleInput(editTextScanStillage.getText().toString().trim(), userId));
//
//                }
//            } else {
//                setDataOffline();
//            }
//        }
//    };


    public void imageButtonCloseClick(View view) {
        relativeLayoutScanDetail.startAnimation(fadeOut);
        relativeLayoutScanDetail.setVisibility(View.GONE);
        editTextScanStillage.setText("");
        editTextScanStillage.setEnabled(true);

    }

    @Override
    public void onFailure(String message) {
        hideProgress();
        showSuccessDialog(message);
//        CustomToast.showToast(this, message);
    }

    @Override
    public void onSuccess(ScanStillageResponse body) {
        if (body.getStatus().equalsIgnoreCase(getString(R.string.success))) {
            hideProgress();
            if (!body.getTransferId().equals("") && body.getIsShiped().equals("1")) {
                showSuccessDialog(getString(R.string.this_stillage_already_transfred));
//                CustomToast.showToast(this, getString(R.string.this_stillage_already_transfred));
                editTextScanStillage.setText("");
            } else {
                if (body.getStandardQty() > 0) {
                    isScanned = true;
                    setData(body);
                } else {
                    showSuccessDialog(getResources().getString(R.string.stillage_discarded));
                    editTextScanStillage.setText("");
                }
            }
        } else {
            hideProgress();
            showSuccessDialog(body.getMessage());
//            CustomToast.showToast(this, body.getMessage());
            editTextScanStillage.setText("");
        }
    }

    @Override
    public void onUpdateTransferFailure(String message) {
        hideProgress();
        if (relativeLayoutScanDetail.getVisibility() == View.VISIBLE) {
            showSuccessDialog(message);
//            CustomToast.showToast(this, message);
//            cancelClick();
        }
    }

    @Override
    public void onUpdateTransferSuccess(UniversalResponse body) {
        hideProgress();
        if (relativeLayoutScanDetail.getVisibility() == View.VISIBLE) {
            showSuccessDialog(body.getMessage());
            if (body.getTransferId().equals("")) {
                cancelClick();
            } else {
                linearLayoutToWarehouse.setVisibility(View.GONE);
                textViewToWarehouse.setVisibility(View.GONE);
                buttonTransfer.setText(getString(R.string.ship));
                transferId = body.getTransferId();
            }
        }
    }

    @Override
    public void onShipFailure(String message) {
        hideProgress();
        showSuccessDialog(message);
//        cancelClick();
    }

    @Override
    public void onShipSuccess(UniversalResponse body) {
        hideProgress();
        showSuccessDialog(body.getMessage());
        if (body.getStatus().equals(getString(R.string.success))) {
            cancelClick();
        }
    }

    void clearViews() {
        isScanned = false;
        editTextScanStillage.setEnabled(true);
        editTextScanStillage.setText("");
        editTextScanStillage.requestFocus();
        stillageDetail.setVisibility(View.GONE);
        stillageDetail.setAnimation(fadeOut);
        relativeLayoutScanDetail.setVisibility(View.GONE);
    }

    void setData(ScanStillageResponse body) {
        editTextScanStillage.setEnabled(false);
        stillageWarehouse = body.getWareHouseID().trim();
        stillageDetail.setVisibility(View.VISIBLE);
        stillageDetail.setAnimation(fadeIn);
        relativeLayoutScanDetail.setVisibility(View.VISIBLE);
        stillageLayout.linearLayoutWarehouse.setVisibility(View.VISIBLE);
        stillageLayout.textViewWarehouse.setText(body.getWareHouseID() + " | " + body.getWareHouseName());
        stillageLayout.textViewitem.setText(body.getItemId());
        stillageLayout.textViewNumber.setText(body.getStickerID());
        stillageLayout.textViewQuantity.setText(body.getStandardQty() + "");
        stillageLayout.textViewStdQuantity.setText(body.getItemStdQty() + "");
        stillageLayout.textViewitemDesc.setText(body.getDescription());

        if (body.getIsShiped().equals("0") && !body.getTransferId().equals("")) {
            linearLayoutToWarehouse.setVisibility(View.GONE);
            textViewToWarehouse.setVisibility(View.GONE);
            buttonTransfer.setText(getString(R.string.ship));
            transferId = body.getTransferId();
        } else if (body.getIsShiped().equals("0") && body.getTransferId().equals("")) {
            linearLayoutToWarehouse.setVisibility(View.VISIBLE);
            textViewToWarehouse.setVisibility(View.VISIBLE);
            buttonTransfer.setText(getString(R.string.transfer));
        }

    }

    @OnClick(R.id.buttonTransfer)
    public void onButtonDropClick() {

        if (linearLayoutOfflineData.getVisibility() == View.GONE) {
            if (buttonTransfer.getText().toString().equals(getString(R.string.transfer))) {
                if (spinnerWarehouse.getSelectedItemPosition() > 0) {
                    showProgress(this);
                    TransferInput transferInput = new TransferInput(editTextScanStillage.getText().toString().trim(), warehouse, userId);
                    iTransferInterface.callUpdateTransferStillage(transferInput);
                } else {
                    TextView textView = (TextView) spinnerWarehouse.getSelectedView();
                    textView.setError(getString(R.string.select_warehouse));
                    textView.requestFocus();
                }
            } else if (buttonTransfer.getText().toString().equals(getString(R.string.ship))) {
                showProgress(this);
                ShipInput shipInput = new ShipInput(editTextScanStillage.getText().toString().trim(), userId, transferId);
                iTransferInterface.callShipStillage(shipInput);
            }
        } else {
            if (isOfflineValidated()) {
                TransferInput transferInput = new TransferInput(editTextScanStillage.getText().toString().trim(), warehouse, userId);
                saveDataOffline(transferInput);
            }
        }

    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        showCancelAlert(5);
    }

    public void cancelClick() {
        isScanned = false;
        editTextScanStillage.setText("");
        editTextScanStillage.setEnabled(true);
        editTextScanStillage.requestFocus();
        relativeLayoutScanDetail.setVisibility(View.GONE);
        relativeLayoutScanDetail.setAnimation(fadeOut);
        linearLayoutOfflineData.setVisibility(View.GONE);
        linearLayoutOfflineData.setAnimation(fadeOut);
    }

    public void imageButtonHomeClick(View view) {
        if (isScanned) {
            showBackAlert(new Intent(TransferStillageActivity.this, DashBoardAcivity.class), true);
        } else {
            finishAffinity();
            startActivity(new Intent(TransferStillageActivity.this, DashBoardAcivity.class));
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

    }

    void setVisibilityInOfflineMode() {
        editTextScanStillage.setEnabled(false);
        linearLayoutOfflineData.setVisibility(View.VISIBLE);
        stillageDetail.setVisibility(View.GONE);
        relativeLayoutScanDetail.setVisibility(View.VISIBLE);
    }

    void disableVisibility() {
        relativeLayoutScanDetail.setVisibility(View.GONE);
        editTextScanStillage.setEnabled(true);
        editTextScanStillage.setText("");
        linearLayoutOfflineData.setVisibility(View.GONE);
        stillageDetail.setVisibility(View.VISIBLE);
    }

    void saveDataOffline(TransferInput data) {
        ArrayList<TransferInput> transferList = new ArrayList<>();
        Gson gson = new Gson();
        String transferData = SharedPref.getTransferData();
        if (!transferData.equals("")) {
            Type type = new TypeToken<ArrayList<TransferInput>>() {
            }.getType();
            transferList = gson.fromJson(transferData, type);
        }
        transferList.add(data);
        String json = gson.toJson(transferList);
        SharedPref.saveTransferData(json);
        showSuccessDialog(getResources().getString(R.string.data_saved_offline));
//        CustomToast.showToast(this, getResources().getString(R.string.data_saved_offline));
        disableVisibility();
    }

    boolean isOfflineValidated() {
        if (spinnerWarehouse.getSelectedItemPosition() == 0) {
            TextView textView = (TextView) spinnerWarehouse.getSelectedView();
            textView.setError(getString(R.string.select_warehouse));
            textView.requestFocus();
            return false;
        }
        return true;
    }

    public void callService() {
        if (NetworkChangeReceiver.isInternetConnected(TransferStillageActivity.this)) {
            ArrayList<TransferInput> transferList = new ArrayList<>();
            Gson gson = new Gson();
            String transferData = SharedPref.getTransferData();
            if (!transferData.equals("")) {
                Type type = new TypeToken<ArrayList<TransferInput>>() {
                }.getType();
                transferList = gson.fromJson(transferData, type);

                for (TransferInput transferInput : transferList) {
                    iTransferInterface.callUpdateTransferStillage(transferInput);
                }
                String json = "";
                SharedPref.saveTransferData(json);
            }
        }
    }
}
