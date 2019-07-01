package com.neprofinishedgood.putaway;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PutAwayActivity extends BaseActivity {

    @BindView(R.id.buttonPutAway)
    Button buttonPutAway;

    @BindView(R.id.buttonCancel)
    Button buttonCancel;

    @BindView(R.id.linearLayoutScanDetail)
    LinearLayout linearLayoutScanDetail;

    @BindView(R.id.editTextScanStillage)
    EditText editTextScanStillage;

    float stillageQtyNo = 0;
    float editQtyNo = 0;

    Animation fadeOut;
    Animation fadeIn;


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
                    linearLayoutScanDetail.setAnimation(fadeIn);
                } else if (str.equals("S000002")) {

                }
            }
        });
    }


    @OnClick(R.id.buttonPutAway)
    public void onButtonConfirmClick() {
        if (editTextScanStillage.getText().toString().trim().length() > 0) {
            finish();
        } else {
            editTextScanStillage.setError(getResources().getString(R.string.please_scan_stillage));
        }


    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        linearLayoutScanDetail.setVisibility(View.GONE);
        linearLayoutScanDetail.setAnimation(fadeOut);
        editTextScanStillage.setText("");
    }


}
