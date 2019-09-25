package com.neprofinishedgood.utils;

import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.neprofinishedgood.R;

import butterknife.BindView;

public class StillageLayout {

    @BindView(R.id.cardView)
    public CardView cardView;


    @BindView(R.id.textViewitem)
    public TextView textViewitem;

    @BindView(R.id.textViewNumber)
    public TextView textViewNumber;

    @BindView(R.id.textViewQuantity)
    public TextView textViewQuantity;

    @BindView(R.id.textViewStdQuatity)
    public TextView textViewStdQuantity;

    @BindView(R.id.textViewitemDesc)
    public TextView textViewitemDesc;

    @BindView(R.id.textViewLocation)
    public TextView textViewLocation;

    @BindView(R.id.textViewWarehouse)
    public TextView textViewWarehouse;

    @BindView(R.id.linearLayoutLocation)
    public LinearLayout linearLayoutLocation;

    @BindView(R.id.linearLayoutWarehouse)
    public LinearLayout linearLayoutWarehouse;

    @BindView(R.id.textViewWorkOrderNumber)
    public TextView textViewWorkOrderNumber;

    @BindView(R.id.linearLayoutWorkOrderNo)
    public LinearLayout linearLayoutWorkOrderNo;

    @BindView(R.id.checkboxRaf)
    public CheckBox checkboxRaf;

    @BindView(R.id.checkboxQcHold)
    public CheckBox checkboxQcHold;

    @BindView(R.id.linearLayoutRafCheckbox)
    public LinearLayout linearLayoutRafCheckbox;


}
