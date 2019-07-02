package com.neprofinishedgood.custom_views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.neprofinishedgood.R;

public class CustomToast {
    static Toast toast;

    public static void showToast(Context context, String text) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = new Toast(context);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custom_toast, null);
        TextView textViewTitle = view.findViewById(R.id.textViewTitle);
        textViewTitle.setText(text);
        toast.setView(view);
        toast.show();
    }


}
