package com.neprofinishedgood.updatequantity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatEditText;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.raf.model.StillageDatum;
import com.neprofinishedgood.utils.StillageLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class UpdateQuantityActivity extends BaseActivity {

    @BindView(R.id.linearLayoutScanDetail)
    LinearLayout linearLayoutScanDetail;
    @BindView(R.id.linearLayoutEnterQuantity)
    LinearLayout linearLayoutEnterQuantity;
    @BindView(R.id.linearLayoutButtons)
    LinearLayout linearLayoutButtons;
    @BindView(R.id.editTextScanStillage)
    AppCompatEditText editTextScanStillage;
    @BindView(R.id.editTextQuantity)
    AppCompatEditText editTextQuantity;
    @BindView(R.id.buttonConfirm)
    CustomButton buttonConfirm;

    @BindView(R.id.stillageDetail)
    View stillageDetail;

    StillageLayout stillageLayout;
    StillageDatum stillageDatum;

    Animation fadeOut;
    Animation fadeIn;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_quantity);

        ButterKnife.bind(this);
        setTitle(getString(R.string.update_quantity));
        initData();
    }

    void initData() {
        stillageLayout = new StillageLayout();
        ButterKnife.bind(stillageLayout, stillageDetail);

        fadeOut = AnimationUtils.loadAnimation(UpdateQuantityActivity.this, R.anim.animate_fade_out);
        fadeIn = AnimationUtils.loadAnimation(UpdateQuantityActivity.this, R.anim.animate_fade_in);
    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanStillageChanged(Editable text) {
        if (text.toString().equalsIgnoreCase("S00001")) {
            linearLayoutScanDetail.setVisibility(View.VISIBLE);
            linearLayoutScanDetail.setAnimation(fadeIn);
            linearLayoutEnterQuantity.setVisibility(View.VISIBLE);
            linearLayoutEnterQuantity.setAnimation(fadeIn);
            linearLayoutButtons.setVisibility(View.VISIBLE);
            linearLayoutButtons.setAnimation(fadeIn);
            setData();
            editTextScanStillage.setEnabled(false);
            editTextQuantity.setText(stillageLayout.textViewQuantity.getText().toString());
        } else {
            linearLayoutScanDetail.setVisibility(View.GONE);
            linearLayoutScanDetail.setAnimation(fadeOut);
            linearLayoutEnterQuantity.setVisibility(View.GONE);
            linearLayoutEnterQuantity.setAnimation(fadeOut);
            linearLayoutButtons.setVisibility(View.GONE);
            linearLayoutButtons.setAnimation(fadeOut);
        }
    }

    void setData() {
//        Gson gson = new Gson();
//        LoadingPlanDatum stillageDatum = gson.fromJson(JsonString, LoadingPlanDatum.class);
        stillageDatum = new StillageDatum();
        stillageDatum.setItem("1");
        stillageDatum.setName("S00001");
        stillageDatum.setNumber("S00001");
        stillageDatum.setQuantity("50");
        stillageDatum.setStdQuantity("100");
        stillageDatum.setStillageId("");

        stillageLayout.textViewitem.setText(stillageDatum.getItem());
        stillageLayout.textViewNumber.setText(stillageDatum.getNumber());
        stillageLayout.textViewQuantity.setText(stillageDatum.getQuantity());
        stillageLayout.textViewStdQuantity.setText(stillageDatum.getStdQuantity());
    }

    @OnTextChanged(value = R.id.editTextQuantity, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextQuantityChanged(Editable text) {
        int stillageQty, stillageStdQty, editQty;

        if (!text.toString().equals("")) {
            stillageStdQty = Integer.parseInt(stillageLayout.textViewStdQuantity.getText().toString());
            editQty = Integer.parseInt(text.toString());

            if (editQty > stillageStdQty) {
                editTextQuantity.setError(getString(R.string.quantity_must_not_greater_than_stillage_std_qty));
                editTextQuantity.requestFocus();
                buttonConfirm.setEnabled(false);
            } else if (editQty == 0) {
                editTextQuantity.setError(getString(R.string.quantity_must_not_be_zero));
                editTextQuantity.requestFocus();
                buttonConfirm.setEnabled(false);
            } else {
                buttonConfirm.setEnabled(true);
            }
        } else {
            buttonConfirm.setEnabled(false);
        }
    }

    @OnClick(R.id.buttonConfirm)
    public void onButtonConfirmClick(){
        CustomToast.showToast(UpdateQuantityActivity.this, getResources().getString(R.string.quantity_updated_successfully));
        finish();
    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick(){
        linearLayoutScanDetail.setVisibility(View.GONE);
        linearLayoutScanDetail.setAnimation(fadeOut);
        linearLayoutEnterQuantity.setVisibility(View.GONE);
        linearLayoutEnterQuantity.setAnimation(fadeOut);
        linearLayoutButtons.setVisibility(View.GONE);
        linearLayoutButtons.setAnimation(fadeOut);
        editTextScanStillage.setEnabled(true);
        editTextScanStillage.setText("");
    }
}
