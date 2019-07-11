package com.neprofinishedgood.utils;

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

}
