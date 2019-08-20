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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neprofinishedgood.R;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.productionjournal.ProductionJournal;
import com.neprofinishedgood.productionjournal.adapter.PickingListAdapter;
import com.neprofinishedgood.productionjournal.adapter.SpinnerItemAdapter;
import com.neprofinishedgood.productionjournal.model.ItemPicked;
import com.neprofinishedgood.productionjournal.model.PickingListSearchResponse;
import com.neprofinishedgood.productionjournal.presenter.IPickingListInterface;
import com.neprofinishedgood.productionjournal.presenter.IPickingListPresenter;
import com.neprofinishedgood.productionjournal.presenter.IPickingListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class PickingListFragment extends Fragment implements IPickingListView {

    @BindView(R.id.recyclerViewPickingList)
    RecyclerView recyclerViewPickingList;

    @BindView(R.id.editTextQuantity)
    public AppCompatEditText editTextQuantity;

    @BindView(R.id.editTextDate)
    public AppCompatEditText editTextDate;

    @BindView(R.id.spinnerItem)
    public Spinner spinnerItem;

    @BindView(R.id.spinnerShift)
    public Spinner spinnerShift;

    @BindView(R.id.buttonAddMoreLine)
    CustomButton buttonAddMoreLine;

    @BindView(R.id.textViewUnit)
    TextView textViewUnit;

    public PickingListAdapter adapter;
    View rootView;

    String itemName, itemId, workOrderNo, userId;

    private ArrayList<String> shiftList;
    public ArrayAdapter<String> arrayAdapter;

    final SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");

    IPickingListInterface iPickingListInterface;

    static PickingListFragment instance;
    private String shift = "", itemQty;
    public SpinnerItemAdapter itemAdapter;


    public static PickingListFragment getInstance() {
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_picking_list, container, false);
        ButterKnife.bind(this, rootView);
        workOrderNo = ProductionJournal.getInstance().workOrderNo;
        userId = ProductionJournal.getInstance().userId;
        iPickingListInterface = new IPickingListPresenter(this, getActivity());
        setAdapter();
        instance = this;
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

//    private void setData(PickingListSearchResponse body) {
//        editTextQuantity.setEnabled(true);
//        itemName = body.getItemName();
//        itemId = body.getItemId();
//    }

    void setAdapter() {

        adapter = new PickingListAdapter(ProductionJournal.getInstance().addedPickingListDatumList);
        recyclerViewPickingList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewPickingList.setAdapter(adapter);
        recyclerViewPickingList.setHasFixedSize(true);

        Calendar c = Calendar.getInstance();
        String date = sdf.format(c.getTime());
        editTextDate.setText(date);

        setSpinnerShiftData();
        setSpinnerItemData();
    }

    @Override
    public void onPickingSearchFailure(String message) {

    }

    @Override
    public void onPickingSearchSuccess(PickingListSearchResponse body) {

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
        }, mYear, mMonth, mDay);
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

    @OnItemSelected(R.id.spinnerShift)
    public void spinnerShiftSelected(Spinner spinner, int position) {
        shift = shiftList.get(position);
    }

    @OnClick(R.id.buttonAddMoreLine)
    public void onButtonAddMoreLineClick() {
        if (isValidated()) {
            ProductionJournal.getInstance().addedPickingListDatumList.add(new ItemPicked(shift, editTextDate.getText().toString(), itemName, itemId, editTextQuantity.getText().toString()));
            adapter.notifyDataSetChanged();
            clearInputs();
        }
    }

    public void clearInputs() {
        spinnerShift.setSelection(0);
        spinnerItem.setSelection(0);
        Calendar c = Calendar.getInstance();
        String date = sdf.format(c.getTime());
        editTextDate.setText(date);
        editTextQuantity.setText("");
    }

    public void clearPickingList() {
        ProductionJournal.getInstance().addedPickingListDatumList = new ArrayList<>();
        adapter.notifyDataSetChanged();
    }

    void setSpinnerItemData() {
        itemAdapter = new SpinnerItemAdapter(getActivity(), R.layout.spinner_layout, ProductionJournal.getInstance().pickingListDatumList);
        spinnerItem.setAdapter(itemAdapter);
    }

    @OnItemSelected(R.id.spinnerItem)
    public void spinnerItemSelected(Spinner spinner, int position) {
        textViewUnit.setText(ProductionJournal.getInstance().pickingListDatumList.get(position).getUnit());
        itemName = itemAdapter.getItem(position).getItemName();
        itemId = itemAdapter.getItem(position).getItemId();
    }

    boolean isValidated() {
        if (spinnerItem.getSelectedItemPosition() == 0) {
            TextView textView = (TextView) spinnerItem.getSelectedView();
            textView.setError(getString(R.string.search_item));
            textView.requestFocus();
            return false;
        }
        if (editTextQuantity.getText().toString().equals("")) {
            editTextQuantity.setError(getString(R.string.enter_quantity));
            editTextQuantity.requestFocus();
            return false;
        }
        return true;
    }

}
