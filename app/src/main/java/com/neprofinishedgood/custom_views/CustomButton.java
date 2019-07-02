package com.neprofinishedgood.custom_views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import com.neprofinishedgood.R;

public class CustomButton extends AppCompatButton {
    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackground(getResources().getDrawable(R.drawable.button_ripple_effect));
        if (isEnabled() || !isPressed()) {
            setBackground(getResources().getDrawable(R.drawable.button_enable));
        } else if (!isEnabled() || isPressed()) {
            setBackground(getResources().getDrawable(R.drawable.button_disable));
        }
    }
}
