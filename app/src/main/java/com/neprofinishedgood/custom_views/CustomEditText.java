package com.neprofinishedgood.custom_views;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.neprofinishedgood.R;

public class CustomEditText extends AppCompatEditText {
    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackground(getResources().getDrawable(R.drawable.editext_drawable));
    }
}
