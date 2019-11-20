package com.neprofinishedgood.productionjournal.ui.main;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.neprofinishedgood.productionjournal.adapter.RouteCardAdapter;
import com.neprofinishedgood.productionjournal.adapter.SpinnerOperationAdapter;
import com.neprofinishedgood.productionjournal.model.ProductionJournalRouteDataInput;
import com.neprofinishedgood.productionjournal.model.RouteCardPicked;
import com.neprofinishedgood.utils.NetworkChangeReceiver;

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

    @BindView(R.id.recyclerViewRouteList)
    RecyclerView recyclerViewRouteList;

    @BindView(R.id.editTextDate)
    public AppCompatEditText editTextDate;

    @BindView(R.id.editTextQuantity)
    public AppCompatEditText editTextQuantity;

    @BindView(R.id.editTextHours)
    public AppCompatEditText editTextHours;

    @BindView(R.id.spinnerShift)
    public Spinner spinnerShift;

    @BindView(R.id.spinnerOperation)
    public Spinner spinnerOperation;

    @BindView(R.id.textViewResource)
    TextView textViewResource;

    @BindView(R.id.textViewResourceType)
    TextView textViewResourceType;

    private ArrayList<String> shiftList;
    private String shift = "", operationId, operationName, resource, resourceType, priority;
    public ArrayAdapter<String> arrayAdapter;
    public RouteCardAdapter adapter;

    public SpinnerOperationAdapter operationAdapter;
    static RouteCardFragment instance;

    public int updatePosition = -1;

    AlertDialog.Builder builder;

    View rootView;
    final SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");

    public static RouteCardFragment getInstance() {
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_route_card, container, false);

        ButterKnife.bind(this, rootView);
        setAdapter();
        instance = this;
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

    void setSpinnerItemData() {
        operationAdapter = new SpinnerOperationAdapter(getActivity(), R.layout.spinner_layout, ProductionJournal.getInstance().routingListDatumList);
        spinnerOperation.setAdapter(operationAdapter);
    }

    void setAdapter() {
        adapter = new RouteCardAdapter(ProductionJournal.getInstance().addedRoutingListDatumList);
        recyclerViewRouteList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewRouteList.setAdapter(adapter);
        recyclerViewRouteList.setHasFixedSize(true);

        Calendar c = Calendar.getInstance();
        String date = sdf.format(c.getTime());
        editTextDate.setText(date);

        setSpinnerShiftData();
        setSpinnerItemData();
    }

    @OnItemSelected(R.id.spinnerOperation)
    public void spinnerOperationSelected(Spinner spinner, int position) {
        resource = ProductionJournal.getInstance().routingListDatumList.get(position).getResource();
        resourceType = ProductionJournal.getInstance().routingListDatumList.get(position).getResourceType();
        operationId = operationAdapter.getItem(position).getOperationId();
        operationName = operationAdapter.getItem(position).getOperationName();
        priority = operationAdapter.getItem(position).getPriority();
        textViewResource.setText(resource);
        textViewResourceType.setText(resourceType);
    }

    @OnItemSelected(R.id.spinnerShift)
    public void spinnerShiftSelected(Spinner spinner, int position) {
        if (position > 0) {
            shift = shiftList.get(position);
        } else {
            shift = "";
        }
    }

    @OnClick(R.id.buttonAddMoreLine)
    public void onButtonAddMoreLineClick() {

        if (isValidated()) {

            boolean dataInserted = false;
            if (updatePosition >= 0) {
                try {
                    ProductionJournal.getInstance().addedRoutingListDatumList.get(updatePosition).setDate(editTextDate.getText().toString());
                    ProductionJournal.getInstance().addedRoutingListDatumList.get(updatePosition).setShift(shift);
                    ProductionJournal.getInstance().addedRoutingListDatumList.get(updatePosition).setOperationName(operationName);
                    ProductionJournal.getInstance().addedRoutingListDatumList.get(updatePosition).setOperationId(operationId);
                    ProductionJournal.getInstance().addedRoutingListDatumList.get(updatePosition).setQuantity(editTextQuantity.getText().toString());
                    ProductionJournal.getInstance().addedRoutingListDatumList.get(updatePosition).setHours(editTextHours.getText().toString());
                    ProductionJournal.getInstance().addedRoutingListDatumList.get(updatePosition).setResource(resource);
                    ProductionJournal.getInstance().addedRoutingListDatumList.get(updatePosition).setResourceType(resourceType);
                    ProductionJournal.getInstance().addedRoutingListDatumList.get(updatePosition).setPriority(priority);
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
                updatePosition = -1;
                dataInserted = true;
            }

            if (!dataInserted) {
                ProductionJournal.getInstance().addedRoutingListDatumList.add(new RouteCardPicked(shift, editTextDate.getText().toString(), operationName, operationId, resource, resourceType, editTextHours.getText().toString(), editTextQuantity.getText().toString(), priority));
                updatePosition = -1;
            }
            adapter.notifyDataSetChanged();
            clearInputs();
        }
    }

    public void clearInputs() {
        spinnerShift.setSelection(0);
        spinnerOperation.setSelection(0);
        Calendar c = Calendar.getInstance();
        String date = sdf.format(c.getTime());
        editTextDate.setText(date);
        editTextQuantity.setText("");
        editTextHours.setText("");
    }

    boolean isValidated() {
        if (spinnerOperation.getSelectedItemPosition() == 0) {
            TextView textView = (TextView) spinnerOperation.getSelectedView();
            textView.setError(getString(R.string.select_operation));
            textView.requestFocus();
            return false;
        }
        if (editTextQuantity.getText().toString().equals("") || editTextQuantity.getText().toString().equals(".")) {
            editTextQuantity.setError(getString(R.string.enter_quantity));
            editTextQuantity.requestFocus();
            return false;
        } else if (!editTextQuantity.getText().toString().equals("") || !editTextQuantity.getText().toString().equals(".")) {
            float qty = Float.parseFloat(editTextQuantity.getText().toString());
            if (qty <= 0) {
                editTextQuantity.setError(getString(R.string.enter_quantity));
                editTextQuantity.requestFocus();
                return false;
            }
        }
        if (editTextHours.getText().toString().equals("")) {
            editTextHours.setError(getString(R.string.enter_hours));
            editTextHours.requestFocus();
            return false;
        } else if (!editTextHours.getText().toString().equals("") || !editTextHours.getText().toString().equals(".")) {
            float qty = Float.parseFloat(editTextHours.getText().toString());
            if (qty <= 0) {
                editTextHours.setError(getString(R.string.enter_hours));
                editTextHours.requestFocus();
                return false;
            }
        }
        return true;
    }

    public void deleteAddedData(int position) {
        ProductionJournal.getInstance().addedRoutingListDatumList.remove(position);
        adapter.notifyDataSetChanged();
        clearInputs();
        updatePosition = -1;
    }

    @OnClick(R.id.buttonConfirm)
    public void onButtonSubmitClick() {
        if (ProductionJournal.getInstance().addedRoutingListDatumList.size() > 0) {
            showConfirmationAlert();
        } else {
            showErrorDialog("Route operation list is empty!");
        }
    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        ProductionJournal.getInstance().showCancelAlert(8);
    }

    public void showConfirmationAlert() {
        builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getString(R.string.production_journal_submit_confirmation));
        builder.setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (NetworkChangeReceiver.isInternetConnected(ProductionJournal.getInstance())) {
                            ProductionJournalRouteDataInput productionJournalRouteDataInput = new ProductionJournalRouteDataInput(
                                    ProductionJournal.getInstance().workOrderNo, ProductionJournal.getInstance().userId,
                                    ProductionJournal.getInstance().textViewItemId.getText().toString(),
                                    ProductionJournal.getInstance().addedRoutingListDatumList);

                            ProductionJournal.getInstance().showProgress(getActivity());
                            ProductionJournal.getInstance().iProductionJournalInterface.callSubmitProductionJournalRouteService(productionJournalRouteDataInput);
                            dialog.cancel();
                        } else {
                            ProductionJournal.getInstance().showSuccessDialog(getString(R.string.no_internet));
                        }
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void showErrorDialog(String message) {
        builder = new AlertDialog.Builder(ProductionJournal.getInstance());
        builder.setMessage(message);
        builder.setCancelable(false)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
