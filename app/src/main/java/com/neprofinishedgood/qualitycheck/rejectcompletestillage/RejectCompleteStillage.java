package com.neprofinishedgood.qualitycheck.rejectcompletestillage;

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
import com.neprofinishedgood.qualitycheck.qualityhold.QualityHold;
import com.neprofinishedgood.raf.model.StillageDatum;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.plannedandunplannedmove.Adapter.SpinnerAdapter;
import com.neprofinishedgood.utils.StillageLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class RejectCompleteStillage extends BaseActivity {

    @BindView(R.id.linearLayoutScanDetail)
    LinearLayout linearLayoutScanDetail;

    @BindView(R.id.stillageDetail)
    View stillageDetail;

    StillageLayout stillageLayout;

    @BindView(R.id.spinnerRejectReason)
    Spinner spinnerRejectReason;

    @BindView(R.id.buttonReject)
    CustomButton buttonReject;

    @BindView(R.id.editTextScanStillage)
    AppCompatEditText editTextScanStillage;

    Animation fadeOut;
    Animation fadeIn;

    ArrayList<UniversalSpinner> reasons;

    StillageDatum stillageDatum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reject_complete_stillage);

        ButterKnife.bind(this);
        setTitle(getString(R.string.reject_complete_stillage));
        initData();
    }

    void initData() {
        stillageLayout = new StillageLayout();
        ButterKnife.bind(stillageLayout, stillageDetail);

        fadeOut = AnimationUtils.loadAnimation(RejectCompleteStillage.this, R.anim.animate_fade_out);
        fadeIn = AnimationUtils.loadAnimation(RejectCompleteStillage.this, R.anim.animate_fade_in);
    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanStillageChanged(Editable text) {
        if (text.toString().equalsIgnoreCase("S00001")) {
            linearLayoutScanDetail.setVisibility(View.VISIBLE);
            linearLayoutScanDetail.setAnimation(fadeIn);
            editTextScanStillage.setEnabled(false);
            setData();
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
        stillageLayout.textViewStdQuantity.setText(stillageDatum.getStdQuantity());


        reasons = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            reasons.add(new UniversalSpinner("Reason " + i, i + ""));
        }
        reasons.add(0, new UniversalSpinner("Select Reason", "0"));
        SpinnerAdapter reasonListAdapter = new SpinnerAdapter(RejectCompleteStillage.this, R.layout.spinner_layout, reasons);
        spinnerRejectReason.setAdapter(reasonListAdapter);
    }

    @OnClick(R.id.buttonReject)
    public void onButtonRejectClick() {
        if (isValidated()) {
            if (editTextScanStillage.getText().toString().equalsIgnoreCase("s00003")) {
                CustomToast.showToast(RejectCompleteStillage.this, getResources().getString(R.string.items_rejected_successfully));
                startActivity(new Intent(this, QualityHold.class).putExtra("DATA", "DATA"));
                finish();
            } else {
                CustomToast.showToast(RejectCompleteStillage.this, getResources().getString(R.string.items_rejected_successfully));
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
        if (spinnerRejectReason.getSelectedItemPosition() == 0) {
            TextView textView = (TextView) spinnerRejectReason.getSelectedView();
            textView.setError(getString(R.string.select_reason));
            textView.requestFocus();
            return false;
        }
        return true;
    }
}
