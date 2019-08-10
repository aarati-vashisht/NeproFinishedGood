package com.neprofinishedgood.productionjournal.ui.main;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.fragment.app.Fragment;

import com.neprofinishedgood.R;
import com.neprofinishedgood.custom_views.CustomButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RouteCardFragment extends Fragment {
    @BindView(R.id.buttonAddMoreLine)
    CustomButton buttonAddMoreLine;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_route_card, container, false);

        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.buttonAddMoreLine)
    public void onButtonAddMoreLineClick(){
    }

}
