package com.neprofinishedgood.raf;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;
import com.neprofinishedgood.raf.model.RafInput;
import com.neprofinishedgood.raf.presenter.IRAFInterface;
import com.neprofinishedgood.raf.presenter.IRAFPresenter;
import com.neprofinishedgood.raf.presenter.IRAFView;
import com.neprofinishedgood.utils.StillageLayout;
import com.neprofinishedgood.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;

public class RAFActivity extends BaseActivity implements IRAFView {
    @BindView(R.id.editTextScanStillage)
    AppCompatEditText editTextScanStillage;
    @BindView(R.id.editTextQuantity)
    AppCompatEditText editTextQuantity;
    @BindView(R.id.stillageDetail)
    View stillageDetail;
    @BindView(R.id.linearLayoutScanDetail)
    LinearLayout linearLayoutScanDetail;
    @BindView(R.id.spinnerShift)
    Spinner spinnerShift;

    long scanStillageLastTexxt = 0;
    long delay = 1500;
    IRAFInterface irafInterface;
    StillageLayout stillageLayout;
    private ArrayList<String> shiftList;
    private String shift = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raf);
        ButterKnife.bind(this);
        stillageLayout = new StillageLayout();
        ButterKnife.bind(stillageLayout, stillageDetail);

        Utils.hideSoftKeyboard(this);
        setTitle(getString(R.string.reportasfinished));
        irafInterface = new IRAFPresenter(this);
    }

    void setSpinnerShiftData() {
        shiftList = new ArrayList<>();
        shiftList.add("Select Shift");
        shiftList.add("A");
        shiftList.add("B");
        shiftList.add("C");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.text1, shiftList);
        spinnerShift.setAdapter(arrayAdapter);

    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanStillageChanged(Editable text) {
        if (!text.toString().trim().equals("")) {
            scanStillagehandler.postDelayed(stillageRunnable, delay);
        }

    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void onEditTextScanStillageTEXTCHANGED(Editable text) {
        scanStillagehandler.removeCallbacks(stillageRunnable);

    }

    //for call service on text change
    Handler scanStillagehandler = new Handler();
    private Runnable stillageRunnable = new Runnable() {
        public void run() {
            showProgress(RAFActivity.this);
            if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
                irafInterface.callScanStillageService(new MoveInput(editTextScanStillage.getText().toString().trim(), userId));
            }
        }
    };


    @Override
    public void onFailure(String message) {
        hideProgress();
        CustomToast.showToast(this, message);
    }

    @Override
    public void onSuccess(ScanStillageResponse body) {
        hideProgress();
        // initData();
        if (body.getStatus().equals(getResources().getString(R.string.success))) {
            setData(body);
        } else {
            CustomToast.showToast(getApplicationContext(), body.getMessage());
            editTextScanStillage.setText("");
        }
    }

    @Override
    public void onUpdateRAFFailure(String message) {
        hideProgress();
        CustomToast.showToast(this, message);
    }

    @Override
    public void onUpdateRAFSuccess(UniversalResponse body) {
        hideProgress();
        CustomToast.showToast(this, body.getMessage());
        linearLayoutScanDetail.setVisibility(View.GONE);
        editTextScanStillage.setEnabled(true);
        editTextScanStillage.setText("");
    }

    private void setData(ScanStillageResponse body) {
        setSpinnerShiftData();
        linearLayoutScanDetail.setVisibility(View.VISIBLE);
        editTextScanStillage.setEnabled(false);
        stillageLayout.textViewitem.setText(body.getItemId());
        stillageLayout.textViewQuantity.setText(body.getStandardQty() + "");
        stillageLayout.textViewitemDesc.setText(body.getDescription());
        stillageLayout.textViewStdQuantity.setText(body.getItemStdQty() + "");
        stillageLayout.textViewNumber.setText(body.getStickerID());
        editTextQuantity.setText(body.getStandardQty() + "");
        editTextQuantity.setSelection(body.getStandardQty().toString().length());
        editTextQuantity.requestFocus();
    }

    @OnItemSelected(R.id.spinnerShift)
    public void spinnerBinSelected(Spinner spinner, int position) {
        shift = shiftList.get(position);
    }

    @OnClick(R.id.buttonConfirm)
    public void onButtonConfirmClick() {
        if (spinnerShift.getSelectedItemPosition() == 0) {
            TextView textView = (TextView) spinnerShift.getSelectedView();
            textView.setError(getString(R.string.select_shift));
        } else {
            showProgress(this);
            RafInput rafInput = new RafInput(editTextScanStillage.getText().toString().trim(), userId, shift, editTextQuantity.getText().toString().trim());
            irafInterface.callRAFServcie(rafInput);
        }

    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        linearLayoutScanDetail.setVisibility(View.GONE);
        editTextScanStillage.setEnabled(true);
        editTextScanStillage.setText("");
    }
}
