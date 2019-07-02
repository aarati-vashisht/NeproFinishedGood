package com.neprofinishedgood.qualitycheck.qualityholdandmove;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.counting.model.StillageDatum;
import com.neprofinishedgood.custom_views.CustomToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class QualityHoldAndMove extends BaseActivity {

    @BindView(R.id.linearLayoutScanDetail)
    LinearLayout linearLayoutScanDetail;

    @BindView(R.id.textViewName)
    TextView textViewName;

    @BindView(R.id.textViewitem)
    TextView textViewitem;

    @BindView(R.id.textViewNumber)
    TextView textViewNumber;

    @BindView(R.id.textViewQuantity)
    TextView textViewQuantity;

    @BindView(R.id.textViewStdQuatity)
    TextView textViewStdQuatity;

    @BindView(R.id.buttonHold)
    Button buttonHold;

    @BindView(R.id.buttonUnhold)
    Button buttonUnhold;

    @BindView(R.id.editTextScanStillage)
    EditText editTextScanStillage;

    Animation fadeOut;
    Animation fadeIn;

    StillageDatum stillageDatum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_hold_and_move);

        ButterKnife.bind(this);
        setTitle(getString(R.string.quality_hold_and_move));
        initData();
    }

    void initData() {
        fadeOut = AnimationUtils.loadAnimation(QualityHoldAndMove.this, R.anim.animate_fade_out);
        fadeIn = AnimationUtils.loadAnimation(QualityHoldAndMove.this, R.anim.animate_fade_in);
    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanStillageChanged(Editable text) {
        if (text.toString().equalsIgnoreCase("S000001") || text.toString().equalsIgnoreCase("S000002")) {
            linearLayoutScanDetail.setVisibility(View.VISIBLE);
            linearLayoutScanDetail.setAnimation(fadeIn);
            setData();
        } else {
            linearLayoutScanDetail.setVisibility(View.GONE);
            linearLayoutScanDetail.setAnimation(fadeOut);
        }
    }

    void setData() {
//        Gson gson = new Gson();
//        StillageDatum stillageDatum = gson.fromJson(JsonString, StillageDatum.class);
        stillageDatum = new StillageDatum();
        String str = editTextScanStillage.getText().toString();
        stillageDatum.setItem("1");
        stillageDatum.setName(str);
        stillageDatum.setNumber(str);
        stillageDatum.setQuantity("100");
        stillageDatum.setStdQuantity("100");
        stillageDatum.setStillageId("");

        textViewitem.setText(stillageDatum.getItem());
        textViewName.setText(stillageDatum.getName());
        textViewNumber.setText(stillageDatum.getNumber());
        textViewQuantity.setText(stillageDatum.getQuantity());
        textViewStdQuatity.setText(stillageDatum.getStdQuantity());

        if(str.equalsIgnoreCase("S000001")){
            buttonHold.setEnabled(true);
            buttonUnhold.setEnabled(false);
        }else if(str.equalsIgnoreCase("S000002")){
            buttonHold.setEnabled(false);
            buttonUnhold.setEnabled(true);
        }
    }

    @OnClick(R.id.buttonHold)
    public void onButtonHoldClick() {
       CustomToast.showToast(QualityHoldAndMove.this, getResources().getString(R.string.items_hold));
       finish();
    }

    @OnClick(R.id.buttonUnhold)
    public void onButtonUnholdClick() {
        CustomToast.showToast(QualityHoldAndMove.this, getResources().getString(R.string.items_unhold));
        finish();
    }

}
