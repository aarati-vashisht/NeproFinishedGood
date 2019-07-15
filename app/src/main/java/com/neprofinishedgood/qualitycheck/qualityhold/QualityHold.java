package com.neprofinishedgood.qualitycheck.qualityhold;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatEditText;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.raf.model.StillageDatum;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.utils.StillageLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class QualityHold extends BaseActivity {

    @BindView(R.id.linearLayoutScanDetail)
    LinearLayout linearLayoutScanDetail;

    @BindView(R.id.stillageDetail)
    View stillageDetail;

    StillageLayout stillageLayout;

    @BindView(R.id.buttonHold)
    CustomButton buttonHold;

    @BindView(R.id.buttonUnhold)
    CustomButton buttonUnhold;

    @BindView(R.id.editTextScanStillage)
    AppCompatEditText editTextScanStillage;

    Animation fadeOut;
    Animation fadeIn;

    StillageDatum stillageDatum;
    private String data = "";
    private String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_hold);

        ButterKnife.bind(this);
        setTitle(getString(R.string.quality_hold));
        initData();
    }

    void initData() {
        stillageLayout = new StillageLayout();
        ButterKnife.bind(stillageLayout, stillageDetail);
        data = getIntent().getStringExtra("DATA");
        if (data == null) data = "";

        setData();
        fadeOut = AnimationUtils.loadAnimation(QualityHold.this, R.anim.animate_fade_out);
        fadeIn = AnimationUtils.loadAnimation(QualityHold.this, R.anim.animate_fade_in);
    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanStillageChanged(Editable text) {
        if (text.toString().equalsIgnoreCase("S00001") || text.toString().equalsIgnoreCase("S00002")) {
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
        if (data.equalsIgnoreCase("DATA")) {
            str = "S00003";
            editTextScanStillage.setText(str);
            stillageDatum = new StillageDatum();
            stillageDatum.setItem("1");
            stillageDatum.setName(str);
            stillageDatum.setNumber(str);
            stillageDatum.setQuantity("100");
            stillageDatum.setStdQuantity("100");
            stillageDatum.setStillageId("");
            linearLayoutScanDetail.setVisibility(View.VISIBLE);
            linearLayoutScanDetail.setAnimation(fadeIn);

        } else {
            str = editTextScanStillage.getText().toString();
            stillageDatum = new StillageDatum();
            stillageDatum.setItem("1");
            stillageDatum.setName(str);
            stillageDatum.setNumber(str);
            stillageDatum.setQuantity("100");
            stillageDatum.setStdQuantity("100");
            stillageDatum.setStillageId("");
        }
        stillageLayout.textViewitem.setText(stillageDatum.getItem());
        stillageLayout.textViewNumber.setText(stillageDatum.getNumber());
        stillageLayout.textViewQuantity.setText(stillageDatum.getQuantity());
        stillageLayout.textViewStdQuantity.setText(stillageDatum.getStdQuantity());

        if (str.equalsIgnoreCase("S00001")) {
            buttonHold.setEnabled(true);
            buttonUnhold.setEnabled(false);
        } else if (str.equalsIgnoreCase("S00002")) {
            buttonHold.setEnabled(false);
            buttonUnhold.setEnabled(true);
        } else if (str.equalsIgnoreCase("S00003")) {
            buttonHold.setEnabled(false);
            buttonUnhold.setEnabled(true);
        }
    }

    @OnClick(R.id.buttonHold)
    public void onButtonHoldClick() {
        CustomToast.showToast(QualityHold.this, getResources().getString(R.string.items_hold));
        finish();
    }

    @OnClick(R.id.buttonUnhold)
    public void onButtonUnholdClick() {
        CustomToast.showToast(QualityHold.this, getResources().getString(R.string.items_unhold));
        finish();
    }

}
