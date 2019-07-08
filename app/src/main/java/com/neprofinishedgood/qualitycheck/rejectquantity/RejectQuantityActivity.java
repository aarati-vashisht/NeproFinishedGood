package com.neprofinishedgood.qualitycheck.rejectquantity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalSpinner;
import com.neprofinishedgood.counting.model.StillageDatum;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.putaway.Adapter.SpinnerAdapter;
import com.neprofinishedgood.qualitycheck.qualityholdandmove.QualityHoldAndMove;
import com.neprofinishedgood.utils.StillageLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class RejectQuantityActivity extends BaseActivity {

    @BindView(R.id.linearLayoutScanDetail)
    LinearLayout linearLayoutScanDetail;

    @BindView(R.id.stillageDetail)
    View stillageDetail;

    StillageLayout stillageLayout;

    @BindView(R.id.editTextRejectQuantity)
    AppCompatEditText editTextRejectQuantity;

    @BindView(R.id.spinnerRejectReason)
    Spinner spinnerRejectReason;

    @BindView(R.id.buttonReject)
    CustomButton buttonReject;

    @BindView(R.id.editTextScanStillage)
    AppCompatEditText editTextScanStillage;

    Animation fadeOut;
    Animation fadeIn;

    float stillageQtyNo = 0;
    float editQtyNo = 0;

    ArrayList<UniversalSpinner> reasons;

    StillageDatum stillageDatum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reject_quantity);

        ButterKnife.bind(this);
        setTitle(getString(R.string.reject_quantity));
        initData();
    }

    void initData() {
        stillageLayout = new StillageLayout();
        ButterKnife.bind(stillageLayout, stillageDetail);

        fadeOut = AnimationUtils.loadAnimation(RejectQuantityActivity.this, R.anim.animate_fade_out);
        fadeIn = AnimationUtils.loadAnimation(RejectQuantityActivity.this, R.anim.animate_fade_in);
    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanStillageChanged(Editable text) {
        if (text.toString().equalsIgnoreCase("S00001")) {
            linearLayoutScanDetail.setVisibility(View.VISIBLE);
            linearLayoutScanDetail.setAnimation(fadeIn);
            setData();
            editTextScanStillage.setEnabled(false);
        } else if (text.toString().equalsIgnoreCase("S00003")) {
            linearLayoutScanDetail.setVisibility(View.VISIBLE);
            linearLayoutScanDetail.setAnimation(fadeIn);
            setData();
            editTextScanStillage.setEnabled(false);
        } else {
            linearLayoutScanDetail.setVisibility(View.GONE);
            linearLayoutScanDetail.setAnimation(fadeOut);
        }
    }

    @OnTextChanged(value = R.id.editTextRejectQuantity, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextRejectQuantityChanged(Editable text) {
        String stillageQty, editQty;
        stillageQty = stillageLayout.textViewQuantity.getText().toString();
        editQty = text.toString();

        stillageQtyNo = Float.parseFloat(stillageQty);

        if (!editQty.equals("")) {
            editQtyNo = Float.parseFloat(editQty);
            if (editQtyNo == 0) {
                buttonReject.setEnabled(false);
            }
            if (editQtyNo > stillageQtyNo) {
                editTextRejectQuantity.setError(getResources().getString(R.string.quantity_must_not_greater_than_stillage_qty));
                editTextRejectQuantity.requestFocus();
                buttonReject.setEnabled(false);
            } else {
                buttonReject.setEnabled(true);
            }
        } else {
            editTextRejectQuantity.setError("Quantity must not blank!");
            editTextRejectQuantity.requestFocus();
            buttonReject.setEnabled(false);
        }
    }

    void setData() {
//        Gson gson = new Gson();
//        LoadingPlanDatum stillageDatum = gson.fromJson(JsonString, LoadingPlanDatum.class);
        stillageDatum = new StillageDatum();
        stillageDatum.setItem("1");
        stillageDatum.setName("S00001");
        stillageDatum.setNumber("S00001");
        stillageDatum.setQuantity("100");
        stillageDatum.setStdQuantity("100");
        stillageDatum.setStillageId("");

        stillageLayout.textViewitem.setText(stillageDatum.getItem());
        stillageLayout.textViewNumber.setText(stillageDatum.getNumber());
        stillageLayout.textViewQuantity.setText(stillageDatum.getQuantity());
        stillageLayout.textViewStdQuatity.setText(stillageDatum.getStdQuantity());


        reasons = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            reasons.add(new UniversalSpinner("Reason " + i, i + ""));
        }
        reasons.add(0, new UniversalSpinner("Select Reason", "0"));
        SpinnerAdapter reasonListAdapter = new SpinnerAdapter(RejectQuantityActivity.this, R.layout.spinner_layout, reasons);
        spinnerRejectReason.setAdapter(reasonListAdapter);
    }

    @OnClick(R.id.buttonReject)
    public void onButtonRejectClick() {
        if (isValidated()) {
            if (editTextScanStillage.getText().toString().equalsIgnoreCase("s00003")) {
                CustomToast.showToast(RejectQuantityActivity.this, getResources().getString(R.string.items_rejected_successfully));
                startActivity(new Intent(this, QualityHoldAndMove.class).putExtra("DATA", "DATA"));
                finish();
            } else {
                CustomToast.showToast(RejectQuantityActivity.this, getResources().getString(R.string.items_rejected_successfully));
                finish();
            }
        }

    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        linearLayoutScanDetail.setVisibility(View.GONE);
        linearLayoutScanDetail.setAnimation(fadeOut);
        editTextScanStillage.setText("");
        stillageDatum = new StillageDatum();
        editTextScanStillage.setEnabled(true);
    }

    boolean isValidated() {
        if (editTextRejectQuantity.getText().toString().equals("0")) {
            editTextRejectQuantity.setError(getResources().getString(R.string.enter_reject_quantity));
            editTextRejectQuantity.requestFocus();
            return false;
        }
        if (spinnerRejectReason.getSelectedItemPosition() == 0) {
            TextView textView = (TextView) spinnerRejectReason.getSelectedView();
            textView.setError(getString(R.string.select_reason));
            textView.requestFocus();
            return false;
        }
        return true;
    }
}
