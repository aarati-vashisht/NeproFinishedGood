package com.neprofinishedgood.plannedandunplannedmove;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.plannedandunplannedmove.Adapter.MoveAdapter;
import com.neprofinishedgood.plannedandunplannedmove.Adapter.SpinnerAdapter;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.UpdateMoveLocationInput;
import com.neprofinishedgood.plannedandunplannedmove.presenter.IMovePresenter;
import com.neprofinishedgood.plannedandunplannedmove.presenter.IMoveView;
import com.neprofinishedgood.utils.StillageLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MoveStillageActivity extends BaseActivity implements IMoveView {
    @BindView(R.id.buttonConfirm)
    AppCompatButton buttonPutAway;
    @BindView(R.id.buttonCancel)
    AppCompatButton buttonCancel;
    @BindView(R.id.linearLayoutScanDetail)
    LinearLayout linearLayoutScanDetail;
    @BindView(R.id.linearLayoutPutAwayLocation)
    LinearLayout linearLayoutPutAwayLocation;
    @BindView(R.id.linearLayoutAssignedLocation)
    LinearLayout linearLayoutAssignedLocation;
    @BindView(R.id.editTextScanStillage)
    AppCompatEditText editTextScanStillage;
    @BindView(R.id.textViewScanAisle)
    AppCompatTextView textViewScanAisle;
    @BindView(R.id.textViewAssignedLocation)
    AppCompatTextView textViewAssignedLocation;
    @BindView(R.id.editTextDropLocation)
    EditText editTextDropLocation;
    @BindView(R.id.textViewtRack)
    AppCompatTextView textViewtRack;
    @BindView(R.id.textViewBin)
    AppCompatTextView textViewBin;
    @BindView(R.id.spinnerAisle)
    Spinner spinnerAisle;
    @BindView(R.id.spinnerRack)
    Spinner spinnerRack;
    @BindView(R.id.spinnerBin)
    Spinner spinnerBin;
    @BindView(R.id.recyclerViewStillage)
    RecyclerView recyclerViewStillage;
    @BindView(R.id.stillageDetail)
    View stillageDetail;

    StillageLayout stillageLayout;

    private IMovePresenter movePresenter;
    long delay = 1500;
    long scanStillageLastTexxt = 0;
    long dropLocationLastText = 0;
    private MoveAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_stillage);
        ButterKnife.bind(this);
        setTitle(getString(R.string.move));
        movePresenter = new IMovePresenter(this);
    }

    //data initialization
    void initData() {
        stillageLayout = new StillageLayout();
        ButterKnife.bind(stillageLayout, stillageDetail);
        textViewScanAisle.setVisibility(View.GONE);
        textViewBin.setVisibility(View.GONE);
        textViewtRack.setVisibility(View.GONE);
        spinnerAisle.setVisibility(View.VISIBLE);
        spinnerBin.setVisibility(View.VISIBLE);
        spinnerRack.setVisibility(View.VISIBLE);

        setSpinnerAisleData();
        setSpinnerRackData();
        setSpinnerBinData();
    }


    @OnClick(R.id.buttonConfirm)
    public void onButtonConfirmClick() {
        if (isValidated()) {
            UpdateMoveLocationInput updateMoveLocationInput;
            if (linearLayoutPutAwayLocation.getVisibility() != View.VISIBLE) {
                updateMoveLocationInput = new UpdateMoveLocationInput(stillageLayout.textViewNumber.getText().toString(), "", userId);
            } else {
                updateMoveLocationInput = new UpdateMoveLocationInput(stillageLayout.textViewNumber.getText().toString(), editTextDropLocation.getText().toString(), userId);
            }
            movePresenter.callMoveServcie(updateMoveLocationInput);
        }

    }

    @OnTextChanged(value = R.id.editTextDropLocation, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void oneditTextDropLocationChanged(Editable text) {
        if (!text.toString().trim().equals("")) {
            dropLocationhandler.postDelayed(dropLocationRunnable, delay);
        }

    }

    @OnTextChanged(value = R.id.editTextDropLocation, callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void onEditTextDropLocationTEXTCHANGED(Editable text) {
        dropLocationhandler.removeCallbacks(dropLocationRunnable);

    }

    //for call service on text change
    Handler dropLocationhandler = new Handler();
    private Runnable dropLocationRunnable = new Runnable() {
        public void run() {
            // showProgress(MoveStillageActivity.this);
            if (System.currentTimeMillis() > (dropLocationLastText + delay - 500)) {
                //call service to check location in database
                textViewScanAisle.setVisibility(View.VISIBLE);
                textViewBin.setVisibility(View.VISIBLE);
                textViewtRack.setVisibility(View.VISIBLE);
                spinnerAisle.setVisibility(View.GONE);
                spinnerBin.setVisibility(View.GONE);
                spinnerRack.setVisibility(View.GONE);
            }
        }
    };

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        linearLayoutScanDetail.setVisibility(View.GONE);
        linearLayoutPutAwayLocation.setVisibility(View.GONE);
        if (linearLayoutPutAwayLocation.getVisibility() == View.VISIBLE)
            linearLayoutPutAwayLocation.startAnimation(fadeOut);
        if (linearLayoutScanDetail.getVisibility() == View.VISIBLE)
            linearLayoutScanDetail.startAnimation(fadeOut);

        editTextScanStillage.setText("");
        editTextScanStillage.setEnabled(true);
        buttonPutAway.setVisibility(View.GONE);
        buttonCancel.setVisibility(View.GONE);

    }

    void setSpinnerAisleData() {
        SpinnerAdapter aisleListAdapter = new SpinnerAdapter(MoveStillageActivity.this, R.layout.spinner_layout, aisleList);
        spinnerAisle.setAdapter(aisleListAdapter);
    }

    void setSpinnerRackData() {
        SpinnerAdapter rackListAdapter = new SpinnerAdapter(MoveStillageActivity.this, R.layout.spinner_layout, rackList);
        spinnerRack.setAdapter(rackListAdapter);
    }

    void setSpinnerBinData() {
        SpinnerAdapter binListAdapter = new SpinnerAdapter(MoveStillageActivity.this, R.layout.spinner_layout, binList);
        spinnerBin.setAdapter(binListAdapter);
    }




    @Override
    public void onUpdateMoveSuccess(UniversalResponse response) {
        hideProgress();
        if (response.getStatus().equals(getString(R.string.success))) {
            CustomToast.showToast(this, response.getMessage());
            onButtonCancelClick();
            clearAllSpinnerData();
        } else {
            CustomToast.showToast(this, getString(R.string.something_went_wrong_please_try_again));
        }
    }

    @Override
    public void onUpdateMoveFailure() {
        hideProgress();
        CustomToast.showToast(this, getString(R.string.something_went_wrong_please_try_again));
    }

    //set response data
    void setData(MoveResponse body) {
        stillageLayout.textViewitem.setText(body.getItemId());
        stillageLayout.textViewQuantity.setText(body.getStandardQty() + "");
        stillageLayout.textViewitemDesc.setText(body.getDescription());
        stillageLayout.textViewStdQuantity.setText(body.getItemStdQty() + "");
        stillageLayout.textViewNumber.setText(body.getStickerID());
        if (body.getAssignedLocation().equals("")) {
            linearLayoutAssignedLocation.setVisibility(View.GONE);
        } else {
            linearLayoutAssignedLocation.setVisibility(View.GONE);
            textViewAssignedLocation.setText(body.getAssignedLocation());

        }
    }

    //check Validation
    boolean isValidated() {
        if (linearLayoutPutAwayLocation.getVisibility() == View.VISIBLE) {
            if (spinnerAisle.getSelectedItemPosition() != 0 || spinnerRack.getSelectedItemPosition() == 0 || spinnerBin.getSelectedItemPosition() == 0) {
                return true;
            } else {
                TextView textView = (TextView) spinnerAisle.getSelectedView();
                textView.setError(getString(R.string.select_aisle));
                textView.requestFocus();
                return false;
            }
        } else {
            if (editTextScanStillage.getText().toString().equals("")) {
                editTextScanStillage.setError(getResources().getString(R.string.enter_stillage_number));
                editTextScanStillage.requestFocus();
                return false;
            }
            return true;
        }
    }

    void clearAllSpinnerData() {
        spinnerAisle.setAdapter(null);
        spinnerRack.setAdapter(null);
        spinnerBin.setAdapter(null);
        editTextDropLocation.setText("");
    }

    public void imageButtonCloseClick(View view) {
        textViewScanAisle.setVisibility(View.GONE);
        textViewBin.setVisibility(View.GONE);
        textViewtRack.setVisibility(View.GONE);
        spinnerAisle.setVisibility(View.VISIBLE);
        spinnerBin.setVisibility(View.VISIBLE);
        spinnerRack.setVisibility(View.VISIBLE);
        editTextDropLocation.setText("");
    }
}
