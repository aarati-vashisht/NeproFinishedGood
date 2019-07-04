package com.neprofinishedgood.counting;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
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

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;

import com.google.gson.Gson;
import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.counting.model.StillageDatum;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.dashboard.DashBoardAcivity;
import com.neprofinishedgood.utils.Constants;
import com.neprofinishedgood.utils.StillageLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class CountingStillageActivity extends BaseActivity {
    @BindView(R.id.frameEnterQuantity)
    FrameLayout frameEnterQuantity;

    @BindView(R.id.frameAssignFlt)
    FrameLayout frameAssignFlt;

    @BindView(R.id.spinnerReason)
    Spinner spinnerReason;

    @BindView(R.id.spinnerAssignFlt)
    Spinner spinnerAssignFlt;

    @BindView(R.id.editTextQuantity)
    AppCompatEditText editTextQuantity;

    @BindView(R.id.cardView)
    CardView cardView;

    @BindView(R.id.linearLayoutReason)
    LinearLayout linearLayoutReason;

    @BindView(R.id.buttonPutAway)
    CustomButton buttonConfirm;

    @BindView(R.id.buttonCancel)
    CustomButton buttonCancel;

    @BindView(R.id.buttonAssign)
    CustomButton buttonAssign;

    @BindView(R.id.buttonUnAssign)
    CustomButton buttonUnAssign;

    @BindView(R.id.linearLayoutScanDetail)
    LinearLayout linearLayoutScanDetail;

    @BindView(R.id.stillageDetail)
    View stillageDetail;

    StillageLayout stillageLayout;

    float stillageQtyNo = 0;
    float editQtyNo = 0;

    Animation fadeOut;
    Animation fadeIn;
    String SELECTED_STILLAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counting_stillage);
        ButterKnife.bind(this);
        initData();
    }

    void initData() {
        stillageLayout = new StillageLayout();
        ButterKnife.bind(stillageLayout, stillageDetail);

        fadeOut = AnimationUtils.loadAnimation(CountingStillageActivity.this, R.anim.animate_fade_out);
        fadeIn = AnimationUtils.loadAnimation(CountingStillageActivity.this, R.anim.animate_fade_in);
        SELECTED_STILLAGE = getIntent().getStringExtra(Constants.SELECTED_STILLAGE);
        Gson gson = new Gson();
        StillageDatum stillageDatum = gson.fromJson(SELECTED_STILLAGE, StillageDatum.class);
        setTitle(stillageDatum.getName());

        stillageLayout.textViewitem.setText(stillageDatum.getItem());
        stillageLayout.textViewName.setText(stillageDatum.getName());
        stillageLayout.textViewNumber.setText(stillageDatum.getNumber());
        stillageLayout.textViewQuantity.setText(stillageDatum.getQuantity());
        stillageLayout.textViewStdQuatity.setText(stillageDatum.getStdQuantity());
        editTextQuantity.setText(stillageDatum.getQuantity());
        editTextQuantity.setSelection(stillageDatum.getQuantity().length());
    }

    @OnTextChanged(value = R.id.editTextQuantity,
            callback = OnTextChanged.Callback.TEXT_CHANGED)
    protected void afterEditTextChanged(Editable editable) {
        String stillageQty, editQty;
        stillageQty = stillageLayout.textViewQuantity.getText().toString();
        editQty = editable.toString();
        stillageQtyNo = Float.parseFloat(stillageQty);
        if (!editQty.equals("")) {
            editQtyNo = Float.parseFloat(editQty);
            if (editQtyNo < stillageQtyNo) {
                linearLayoutReason.setVisibility(View.VISIBLE);
                buttonConfirm.setEnabled(true);
            } else if (editQtyNo > stillageQtyNo) {
                linearLayoutReason.setVisibility(View.GONE);
                editTextQuantity.setError(getResources().getString(R.string.quantity_must_not_greater_than_stillage_qty), getDrawable(R.drawable.error_icon));
                editTextQuantity.requestFocus();
                buttonConfirm.setEnabled(false);
            } else {
                linearLayoutReason.setVisibility(View.GONE);
                buttonConfirm.setEnabled(true);
            }
        } else {
            editTextQuantity.setError(getResources().getString(R.string.quantiy_must_not_be_blank));
            editTextQuantity.requestFocus();
            buttonConfirm.setEnabled(false);
        }
        setData();
    }

    void setData() {
        String[] reasonsList = {"Select UniversalSpinner", "Wrong Product", "Product Damaged", "Other"};
        ArrayAdapter<String> reasonAdapter = new ArrayAdapter(this, R.layout.spinner_layout, reasonsList);
        spinnerReason.setAdapter(reasonAdapter);

        String[] assignFltList = {"Select FLT", "option 1", "option 2", "option 3"};
        ArrayAdapter<String> assignFltAdapter = new ArrayAdapter(this, R.layout.spinner_layout, assignFltList);
        spinnerAssignFlt.setAdapter(assignFltAdapter);

    }

    @OnClick(R.id.buttonPutAway)
    public void onButtonConfirmClick() {

        if (editQtyNo < stillageQtyNo) {
            if (!spinnerReason.getSelectedItem().toString().equals("Select UniversalSpinner")) {

                frameEnterQuantity.setVisibility(View.GONE);
                frameEnterQuantity.setAnimation(fadeOut);
                frameAssignFlt.setVisibility(View.VISIBLE);
                frameAssignFlt.setAnimation(fadeIn);

//                Utils.animateFadeOut(frameEnterQuantity, 500);
//                Utils.animateFadeIn(frameAssignFlt, 500);


            } else {
                TextView textView = (TextView) spinnerReason.getSelectedView();
                textView.setError("Select reason");
                textView.requestFocus();
            }
        } else {
            frameEnterQuantity.setVisibility(View.GONE);
            frameEnterQuantity.setAnimation(fadeOut);
            frameAssignFlt.setVisibility(View.VISIBLE);
            frameAssignFlt.setAnimation(fadeIn);

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
        startActivity(new Intent(CountingStillageActivity.this, DashBoardAcivity.class));
        finish();
    }

    @OnClick(R.id.buttonAssign)
    public void onButtonAssignClick() {

    }

    @OnClick(R.id.buttonUnAssign)
    public void onButtonUnAssignClick() {

    }
}
