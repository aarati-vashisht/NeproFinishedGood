package com.neprofinishedgood.custom_views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.neprofinishedgood.R;

public class CustomAlert {
    private static AlertDialog.Builder builder;

    public static void showCustomAlert(Context context) {
        builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getString(R.string.dialog_message));
        builder.setMessage("Do you want to close this application ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
