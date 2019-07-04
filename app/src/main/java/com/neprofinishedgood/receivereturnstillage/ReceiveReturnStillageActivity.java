package com.neprofinishedgood.receivereturnstillage;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatEditText;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.counting.model.StillageDatum;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.returnstillage.ReturnStillageActivity;
import com.neprofinishedgood.utils.StillageLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class ReceiveReturnStillageActivity extends BaseActivity {

    @BindView(R.id.relativeLayoutScanDetail)
    RelativeLayout relativeLayoutScanDetail;

    @BindView(R.id.stillageDetail)
    View stillageDetail;

    @BindView(R.id.editTextScanStillage)
    AppCompatEditText editTextScanStillage;

    StillageLayout stillageLayout;

    StillageDatum stillageDatum;

    Animation fadeOut;
    Animation fadeIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_return_stillage);

        ButterKnife.bind(this);
        setTitle(getString(R.string.recieve_return_stillage));
        initData();
    }

    void initData() {
        stillageLayout = new StillageLayout();
        ButterKnife.bind(stillageLayout, stillageDetail);

        fadeOut = AnimationUtils.loadAnimation(ReceiveReturnStillageActivity.this, R.anim.animate_fade_out);
        fadeIn = AnimationUtils.loadAnimation(ReceiveReturnStillageActivity.this, R.anim.animate_fade_in);
    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanStillageChanged(Editable text) {
        if (text.toString().equalsIgnoreCase("S000001")) {
            relativeLayoutScanDetail.setVisibility(View.VISIBLE);
            relativeLayoutScanDetail.setAnimation(fadeIn);
            setData();
            editTextScanStillage.setEnabled(false);
        } else {
            relativeLayoutScanDetail.setVisibility(View.GONE);
            relativeLayoutScanDetail.setAnimation(fadeOut);
        }
    }

    void setData() {
//        Gson gson = new Gson();
//        StillageDatum stillageDatum = gson.fromJson(JsonString, StillageDatum.class);
        stillageDatum = new StillageDatum();
        stillageDatum.setItem("1");
        stillageDatum.setName("S000001");
        stillageDatum.setNumber("S000001");
        stillageDatum.setQuantity("100");
        stillageDatum.setStdQuantity("100");
        stillageDatum.setStillageId("");

        stillageLayout.textViewitem.setText(stillageDatum.getItem());
        stillageLayout.textViewName.setText(stillageDatum.getName());
        stillageLayout.textViewNumber.setText(stillageDatum.getNumber());
        stillageLayout.textViewQuantity.setText(stillageDatum.getQuantity());
        stillageLayout.textViewStdQuatity.setText(stillageDatum.getStdQuantity());

    }

    @OnClick(R.id.buttonReceive)
    public void onButtonReceiveClick(){
        CustomToast.showToast(ReceiveReturnStillageActivity.this, getResources().getString(R.string.item_received_successfully));
        finish();
    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        editTextScanStillage.setText("");
        editTextScanStillage.setEnabled(true);
        relativeLayoutScanDetail.setVisibility(View.GONE);
        relativeLayoutScanDetail.setAnimation(fadeOut);
        stillageDatum = new StillageDatum();
    }
}
