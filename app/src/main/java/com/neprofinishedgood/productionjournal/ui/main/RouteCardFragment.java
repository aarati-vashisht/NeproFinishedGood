package com.neprofinishedgood.productionjournal.ui.main;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import com.neprofinishedgood.R;
import com.neprofinishedgood.custom_views.CustomButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RouteCardFragment extends Fragment {
    @BindView(R.id.buttonAddMoreLine)
    CustomButton buttonAddMoreLine;

    @BindView(R.id.editTextDate)
    AppCompatEditText editTextDate;

    View rootView;
    final SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_route_card, container, false);

        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.editTextDate)
    public void onEditTextDateClick() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.DAY_OF_MONTH, day);
                c.set(Calendar.MONTH, month);
                String date = sdf.format(c.getTime());
                editTextDate.setText(date);
            }
        },mYear,mMonth,mDay);
        datePickerDialog.show();
    }

    @OnClick(R.id.buttonAddMoreLine)
    public void onButtonAddMoreLineClick(){
    }

}
