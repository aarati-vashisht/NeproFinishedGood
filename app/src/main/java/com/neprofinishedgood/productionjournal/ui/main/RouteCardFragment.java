package com.neprofinishedgood.productionjournal.ui.main;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import com.neprofinishedgood.R;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.productionjournal.ProductionJournal;
import com.neprofinishedgood.productionjournal.adapter.SpinnerItemAdapter;
import com.neprofinishedgood.productionjournal.adapter.SpinnerOperationAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class RouteCardFragment extends Fragment {
    @BindView(R.id.buttonAddMoreLine)
    CustomButton buttonAddMoreLine;

    @BindView(R.id.editTextDate)
    AppCompatEditText editTextDate;

    @BindView(R.id.spinnerShift)
    public Spinner spinnerShift;

    @BindView(R.id.spinnerOperation)
    public Spinner spinnerOperation;

    @BindView(R.id.textViewResource)
    TextView textViewResource;

    @BindView(R.id.textViewResourceType)
    TextView textViewResourceType;

    private ArrayList<String> shiftList;
    private String shift = "", operationId, operationName;
    public ArrayAdapter<String> arrayAdapter;

    public SpinnerOperationAdapter operationAdapter;


    View rootView;
    final SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_route_card, container, false);

        ButterKnife.bind(this, rootView);
        setAdapter();
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

    void setSpinnerShiftData() {
        shiftList = new ArrayList<>();
        shiftList.add("Select Shift");
        shiftList.add("A");
        shiftList.add("B");
        shiftList.add("C");
        arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, R.id.text1, shiftList);
        spinnerShift.setAdapter(arrayAdapter);
    }

    void setSpinnerItemData() {
        operationAdapter = new SpinnerOperationAdapter(getActivity(), R.layout.spinner_layout, ProductionJournal.getInstance().routingListDatumList);
        spinnerOperation.setAdapter(operationAdapter);
    }

    void setAdapter() {

        Calendar c = Calendar.getInstance();
        String date = sdf.format(c.getTime());
        editTextDate.setText(date);

        setSpinnerShiftData();
        setSpinnerItemData();
    }

    @OnItemSelected(R.id.spinnerOperation)
    public void spinnerOperationSelected(Spinner spinner, int position) {
        textViewResource.setText(ProductionJournal.getInstance().routingListDatumList.get(position).getResource());
        textViewResourceType.setText(ProductionJournal.getInstance().routingListDatumList.get(position).getResourceType());
        operationName = operationAdapter.getItem(position).getOperationName();
        operationId = operationAdapter.getItem(position).getOperationId();
    }

    @OnClick(R.id.buttonAddMoreLine)
    public void onButtonAddMoreLineClick(){
    }


}
