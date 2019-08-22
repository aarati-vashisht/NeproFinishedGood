package com.neprofinishedgood.workorderstartend;

import androidx.appcompat.widget.AppCompatEditText;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.utils.NetworkChangeReceiver;
import com.neprofinishedgood.workorderstartend.Presenter.IWorkOrderStartEndInterface;
import com.neprofinishedgood.workorderstartend.Presenter.IWorkOrderStartEndPresenter;
import com.neprofinishedgood.workorderstartend.Presenter.IWorkOrderStartEndView;
import com.neprofinishedgood.workorderstartend.model.WorkOrderScanInput;
import com.neprofinishedgood.workorderstartend.model.WorkOrderScanResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class WorkOrderStartEndActivity extends BaseActivity implements IWorkOrderStartEndView {

    @BindView(R.id.linearLayoutWorkOrderScanDetail)
    LinearLayout linearLayoutWorkOrderScanDetail;

    @BindView(R.id.linearLayoutButtons)
    LinearLayout linearLayoutButtons;

    @BindView(R.id.editTextScanWorkOrder)
    AppCompatEditText editTextScanWorkOrder;

    @BindView(R.id.textViewWorkOrderNumber)
    TextView textViewWorkOrderNumber;

    @BindView(R.id.textViewitem)
    TextView textViewitem;

    @BindView(R.id.textViewProductionLine)
    TextView textViewProductionLine;

    @BindView(R.id.textViewWarehouse)
    TextView textViewWarehouse;

    @BindView(R.id.textViewQuantity)
    TextView textViewQuantity;

    @BindView(R.id.textViewSite)
    TextView textViewSite;

    @BindView(R.id.textViewStatus)
    TextView textViewStatus;

    @BindView(R.id.textViewitemDesc)
    TextView textViewitemDesc;

    @BindView(R.id.buttonStart)
    CustomButton buttonStart;

    @BindView(R.id.buttonEnd)
    CustomButton buttonEnd;

    IWorkOrderStartEndInterface iWorkOrderStartEndInterface;

    long delay = 1000;
    long scanStillageLastTexxt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_order_start_end);
        setTitle(getString(R.string.workorder_start_end));
        ButterKnife.bind(this);

        iWorkOrderStartEndInterface = new IWorkOrderStartEndPresenter(this, this);
    }

    @OnTextChanged(value = R.id.editTextScanWorkOrder, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanWorkOrderChanged(Editable text) {
        if (!text.toString().trim().equals("")) {
            scanWorkOrderhandler.postDelayed(workOrderRunnable, delay);
        }

    }

    @OnTextChanged(value = R.id.editTextScanWorkOrder, callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void onEditTextScanWorkOrderTEXTCHANGED(Editable text) {
        scanWorkOrderhandler.removeCallbacks(workOrderRunnable);

    }

    //for call service on text change
    Handler scanWorkOrderhandler = new Handler();
    private Runnable workOrderRunnable = new Runnable() {
        public void run() {
            if (NetworkChangeReceiver.isInternetConnected(WorkOrderStartEndActivity.this)) {
                showProgress(WorkOrderStartEndActivity.this);
                if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
                    iWorkOrderStartEndInterface.callScanWorkOrderService(new WorkOrderScanInput(editTextScanWorkOrder.getText().toString().trim(), userId));
                }
            } else {
                CustomToast.showToast(WorkOrderStartEndActivity.this, getString(R.string.no_internet));
            }
        }
    };

    @Override
    public void onWorkOrderScanFailure(String message) {
        hideProgress();
        CustomToast.showToast(this, message);
    }

    @Override
    public void onWorkOrderScanSuccess(WorkOrderScanResponse body) {
        hideProgress();
        if (body.getStatus().equalsIgnoreCase(getString(R.string.success))) {
            setData(body);
            editTextScanWorkOrder.setEnabled(false);
        } else {
            CustomToast.showToast(getApplicationContext(), body.getMessage());
            editTextScanWorkOrder.setText("");
        }
    }

    void setData(WorkOrderScanResponse body) {
        linearLayoutButtons.setVisibility(View.VISIBLE);
        linearLayoutWorkOrderScanDetail.setVisibility(View.VISIBLE);
        textViewWorkOrderNumber.setText(body.getWorkOrderNo());
        textViewitem.setText(body.getItemId());
        textViewProductionLine.setText(body.getProductionLine());
        textViewWarehouse.setText(body.getWareHouse());
        textViewQuantity.setText(body.getQuantity());
        textViewSite.setText(body.getSite());
        textViewStatus.setText(body.getWOStatus());
        textViewitemDesc.setText(body.getItemDescription());

        if (body.getStatusId().equals("1")) {
            buttonEnd.setEnabled(false);
            buttonStart.setEnabled(true);
        } else if ((body.getStatusId().equals("5"))) {
            buttonEnd.setEnabled(true);
            buttonStart.setEnabled(false);
        } else {
            buttonStart.setEnabled(false);
            buttonEnd.setEnabled(false);
        }
    }

    @OnClick(R.id.buttonStart)
    public void onButtonStartClick() {
        showProgress(this);
        WorkOrderScanInput workOrderScanInput = new WorkOrderScanInput(editTextScanWorkOrder.getText().toString().trim(), userId);
        iWorkOrderStartEndInterface.callWorkOrderStartService(workOrderScanInput);
    }

    @OnClick(R.id.buttonEnd)
    public void onButtonEndClick() {
        showProgress(this);
        iWorkOrderStartEndInterface.callWorkOrderEndService(new WorkOrderScanInput(editTextScanWorkOrder.getText().toString().trim(), userId));
    }

    @Override
    public void onWorkOrderStartFailure(String message) {
        hideProgress();
        CustomToast.showToast(this, message);
    }

    @Override
    public void onWorkOrderStartSuccess(UniversalResponse body) {
        hideProgress();
        if (body.getStatus().equalsIgnoreCase(getString(R.string.success))) {
            CustomToast.showToast(getApplicationContext(), body.getMessage());
              disableViews();
        } else {
            CustomToast.showToast(getApplicationContext(), body.getMessage());
        }
    }

    @Override
    public void onWorkOrderEndFailure(String message) {
        hideProgress();
        CustomToast.showToast(this, message);
    }

    @Override
    public void onWorkOrderEndSuccess(UniversalResponse body) {
        hideProgress();
        if (body.getStatus().equalsIgnoreCase(getString(R.string.success))) {
            CustomToast.showToast(getApplicationContext(), body.getMessage());
            disableViews();
        } else {
            CustomToast.showToast(getApplicationContext(), body.getMessage());
        }
    }

    private void disableViews() {
        linearLayoutButtons.setVisibility(View.GONE);
        linearLayoutWorkOrderScanDetail.setVisibility(View.GONE);
        editTextScanWorkOrder.setEnabled(true);
        editTextScanWorkOrder.setText("");
    }

}
