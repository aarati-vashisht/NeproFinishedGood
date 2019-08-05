package com.neprofinishedgood.utils;

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

    @BindView(R.id.linearLayoutLocation)
    public LinearLayout linearLayoutLocation;

    @BindView(R.id.textViewWorkOrderNumber)
    public TextView textViewWorkOrderNumber;

    @BindView(R.id.linearLayoutWorkOrderNo)
    public LinearLayout linearLayoutWorkOrderNo;


}
