package com.neprofinishedgood.lookup;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.lookup.presenter.ILookUpInterface;
import com.neprofinishedgood.lookup.presenter.ILookUpView;
import com.neprofinishedgood.lookup.presenter.LookUpPresenter;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.utils.NetworkChangeReceiver;
import com.neprofinishedgood.utils.StillageLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class LookUpActivity extends BaseActivity implements ILookUpView {

    @BindView(R.id.editTextScanStillage)
    AppCompatEditText editTextScanStillage;

    @BindView(R.id.stillageLayoutLookUp)
    View stillageLayoutLookUp;

    long delay = 1000;
    long scanStillageLastTexxt = 0;
    ILookUpInterface iLookUpInterface;
    StillageLayout stillageLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_up);
        ButterKnife.bind(this);
        stillageLayout = new StillageLayout();
        ButterKnife.bind(stillageLayout, stillageLayoutLookUp);
        setTitle(getString(R.string.lookUp));
        editTextScanStillage.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        iLookUpInterface = new LookUpPresenter(this, this);

    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanStillageChanged(Editable text) {
        if (!text.toString().trim().equals("")) {
//            scanStillagehandler.postDelayed(stillageRunnable, delay);
            if (text.toString().trim().length() == scanStillageLength) {
                if (NetworkChangeReceiver.isInternetConnected(LookUpActivity.this)) {
                    showProgress(LookUpActivity.this);
                    iLookUpInterface.callScanStillageService(new MoveInput(editTextScanStillage.getText().toString().trim(), userId));
                } else {
                    showSuccessDialog(getString(R.string.no_internet));
//                    CustomToast.showToast(LookUpActivity.this, getString(R.string.no_internet));
                }
            }
        }

    }

//    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.TEXT_CHANGED)
//    public void onEditTextScanStillageTEXTCHANGED(Editable text) {
//        scanStillagehandler.removeCallbacks(stillageRunnable);
//
//    }
//
//    //for call service on text change
//    Handler scanStillagehandler = new Handler();
//    private Runnable stillageRunnable = new Runnable() {
//        public void run() {
//            if (NetworkChangeReceiver.isInternetConnected(LookUpActivity.this)) {
//                showProgress(LookUpActivity.this);
//                if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
//                    iLookUpInterface.callScanStillageService(new AisleInput(editTextScanStillage.getText().toString().trim(), userId));
//                }
//            } else {
//                CustomToast.showToast(LookUpActivity.this, getString(R.string.no_internet));
//            }
//        }
//    };


    public void imageButtonCloseClick(View view) {
        stillageLayoutLookUp.startAnimation(fadeOut);
        stillageLayoutLookUp.setVisibility(View.GONE);
        stillageLayout.linearLayoutLocation.startAnimation(fadeOut);
        stillageLayout.linearLayoutLocation.setVisibility(View.GONE);
        editTextScanStillage.setText("");
        editTextScanStillage.setEnabled(true);

    }

    @Override
    public void onFailure(String message) {
        hideProgress();
        showSuccessDialog(message);
//        CustomToast.showToast(this, message);
    }

    @Override
    public void onSuccess(ScanStillageResponse body) {
        hideProgress();
        if (body.getStatus().equalsIgnoreCase(getString(R.string.success))) {
            if (body.getStandardQty() > 0) {
                setData(body);
                editTextScanStillage.setEnabled(false);
            }
            else{
                showSuccessDialog(getResources().getString(R.string.stillage_discarded));
                editTextScanStillage.setText("");
            }

        } else {
            showSuccessDialog(body.getMessage());
//            CustomToast.showToast(getApplicationContext(), body.getMessage());
            editTextScanStillage.setText("");
        }
    }

    void setData(ScanStillageResponse body) {
        stillageLayoutLookUp.setVisibility(View.VISIBLE);
        stillageLayout.textViewitem.setText(body.getItemId());
        stillageLayout.textViewNumber.setText(body.getStickerID());
        stillageLayout.textViewQuantity.setText(body.getStandardQty() + "");
        stillageLayout.textViewStdQuantity.setText(body.getItemStdQty() + "");
        stillageLayout.textViewitemDesc.setText(body.getDescription());
        stillageLayout.linearLayoutLocation.setVisibility(View.VISIBLE);
        stillageLayout.textViewLocation.setText(body.getLocation());
    }
}
