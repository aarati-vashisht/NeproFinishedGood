package com.neprofinishedgood.workorderstartend;

import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.dashboard.DashBoardAcivity;
import com.neprofinishedgood.qualitycheck.rejectquantity.RejectQuantityActivity;
import com.neprofinishedgood.utils.NetworkChangeReceiver;
import com.neprofinishedgood.utils.Utils;
import com.neprofinishedgood.workorderstartend.Presenter.IWorkOrderStartEndInterface;
import com.neprofinishedgood.workorderstartend.Presenter.IWorkOrderStartEndPresenter;
import com.neprofinishedgood.workorderstartend.Presenter.IWorkOrderStartEndView;
import com.neprofinishedgood.workorderstartend.model.WorkOrderScanInput;
import com.neprofinishedgood.workorderstartend.model.WorkOrderScanResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class WorkOrderStartEndActivity extends BaseActivity implements IWorkOrderStartEndView {

    @BindView(R.id.linearLayoutWorkOrderScanDetail)
    LinearLayout linearLayoutWorkOrderScanDetail;

    @BindView(R.id.linearLayoutButtons)
    LinearLayout linearLayoutButtons;

    @BindView(R.id.linearLayoutEndQuantities)
    LinearLayout linearLayoutEndQuantities;

    @BindView(R.id.linearLayoutRoutePick)
    LinearLayout linearLayoutRoutePick;

    @BindView(R.id.linearLayoutStartQty)
    LinearLayout linearLayoutStartQty;

    @BindView(R.id.linearLayoutDetailStartedQty)
    LinearLayout linearLayoutDetailStartedQty;

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

    @BindView(R.id.textViewBalanceQty)
    TextView textViewBalanceQty;

    @BindView(R.id.textViewRafQty)
    TextView textViewRafQty;

    @BindView(R.id.textViewQtyStarted)
    TextView textViewQtyStarted;

    @BindView(R.id.buttonStart)
    CustomButton buttonStart;

    @BindView(R.id.buttonEnd)
    CustomButton buttonEnd;

    @BindView(R.id.radioGroupStartQty)
    RadioGroup radioGroupStartQty;

    @BindView(R.id.radioButtonFullQty)
    RadioButton radioButtonFullQty;

    @BindView(R.id.radioButtonZeroQty)
    RadioButton radioButtonZeroQty;

    @BindView(R.id.radioButtonPartialQty)
    RadioButton radioButtonPartialQty;

    @BindView(R.id.editTextPartialQty)
    AppCompatEditText editTextPartialQty;

    @BindView(R.id.checkBoxAutoRoute)
    CheckBox checkBoxAutoRoute;

    @BindView(R.id.checkBoxAutoPicking)
    CheckBox checkBoxAutoPicking;

    private String autoRoute = "0";
    private String autoPick = "0";

    private String startQty = "0";

    String getStartQty = "0";

    private int selectedRadio = 0;

    float maxStartQty;
    float woQty;

    IWorkOrderStartEndInterface iWorkOrderStartEndInterface;

    long delay = 1000;
    long scanStillageLastTexxt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_order_start_end);
        setTitle(getString(R.string.workorder_start_end));
        ButterKnife.bind(this);
        editTextScanWorkOrder.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        iWorkOrderStartEndInterface = new IWorkOrderStartEndPresenter(this, this);

        radioGroupStartQty.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == radioButtonFullQty.getId()) {
                selectedRadio = 0;
                editTextPartialQty.setVisibility(View.GONE);
            } else if (checkedId == radioButtonZeroQty.getId()) {
                selectedRadio = 1;
                editTextPartialQty.setVisibility(View.GONE);
            } else if (checkedId == radioButtonPartialQty.getId()) {
                selectedRadio = 2;
                editTextPartialQty.setVisibility(View.VISIBLE);
            }
        });
    }

    @OnTextChanged(value = R.id.editTextScanWorkOrder, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanWorkOrderChanged(Editable text) {
        if (!text.toString().trim().equals("")) {
//            scanWorkOrderhandler.postDelayed(workOrderRunnable, delay);
            if (text.toString().trim().length() == scanWorkOrderLength) {
                if (NetworkChangeReceiver.isInternetConnected(WorkOrderStartEndActivity.this)) {
                    showProgress(WorkOrderStartEndActivity.this);
                    if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
                        iWorkOrderStartEndInterface.callScanWorkOrderService(new WorkOrderScanInput(editTextScanWorkOrder.getText().toString().trim(), userId, "", "", ""));
                    }
                } else {
                    showSuccessDialog(getString(R.string.no_internet));
//                    CustomToast.showToast(WorkOrderStartEndActivity.this, getString(R.string.no_internet));
                }
            }
        }

    }

    @OnTextChanged(value = R.id.editTextScanWorkOrder, callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void onEditTextScanWorkOrderTEXTCHANGED(Editable text) {
//        scanWorkOrderhandler.removeCallbacks(workOrderRunnable);

    }

//    //for call service on text change
//    Handler scanWorkOrderhandler = new Handler();
//    private Runnable workOrderRunnable = new Runnable() {
//        public void run() {
//            if (NetworkChangeReceiver.isInternetConnected(WorkOrderStartEndActivity.this)) {
//                showProgress(WorkOrderStartEndActivity.this);
//                if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
//                    iWorkOrderStartEndInterface.callScanWorkOrderService(new WorkOrderScanInput(editTextScanWorkOrder.getText().toString().trim(), userId));
//                }
//            } else {
//                CustomToast.showToast(WorkOrderStartEndActivity.this, getString(R.string.no_internet));
//            }
//        }
//    };

    @Override
    public void onWorkOrderScanFailure(String message) {
        hideProgress();
        showSuccessDialog(message);
//        CustomToast.showToast(this, message);
    }

    @Override
    public void onWorkOrderScanSuccess(WorkOrderScanResponse body) {
        hideProgress();
        if (body.getStatus().equalsIgnoreCase(getString(R.string.success))) {
            isScanned = true;
            setData(body);
            editTextScanWorkOrder.setEnabled(false);
        } else {
            showSuccessDialog(body.getMessage());
//            CustomToast.showToast(getApplicationContext(), body.getMessage());
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

        if (body.getQuantity() != null) {
            if (!body.getQuantity().equals("")) {
                woQty = Float.parseFloat(body.getQuantity());
            } else {
                woQty = 0;
            }
        } else {
            woQty = 0;
        }

        if (body.getStartedQty() != null) {
            if (!body.getStartedQty().equals("")) {
                maxStartQty = woQty - Float.parseFloat(body.getStartedQty());
                textViewQtyStarted.setText(body.getStartedQty());
            } else {
                maxStartQty = woQty;
                textViewQtyStarted.setText("0");
            }
        } else {
            maxStartQty = woQty;
            textViewQtyStarted.setText("0");
        }

        if (body.getStatusId().equals("1") || body.getStatusId().equals("4")) {
            if (maxStartQty != 0) {
                if(maxStartQty!=woQty){
                    radioButtonFullQty.setVisibility(View.GONE);
                    radioButtonZeroQty.setVisibility(View.GONE);
                    radioButtonPartialQty.setChecked(true);
                } else {
                    radioButtonFullQty.setVisibility(View.VISIBLE);
                    radioButtonZeroQty.setVisibility(View.VISIBLE);
                    radioButtonFullQty.setChecked(true);
                }
                linearLayoutRoutePick.setVisibility(View.VISIBLE);
                linearLayoutStartQty.setVisibility(View.VISIBLE);
                checkBoxAutoPicking.setChecked(false);
                checkBoxAutoRoute.setChecked(false);
                editTextPartialQty.setText(maxStartQty+"");
                getStartQty = body.getQuantity();
                buttonEnd.setEnabled(false);
                buttonStart.setEnabled(true);
                linearLayoutEndQuantities.setVisibility(View.GONE);
            } else{
                linearLayoutRoutePick.setVisibility(View.GONE);
                linearLayoutStartQty.setVisibility(View.GONE);

                linearLayoutEndQuantities.setVisibility(View.GONE);
                buttonStart.setEnabled(false);
                buttonEnd.setEnabled(false);
            }
        } else if ((body.getStatusId().equals("5"))) {

            linearLayoutRoutePick.setVisibility(View.GONE);
            linearLayoutStartQty.setVisibility(View.GONE);

            buttonEnd.setEnabled(true);
            buttonStart.setEnabled(false);

            linearLayoutEndQuantities.setVisibility(View.VISIBLE);

            if (body.getRafQuantity().equals("")) {
                textViewRafQty.setText(body.getRafQuantity());
            } else {
                if (Utils.isStringIsFloatNum(body.getRafQuantity())) {
                    round(Float.parseFloat(body.getRafQuantity()));
                    textViewRafQty.setText(round(Float.parseFloat(body.getRafQuantity())) + "");
                } else {
                    textViewRafQty.setText(body.getRafQuantity());
                }
            }

            if (body.getBalanceQuantity().equals("")) {
                textViewBalanceQty.setText(body.getBalanceQuantity());
            } else {
                if (Utils.isStringIsFloatNum(body.getBalanceQuantity())) {
                    round(Float.parseFloat(body.getBalanceQuantity()));
                    textViewBalanceQty.setText(round(Float.parseFloat(body.getBalanceQuantity())) + "");
                } else {
                    textViewBalanceQty.setText(body.getBalanceQuantity());
                }
            }

        } else {

            linearLayoutRoutePick.setVisibility(View.GONE);
            linearLayoutStartQty.setVisibility(View.GONE);

            linearLayoutEndQuantities.setVisibility(View.GONE);
            buttonStart.setEnabled(false);
            buttonEnd.setEnabled(false);
        }
    }

    @OnTextChanged(value = R.id.editTextPartialQty, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextPartialQtyChanged(Editable text) {
        if (!text.toString().equals("")) {
            if (Float.parseFloat(text.toString().trim()) > maxStartQty) {
                editTextPartialQty.setText("");
                showSuccessDialog(getResources().getString(R.string.qty_exceeded));
                editTextPartialQty.requestFocus();
            }
        }
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

    boolean isValidated() {
        if (selectedRadio == 0) {
            startQty = getStartQty;
        } else if (selectedRadio == 1) {
            startQty = "0";
        } else if (selectedRadio == 2) {
            String strQty = editTextPartialQty.getText().toString();
            if (strQty.isEmpty() || strQty.equals(".") || strQty.equals("0")) {
                showSuccessDialog(getResources().getString(R.string.enter_qty));
                editTextPartialQty.requestFocus();
                return false;
            } else {
                startQty = strQty;
            }
        }

        return true;
    }

    @OnClick(R.id.buttonStart)
    public void onButtonStartClick() {
        if (isValidated()) {
            showProgress(this);
            WorkOrderScanInput workOrderScanInput = new WorkOrderScanInput(editTextScanWorkOrder.getText().toString().trim(), userId, autoRoute, autoPick, startQty);
            iWorkOrderStartEndInterface.callWorkOrderStartService(workOrderScanInput);
        }
    }

    @OnClick(R.id.buttonEnd)
    public void onButtonEndClick() {
        showProgress(this);
        iWorkOrderStartEndInterface.callWorkOrderEndService(new WorkOrderScanInput(editTextScanWorkOrder.getText().toString().trim(), userId, "", "", ""));
    }

    @Override
    public void onWorkOrderStartFailure(String message) {
        hideProgress();
        showSuccessDialog(message);
//        CustomToast.showToast(this, message);
    }

    @Override
    public void onWorkOrderStartSuccess(UniversalResponse body) {
        hideProgress();
        if (body.getStatus().equalsIgnoreCase(getString(R.string.success))) {
            isScanned = false;
            showSuccessDialog(body.getMessage());
//            CustomToast.showToast(getApplicationContext(), body.getMessage());
            disableViews();
        } else {
            showSuccessDialog(body.getMessage());
//            CustomToast.showToast(getApplicationContext(), body.getMessage());
        }
    }

    @Override
    public void onWorkOrderEndFailure(String message) {
        hideProgress();
        showSuccessDialog(message);
//        CustomToast.showToast(this, message);
    }

    @Override
    public void onWorkOrderEndSuccess(UniversalResponse body) {
        hideProgress();
        if (body.getStatus().equalsIgnoreCase(getString(R.string.success))) {
            isScanned = false;
            showSuccessDialog(body.getMessage());
//            CustomToast.showToast(getApplicationContext(), body.getMessage());
            disableViews();
        } else {
            showSuccessDialog(body.getMessage());
//            CustomToast.showToast(getApplicationContext(), body.getMessage());
        }
    }

    private void disableViews() {
        linearLayoutRoutePick.setVisibility(View.GONE);
        linearLayoutStartQty.setVisibility(View.GONE);
        editTextPartialQty.setText("");

        linearLayoutButtons.setVisibility(View.GONE);
        linearLayoutWorkOrderScanDetail.setVisibility(View.GONE);
        editTextScanWorkOrder.setEnabled(true);
        editTextScanWorkOrder.setText("");
    }

    public void imageButtonHomeClick(View view) {
        if (isScanned) {
            showBackAlert(new Intent(WorkOrderStartEndActivity.this, DashBoardAcivity.class), true);
        } else {
            finishAffinity();
            startActivity(new Intent(WorkOrderStartEndActivity.this, DashBoardAcivity.class));
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
