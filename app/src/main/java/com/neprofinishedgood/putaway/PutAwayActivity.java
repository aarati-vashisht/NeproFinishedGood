package com.neprofinishedgood.putaway;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;

import com.neprofinishedgood.dashboard.DashBoardAcivity;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_away);
        ButterKnife.bind(this);
        setTitle(getString(R.string.put_away));


        initData();
    }

    void initData() {
        editTextScanStillage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editTextScanStillage.getText().toString();
                if (str.equals("S000001")) {
                    linearLayoutScanDetail.setVisibility(View.VISIBLE);
                    setData();
                }
            }
        });
    }

    void setData() {

        editTextQuantity.setText(textViewQuantity.getText().toString());

        editTextQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String stillageQty, editQty;
                stillageQty = textViewQuantity.getText().toString();
                editQty = editTextQuantity.getText().toString();

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
        });

        String[] reasonsList = {"Select Reason", "Wrong Product", "Product Damaged", "Other"};
        ArrayAdapter<String> reasonAdapter = new ArrayAdapter(this, R.layout.spinner_layout, reasonsList);
        spinnerReason.setAdapter(reasonAdapter);

        String[] assignFltList = {"Select FLT", "option 1", "option 2", "option 3"};
        ArrayAdapter<String> assignFltAdapter = new ArrayAdapter(this, R.layout.spinner_layout, assignFltList);
        spinnerAssignFlt.setAdapter(assignFltAdapter);

    }

    @OnClick(R.id.buttonConfirm)
    public void onButtonConfirmClick() {

        if (editQtyNo < stillageQtyNo) {
            if (!spinnerReason.getSelectedItem().toString().equals("Select Reason")) {
                frameEnterQuantity.animate()
                        .alpha(0.0f)
                        .setDuration(500)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                frameEnterQuantity.setVisibility(View.GONE);
                                frameAssignFlt.setAlpha(0.0f);
                                frameAssignFlt.animate()
                                        .alpha(1.0f)
                                        .setDuration(500)
                                        .setListener(new AnimatorListenerAdapter() {
                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                super.onAnimationEnd(animation);
                                                frameAssignFlt.setVisibility(View.VISIBLE);
                                            }
                                        });
                            }
                        });
            } else {
                TextView textView = (TextView) spinnerReason.getSelectedView();
                textView.setError("Select reason");
                textView.requestFocus();
            }
        } else {
            frameEnterQuantity.animate()
                    .alpha(0.0f)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            frameEnterQuantity.setVisibility(View.GONE);
                            frameAssignFlt.setAlpha(0.0f);
                            frameAssignFlt.animate()
                                    .alpha(1.0f)
                                    .setDuration(500)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                            frameAssignFlt.setVisibility(View.VISIBLE);
                                        }
                                    });
                        }
                    });
        }
    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        startActivity(new Intent(PutAwayActivity.this, DashBoardAcivity.class));
        finish();
    }

    @OnClick(R.id.buttonAssign)
    public void onButtonAssignClick() {

    }

    @OnClick(R.id.buttonUnAssign)
    public void onButtonUnAssignClick() {

    }
}
