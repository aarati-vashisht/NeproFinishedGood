package com.neprofinishedgood.returnstillage;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalSpinner;
import com.neprofinishedgood.counting.model.StillageDatum;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.putaway.Adapter.SpinnerAdapter;
import com.neprofinishedgood.utils.StillageLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class ReturnStillageActivity extends BaseActivity {

    @BindView(R.id.relativeLayoutScanDetail)
    RelativeLayout relativeLayoutScanDetail;

    @BindView(R.id.editTextScanLocation)
    EditText editTextScanLocation;

    @BindView(R.id.spinnerAisle)
    Spinner spinnerAisle;

    @BindView(R.id.spinnerRack)
    Spinner spinnerRack;

    @BindView(R.id.spinnerBin)
    Spinner spinnerBin;

    @BindView(R.id.buttonDrop)
    Button buttonDrop;

    @BindView(R.id.editTextScanStillage)
    EditText editTextScanStillage;

    @BindView(R.id.stillageDetail)
    View stillageDetail;

    StillageLayout stillageLayout;

    Animation fadeOut;
    Animation fadeIn;

    ArrayList<UniversalSpinner> aisleList;
    ArrayList<UniversalSpinner> rackList;
    ArrayList<UniversalSpinner> binList;

    StillageDatum stillageDatum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_stillage);

        ButterKnife.bind(this);
        setTitle(getString(R.string.return_stillage));
        initData();
    }

    void initData() {
        stillageLayout = new StillageLayout();
        ButterKnife.bind(stillageLayout, stillageDetail);

        fadeOut = AnimationUtils.loadAnimation(ReturnStillageActivity.this, R.anim.animate_fade_out);
        fadeIn = AnimationUtils.loadAnimation(ReturnStillageActivity.this, R.anim.animate_fade_in);
    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanStillageChanged(Editable text) {
        if (text.toString().equalsIgnoreCase("S000001")) {
            relativeLayoutScanDetail.setVisibility(View.VISIBLE);
            relativeLayoutScanDetail.setAnimation(fadeIn);
            setData();
            editTextScanLocation.requestFocus();
        } else {
            relativeLayoutScanDetail.setVisibility(View.GONE);
            relativeLayoutScanDetail.setAnimation(fadeOut);
        }
    }

    @OnTextChanged(value = R.id.editTextScanLocation, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanLocationChanged(Editable text) {
        String editQty = text.toString();

        if (editQty.equalsIgnoreCase("ABC")) {
            setSpinnerAisleData();
            setSpinnerRackData();
            setSpinnerBinData();
            buttonDrop.setEnabled(true);
        } else {
            buttonDrop.setEnabled(false);
        }
    }

    void setData() {
//        Gson gson = new Gson();
//        StillageDatum stillageDatum = gson.fromJson(JsonString, StillageDatum.class);
        stillageDatum = new StillageDatum();
        stillageDatum.setItem("1");
        stillageDatum.setName("S000001");
        stillageDatum.setNumber("S000001");
        stillageDatum.setQuantity("100");
        stillageDatum.setStdQuantity("100");
        stillageDatum.setStillageId("");

        stillageLayout.textViewitem.setText(stillageDatum.getItem());
        stillageLayout.textViewName.setText(stillageDatum.getName());
        stillageLayout.textViewNumber.setText(stillageDatum.getNumber());
        stillageLayout.textViewQuantity.setText(stillageDatum.getQuantity());
        stillageLayout.textViewStdQuatity.setText(stillageDatum.getStdQuantity());

        stillageLayout.cardView.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));

    }

    @OnClick(R.id.buttonDrop)
    public void onButtonDropClick() {
        if (isValidated()) {
            CustomToast.showToast(ReturnStillageActivity.this, getResources().getString(R.string.item_droped_successfully));
            finish();
        }
    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        editTextScanStillage.setText("");
        editTextScanLocation.setText("");
        relativeLayoutScanDetail.setVisibility(View.GONE);
        relativeLayoutScanDetail.setAnimation(fadeOut);
        stillageDatum = new StillageDatum();
        clearAllSpinnerData();
    }

    void setSpinnerAisleData() {
        aisleList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            aisleList.add(new UniversalSpinner("Aisle " + i, i + ""));
        }
        aisleList.add(0, new UniversalSpinner("Select Aisle", "0"));
        SpinnerAdapter aisleListAdapter = new SpinnerAdapter(ReturnStillageActivity.this, R.layout.spinner_layout, aisleList);
        spinnerAisle.setAdapter(aisleListAdapter);
    }

    void setSpinnerRackData() {
        rackList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            rackList.add(new UniversalSpinner("Rack " + i, i + ""));
        }
        rackList.add(0, new UniversalSpinner("Select Rack", "0"));
        SpinnerAdapter rackListAdapter = new SpinnerAdapter(ReturnStillageActivity.this, R.layout.spinner_layout, rackList);
        spinnerRack.setAdapter(rackListAdapter);
    }

    void setSpinnerBinData() {
        binList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            binList.add(new UniversalSpinner("Bin " + i, i + ""));
        }
        binList.add(0, new UniversalSpinner("Select Bin", "0"));
        SpinnerAdapter binListAdapter = new SpinnerAdapter(ReturnStillageActivity.this, R.layout.spinner_layout, binList);
        spinnerBin.setAdapter(binListAdapter);
    }

    void clearAllSpinnerData() {
        spinnerAisle.setAdapter(null);
        spinnerRack.setAdapter(null);
        spinnerBin.setAdapter(null);
    }

    boolean isValidated() {
        if (editTextScanLocation.getText().toString().equals("")) {
            editTextScanLocation.setError(getResources().getString(R.string.enter_scan_location));
            editTextScanLocation.requestFocus();
            return false;
        }
        if (spinnerAisle.getSelectedItemPosition() == 0) {
            TextView textView = (TextView) spinnerAisle.getSelectedView();
            textView.setError(getString(R.string.select_aisle));
            textView.requestFocus();
            return false;
        }

        if (spinnerRack.getSelectedItemPosition() == 0) {
            TextView textView = (TextView) spinnerRack.getSelectedView();
            textView.setError(getString(R.string.select_rack));
            textView.requestFocus();
            return false;
        }
        if (spinnerBin.getSelectedItemPosition() == 0) {
            TextView textView = (TextView) spinnerBin.getSelectedView();
            textView.setError(getString(R.string.select_bin));
            textView.requestFocus();
            return false;
        }
        return true;
    }
}
