package com.neprofinishedgood.lookup;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatEditText;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class LookUpActivity extends BaseActivity  {
    @BindView(R.id.stillageLayoutLookUp)
    LinearLayout stillageLayoutLookUp;
    @BindView(R.id.linearLayoutLocation)
    LinearLayout linearLayoutLocation;
    @BindView(R.id.editTextScanStillage)
    AppCompatEditText editTextScanStillage;
    Animation fadeOut;
    Animation fadeIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_up);
        ButterKnife.bind(this);
        setTitle(getString(R.string.lookUp));

    }



    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanStillageChanged(Editable text) {
        if (text.toString().equalsIgnoreCase("S00001")) {
            stillageLayoutLookUp.setVisibility(View.VISIBLE);
            stillageLayoutLookUp.startAnimation(fadeIn);
            linearLayoutLocation.setVisibility(View.VISIBLE);
            linearLayoutLocation.startAnimation(fadeIn);
            editTextScanStillage.setEnabled(false);

        }


    }


    public void imageButtonCloseClick(View view) {
        stillageLayoutLookUp.startAnimation(fadeOut);
        stillageLayoutLookUp.setVisibility(View.GONE);
        linearLayoutLocation.startAnimation(fadeOut);
        linearLayoutLocation.setVisibility(View.GONE);
        editTextScanStillage.setText("");
        editTextScanStillage.setEnabled(true);

    }
}
