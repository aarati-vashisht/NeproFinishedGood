package com.neprofinishedgood.putaway;


import android.content.Intent;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;

import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.dashboard.DashBoardAcivity;
import com.neprofinishedgood.putaway.Adapter.SpinnerAdapter;
import com.neprofinishedgood.putaway.model.Reason;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;

public class PutAwayActivity extends BaseActivity {

    @BindView(R.id.frameEnterQuantity)
    FrameLayout frameEnterQuantity;

    @BindView(R.id.frameAssignFlt)
    FrameLayout frameAssignFlt;

    @BindView(R.id.spinnerReason)
    Spinner spinnerReason;

    @BindView(R.id.spinnerAssignFlt)
    Spinner spinnerAssignFlt;

    @BindView(R.id.textViewQuantity)
    TextView textViewQuantity;

    @BindView(R.id.editTextQuantity)
    EditText editTextQuantity;

    @BindView(R.id.linearLayoutReason)
    LinearLayout linearLayoutReason;

    @BindView(R.id.buttonConfirm)
    Button buttonConfirm;

    @BindView(R.id.buttonCancel)
    Button buttonCancel;

    @BindView(R.id.buttonAssign)
    Button buttonAssign;

    @BindView(R.id.buttonUnAssign)
    Button buttonUnAssign;

    @BindView(R.id.linearLayoutScanDetail)
    LinearLayout linearLayoutScanDetail;

    @BindView(R.id.editTextScanStillage)
    EditText editTextScanStillage;

    float stillageQtyNo = 0;
    float editQtyNo = 0;

    Animation fadeOut;
    Animation fadeIn;

    ArrayList<Reason> reasons;
    ArrayList<Reason> fltList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_away);
        ButterKnife.bind(this);
        setTitle(getString(R.string.put_away));
        initData();
    }

    void initData() {
        fadeOut = AnimationUtils.loadAnimation(PutAwayActivity.this, R.anim.animate_fade_out);
        fadeIn = AnimationUtils.loadAnimation(PutAwayActivity.this, R.anim.animate_fade_in);
    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanStillageChanged(Editable text) {
        if (text.toString().equalsIgnoreCase("S000001")) {
            linearLayoutScanDetail.setVisibility(View.VISIBLE);
            linearLayoutScanDetail.setAnimation(fadeIn);
            setData();
        }
        else{
            linearLayoutScanDetail.setVisibility(View.GONE);
            linearLayoutScanDetail.setAnimation(fadeOut);
        }
    }

    @OnTextChanged(value = R.id.editTextQuantity, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextQuantityChanged(Editable text){
        String stillageQty, editQty;
        stillageQty = textViewQuantity.getText().toString();
        editQty = text.toString();

        stillageQtyNo = Float.parseFloat(stillageQty);

        if (!editQty.equals("")) {
            editQtyNo = Float.parseFloat(editQty);

            if (editQtyNo < stillageQtyNo) {
                linearLayoutReason.setVisibility(View.VISIBLE);
                buttonConfirm.setEnabled(true);
            } else if (editQtyNo > stillageQtyNo) {
                linearLayoutReason.setVisibility(View.GONE);
                editTextQuantity.setError("Quantity must not greater than stillage quantity!");
                editTextQuantity.requestFocus();
                buttonConfirm.setEnabled(false);
            } else {
                linearLayoutReason.setVisibility(View.GONE);
                buttonConfirm.setEnabled(true);
            }
        } else {
            editTextQuantity.setError("Quantity must not blank!");
            editTextQuantity.requestFocus();
            buttonConfirm.setEnabled(false);
        }
    }

    void setData() {

        editTextQuantity.setText(textViewQuantity.getText().toString());

        reasons = new ArrayList<>();
        for(int i = 0; i<5; i++){
            reasons.add(new Reason("Reason "+i,i+""));
        }
        reasons.add(0,new Reason("Select Reason","0"));
        SpinnerAdapter reasonListAdapter = new SpinnerAdapter(PutAwayActivity.this, R.layout.spinner_layout, reasons );
        spinnerReason.setAdapter(reasonListAdapter);

        fltList = new ArrayList<>();
        for(int i = 0; i<5; i++){
            fltList.add(new Reason("Flt "+i,i+""));
        }
        fltList.add(0,new Reason("Select Flt","0"));
        SpinnerAdapter fltLitAdapter = new SpinnerAdapter(PutAwayActivity.this, R.layout.spinner_layout, fltList );
        spinnerAssignFlt.setAdapter(fltLitAdapter);

    }

    @OnItemSelected(R.id.spinnerAssignFlt)
    void onItemSelected(int position) {
        if(position == 0){
            buttonAssign.setEnabled(false);
        }
        else {
            buttonAssign.setEnabled(true);
        }
    }

    @OnClick(R.id.buttonConfirm)
    public void onButtonConfirmClick() {

        if (editQtyNo < stillageQtyNo) {
            if (spinnerReason.getSelectedItemPosition() != 0) {

                frameEnterQuantity.setVisibility(View.GONE);
                frameAssignFlt.setVisibility(View.VISIBLE);

//                Utils.animateFadeOut(frameEnterQuantity, 500);
//                Utils.animateFadeIn(frameAssignFlt, 500);
            } else {
                TextView textView = (TextView) spinnerReason.getSelectedView();
                textView.setError(getString(R.string.select_reason));
                textView.requestFocus();
            }
        } else {
            frameEnterQuantity.setVisibility(View.GONE);
            frameAssignFlt.setVisibility(View.VISIBLE);
//            frameEnterQuantity.animate()
//                    .alpha(0.0f)
//                    .setDuration(500)
//                    .setListener(new AnimatorListenerAdapter() {
//                        @Override
//                        public void onAnimationEnd(Animator animation) {
//                            super.onAnimationEnd(animation);
//                            frameEnterQuantity.setVisibility(View.GONE);
//                            frameAssignFlt.setAlpha(0.0f);
//                            frameAssignFlt.animate()
//                                    .alpha(1.0f)
//                                    .setDuration(500)
//                                    .setListener(new AnimatorListenerAdapter() {
//                                        @Override
//                                        public void onAnimationEnd(Animator animation) {
//                                            super.onAnimationEnd(animation);
//                                            frameAssignFlt.setVisibility(View.VISIBLE);
//                                        }
//                                    });
//                        }
//                    });
        }
    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        finish();
    }

    @OnClick(R.id.buttonAssign)
    public void onButtonAssignClick() {
        CustomToast.showTOast(PutAwayActivity.this, getString(R.string.data_saved_successfully));
        finish();
    }

    @OnClick(R.id.buttonUnAssign)
    public void onButtonUnAssignClick() {
        CustomToast.showTOast(PutAwayActivity.this, getString(R.string.data_saved_successfully));
        finish();
    }
}
