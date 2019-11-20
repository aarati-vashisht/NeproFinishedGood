package com.neprofinishedgood.move;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.lookup.LookUpActivity;
import com.neprofinishedgood.move.adapter.MoveAdapter;
import com.neprofinishedgood.move.model.AllAssignedDataInput;
import com.neprofinishedgood.move.model.AssignedStillages;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.move.model.UpdateMoveLocationInput;
import com.neprofinishedgood.move.presenter.IPlannedAndUnPlannedView;
import com.neprofinishedgood.move.presenter.IPlannedUnplannedPresenter;
import com.neprofinishedgood.utils.Constants;
import com.neprofinishedgood.utils.NetworkChangeReceiver;
import com.neprofinishedgood.utils.SharedPref;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class MoveActivity extends BaseActivity implements IPlannedAndUnPlannedView {


    private static MoveActivity instance;
    @BindView(R.id.editTextScanStillage)
    AppCompatEditText editTextScanStillage;
    @BindView(R.id.recyclerViewStillage)
    RecyclerView recyclerViewStillage;


    public IPlannedUnplannedPresenter iPlannedUnplannedPresenter;
    long delay = 1500;
    long scanStillageLastTexxt = 0;
    private MoveAdapter adapter;

    public static MoveActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move);
        ButterKnife.bind(this);
        instance = this;
        setTitle(getString(R.string.move));
        iPlannedUnplannedPresenter = new IPlannedUnplannedPresenter(this, this);
        callService();
        editTextScanStillage.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
    }

    public void getAllAssignedData() {
        if (NetworkChangeReceiver.isInternetConnected(MoveActivity.this)) {
            showProgress(this);
            iPlannedUnplannedPresenter.callGetAllAssignedData(new AllAssignedDataInput(userId));
        } else {
            showSuccessDialog(getString(R.string.no_internet));
        }
    }


    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanStillageChanged(Editable text) {
        if (!text.toString().trim().equals("")) {
//            scanStillagehandler.postDelayed(stillageRunnable, delay);
            if (text.toString().trim().length() == scanStillageLength) {
                if (NetworkChangeReceiver.isInternetConnected(MoveActivity.this)) {
                    showProgress(MoveActivity.this);
                    String stillageTxt = editTextScanStillage.getText().toString().trim();
                    iPlannedUnplannedPresenter.callScanStillageService(new MoveInput(stillageTxt, userId));
                } else {
                    showSuccessDialog(getString(R.string.no_internet));
//                    offlineProcess();
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
//            if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
//                if (NetworkChangeReceiver.isInternetConnected(MoveActivity.this)) {
//                    showProgress(MoveActivity.this);
//                    String stillageTxt = editTextScanStillage.getText().toString().trim();
//                    iPlannedUnplannedPresenter.callScanStillageService(new AisleInput(stillageTxt, userId));
//                } else {
//                    offlineProcess();
//                }
//            }
//        }
//    };

    //on get data success
    @Override
    public void onSuccess(ScanStillageResponse body) {
        hideProgress();
        // initData();
        if (body.getStatus().equals(getResources().getString(R.string.success))) {
            if (isLocationMatched(body.getWareHouseID())) {
                if (body.getStandardQty() > 0) {
                    Gson gson = new Gson();
                    String putExtraData = gson.toJson(body);
                    startActivity(new Intent(this, MoveStillageActivity.class).putExtra(Constants.SELECTED_STILLAGE, putExtraData));
                    editTextScanStillage.setText("");
                    overridePendingTransition(0, 0);
                } else {
                    showSuccessDialog(getResources().getString(R.string.stillage_discarded));
                    editTextScanStillage.setText("");
                }
            } else {
                editTextScanStillage.setText("");
                showSuccessDialog(getResources().getString(R.string.stillage_not_found));
            }
        } else {
            editTextScanStillage.setText("");
            showSuccessDialog(body.getMessage());
//            CustomToast.showToast(getApplicationContext(), body.getMessage());
        }

    }

    //on get data failure
    @Override
    public void onFailure(String message) {
        hideProgress();
        showSuccessDialog(message);
//        CustomToast.showToast(this, message);
    }

    @Override
    public void onAssignedFailure(String message) {
        hideProgress();
//        showSuccessDialog(message);
    }

    @Override
    public void onAssignedSuccess(AssignedStillages body) {
        hideProgress();
        if (body.getStatus().equals(getString(R.string.success))) {
//            showSuccessDialog(body.getMessage());
//            CustomToast.showToast(this, body.getMessage());
            if (body.getStillageList().size() > 0) {
                setAdapter(body.getStillageList());
            }
        } else {
//            showSuccessDialog(body.getMessage());
            // CustomToast.showToast(this, getString(R.string.something_went_wrong_please_try_again));
        }
    }

    private void setAdapter(List<ScanStillageResponse> stillageList) {
        recyclerViewStillage.setVisibility(View.VISIBLE);
        adapter = new MoveAdapter(stillageList);
        recyclerViewStillage.setAdapter(adapter);
        recyclerViewStillage.setHasFixedSize(true);
    }

    void offlineProcess() {
        startActivity(new Intent(this, MoveStillageActivity.class).putExtra(Constants.SELECTED_STILLAGE_OFFLINE, editTextScanStillage.getText().toString().trim()));
        editTextScanStillage.setText("");
        overridePendingTransition(0, 0);
    }

    //initializes data and calling MoveService after checking internet
    public void callService() {
        if (NetworkChangeReceiver.isInternetConnected(MoveActivity.this)) {
            ArrayList<UpdateMoveLocationInput> moveList = new ArrayList<>();
            Gson gson = new Gson();
            String moveData = SharedPref.getMoveData();
            if (!moveData.equals("")) {
                Type type = new TypeToken<ArrayList<UpdateMoveLocationInput>>() {
                }.getType();
                moveList = gson.fromJson(moveData, type);

                for (UpdateMoveLocationInput updateMoveLocationInput : moveList) {
                    iPlannedUnplannedPresenter.callMoveServcie(updateMoveLocationInput);
                }
                String json = "";
                SharedPref.saveMoveData(json);
            }
            getAllAssignedData();
        }
    }
}
