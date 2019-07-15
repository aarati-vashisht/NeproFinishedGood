package com.neprofinishedgood.transferstillage;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalSpinner;
import com.neprofinishedgood.plannedandunplannedmove.Adapter.SpinnerAdapter;
import com.neprofinishedgood.raf.model.StillageDatum;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.utils.StillageLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class TransferStillageActivity extends BaseActivity {

    @BindView(R.id.relativeLayoutScanDetail)
    RelativeLayout relativeLayoutScanDetail;


    @BindView(R.id.buttonTransfer)
    CustomButton buttonTransfer;

    @BindView(R.id.editTextScanStillage)
    AppCompatEditText editTextScanStillage;

    @BindView(R.id.stillageDetail)
    View stillageDetail;

    @BindView(R.id.spinnerWarehouse)
    Spinner spinnerWarehouse;

    StillageLayout stillageLayout;

    Animation fadeOut;
    Animation fadeIn;

    ArrayList<UniversalSpinner> aisleList;
    ArrayList<UniversalSpinner> rackList;
    ArrayList<UniversalSpinner> binList;

    StillageDatum stillageDatum;
    private ArrayList<UniversalSpinner> warehouseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_stillage);

        ButterKnife.bind(this);
        setTitle(getString(R.string.transfer_stillage));
        initData();
    }

    void initData() {
        stillageLayout = new StillageLayout();
        ButterKnife.bind(stillageLayout, stillageDetail);

        fadeOut = AnimationUtils.loadAnimation(TransferStillageActivity.this, R.anim.animate_fade_out);
        fadeIn = AnimationUtils.loadAnimation(TransferStillageActivity.this, R.anim.animate_fade_in);
    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanStillageChanged(Editable text) {
        if (text.toString().equalsIgnoreCase("S00001")) {
            relativeLayoutScanDetail.setVisibility(View.VISIBLE);
            relativeLayoutScanDetail.setAnimation(fadeIn);
            setData();
            editTextScanStillage.setEnabled(false);
        } else {
            relativeLayoutScanDetail.setVisibility(View.GONE);
            relativeLayoutScanDetail.setAnimation(fadeOut);
        }
    }


    void setData() {
//        Gson gson = new Gson();
//        LoadingPlanDatum stillageDatum = gson.fromJson(JsonString, LoadingPlanDatum.class);
        stillageDatum = new StillageDatum();
        stillageDatum.setItem("1");
        stillageDatum.setName("S00001");
        stillageDatum.setNumber("S00001");
        stillageDatum.setQuantity("100");
        stillageDatum.setStdQuantity("100");
        stillageDatum.setStillageId("");

        stillageLayout.textViewitem.setText(stillageDatum.getItem());
        stillageLayout.textViewNumber.setText(stillageDatum.getNumber());
        stillageLayout.textViewQuantity.setText(stillageDatum.getQuantity());
        stillageLayout.textViewStdQuantity.setText(stillageDatum.getStdQuantity());

        warehouseList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            warehouseList.add(new UniversalSpinner("Warehouse " + i, i + ""));
        }
        warehouseList.add(0, new UniversalSpinner("Select Warehouse", "0"));
        SpinnerAdapter reasonListAdapter = new SpinnerAdapter(TransferStillageActivity.this, R.layout.spinner_layout, warehouseList);
        spinnerWarehouse.setAdapter(reasonListAdapter);
    }

    @OnClick(R.id.buttonTransfer)
    public void onButtonDropClick() {
        if (spinnerWarehouse.getSelectedItemPosition() > 0) {
            CustomToast.showToast(TransferStillageActivity.this, getResources().getString(R.string.item_transferred_successfully));
            finish();
        }else {
            TextView textView = (TextView) spinnerWarehouse.getSelectedView();
            textView.setError(getString(R.string.select_warehouse));
            textView.requestFocus();
        }

    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        editTextScanStillage.setText("");
        editTextScanStillage.setEnabled(true);
        relativeLayoutScanDetail.setVisibility(View.GONE);
        relativeLayoutScanDetail.setAnimation(fadeOut);
        stillageDatum = new StillageDatum();
    }


}
