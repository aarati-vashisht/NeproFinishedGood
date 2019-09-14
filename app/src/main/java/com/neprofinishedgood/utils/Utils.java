package com.neprofinishedgood.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.view.View;
import android.view.WindowManager;

public class Utils {

    public static void hideSoftKeyboard(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static void animateFadeOut(View view, long duration) {
        view.animate()
                .alpha(0.0f)
                .setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        view.setVisibility(View.GONE);
                    }
                });
    }

    public static void animateFadeIn(View view, long duration) {
        view.setAlpha(0.0f);
        view.animate()
                .alpha(1.0f)
                .setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        view.setVisibility(View.VISIBLE);
                    }
                });
    }

    public static boolean isStringIsFloatNum(String input)
    {
        try
        {
            Float.parseFloat(input);
        }
        catch(NumberFormatException ex)
        {
            return false;
        }
        return true;
    }
}
