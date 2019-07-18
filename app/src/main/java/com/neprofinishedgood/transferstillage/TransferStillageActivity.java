package com.neprofinishedgood.transferstillage;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.plannedandunplannedmove.adapter.SpinnerAdapter;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;
import com.neprofinishedgood.transferstillage.model.TransferInput;
import com.neprofinishedgood.transferstillage.presenter.ITransferInterface;
import com.neprofinishedgood.transferstillage.presenter.ITransferView;
import com.neprofinishedgood.transferstillage.presenter.TransferPresenter;
import com.neprofinishedgood.utils.StillageLayout;

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

    StillageLayout stillageLayout;


    long delay = 1000;
    long scanStillageLastTexxt = 0;

    private ITransferInterface iTransferInterface;
    private String warehouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_stillage);
        ButterKnife.bind(this);
        stillageLayout = new StillageLayout();
        ButterKnife.bind(stillageLayout, stillageDetail);
        setTitle(getString(R.string.transfer));
        iTransferInterface = new TransferPresenter(this);
        initData();

    }

    private void initData() {
        SpinnerAdapter reasonListAdapter = new SpinnerAdapter(TransferStillageActivity.this, R.layout.spinner_layout, warehouseList);
        spinnerWarehouse.setAdapter(reasonListAdapter);
    }

    @OnItemSelected(R.id.spinnerWarehouse)
    public void spinnerBinSelected(Spinner spinner, int position) {
        warehouse = warehouseList.get(position).getId();
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
            showProgress(TransferStillageActivity.this);
            if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
                iTransferInterface.callScanStillageService(new MoveInput(editTextScanStillage.getText().toString().trim(), userId));

            }
        }
    };


    public void imageButtonCloseClick(View view) {
        relativeLayoutScanDetail.startAnimation(fadeOut);
        relativeLayoutScanDetail.setVisibility(View.GONE);
        editTextScanStillage.setText("");
        editTextScanStillage.setEnabled(true);

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
            if (body.getIsTransfered() == 1) {
                CustomToast.showToast(this, getString(R.string.this_stillage_already_transfred));
                editTextScanStillage.setText("");
            } else {
                setData(body);
            }
        } else {
            hideProgress();
            CustomToast.showToast(this, body.getMessage());
            editTextScanStillage.setText("");
        }
    }

    @Override
    public void onUpdateTransferFailure(String message) {
        hideProgress();
        CustomToast.showToast(this, message);
        onButtonCancelClick();
    }

    @Override
    public void onUpdateTransferSuccess(UniversalResponse body) {
        hideProgress();
        CustomToast.showToast(this, body.getMessage());
        editTextScanStillage.setEnabled(true);
        editTextScanStillage.setText("");
        editTextScanStillage.requestFocus();
        stillageDetail.setVisibility(View.GONE);
        stillageDetail.setAnimation(fadeOut);
        relativeLayoutScanDetail.setVisibility(View.GONE);
    }

    void setData(ScanStillageResponse body) {
        stillageDetail.setVisibility(View.VISIBLE);
        stillageDetail.setAnimation(fadeIn);
        relativeLayoutScanDetail.setVisibility(View.VISIBLE);
        stillageLayout.textViewitem.setText(body.getItemId());
        stillageLayout.textViewNumber.setText(body.getStickerID());
        stillageLayout.textViewQuantity.setText(body.getStandardQty() + "");
        stillageLayout.textViewStdQuantity.setText(body.getItemStdQty() + "");
        stillageLayout.textViewitemDesc.setText(body.getDescription());
    }

    @OnClick(R.id.buttonTransfer)
    public void onButtonDropClick() {
        if (spinnerWarehouse.getSelectedItemPosition() > 0) {
            showProgress(this);
            TransferInput transferInput = new TransferInput(editTextScanStillage.getText().toString().trim(), warehouse, userId);
            iTransferInterface.callUpdateTransferStillage(transferInput);
        } else {
            TextView textView = (TextView) spinnerWarehouse.getSelectedView();
            textView.setError(getString(R.string.select_warehouse));
            textView.requestFocus();
        }

    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        editTextScanStillage.setText("");
        editTextScanStillage.setEnabled(true);
        relativeLayoutScanDetail.setVisibility(View.GONE);
        relativeLayoutScanDetail.setAnimation(fadeOut);

    }


}
