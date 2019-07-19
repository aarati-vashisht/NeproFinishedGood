package com.neprofinishedgood.plannedandunplannedmove;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.plannedandunplannedmove.adapter.MoveAdapter;
import com.neprofinishedgood.plannedandunplannedmove.model.AllAssignedDataInput;
import com.neprofinishedgood.plannedandunplannedmove.model.AssignedStillages;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.UpdateMoveLocationInput;
import com.neprofinishedgood.plannedandunplannedmove.presenter.IPlannedAndUnPlannedView;
import com.neprofinishedgood.plannedandunplannedmove.presenter.IPlannedUnplannedPresenter;
import com.neprofinishedgood.utils.Constants;
import com.neprofinishedgood.utils.NetworkChangeReceiver;
import com.neprofinishedgood.utils.NetworkHandleService;
import com.neprofinishedgood.utils.SharedPref;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class PlannedAndUnPlannedMoveActivity extends BaseActivity implements IPlannedAndUnPlannedView {


    private static PlannedAndUnPlannedMoveActivity instance;
    @BindView(R.id.editTextScanStillage)
    AppCompatEditText editTextScanStillage;
    @BindView(R.id.recyclerViewStillage)
    RecyclerView recyclerViewStillage;


    private IPlannedUnplannedPresenter iPlannedUnplannedPresenter;
    long delay = 1500;
    long scanStillageLastTexxt = 0;
    private MoveAdapter adapter;

    public static PlannedAndUnPlannedMoveActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planned_unplanned_move);
        ButterKnife.bind(this);
        instance = this;
        setTitle(getString(R.string.move));
        iPlannedUnplannedPresenter = new IPlannedUnplannedPresenter(this, this);
        callService();

    }

    public void getAllAssignedData() {
        showProgress(this);
        iPlannedUnplannedPresenter.callGetAllAssignedData(new AllAssignedDataInput(userId));
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
            if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
                if (NetworkChangeReceiver.isInternetConnected(PlannedAndUnPlannedMoveActivity.this)) {
                    showProgress(PlannedAndUnPlannedMoveActivity.this);
                    String stillageTxt = editTextScanStillage.getText().toString().trim();
                    iPlannedUnplannedPresenter.callScanStillageService(new MoveInput(stillageTxt, userId));
                } else {
                    offlineProcess();
                }
            }
        }
    };

    //on get data success
    @Override
    public void onSuccess(ScanStillageResponse body) {
        hideProgress();
        // initData();
        if (body.getStatus().equals(getResources().getString(R.string.success))) {
            Gson gson = new Gson();
            String putExtraData = gson.toJson(body);
            startActivity(new Intent(this, MoveStillageActivity.class).putExtra(Constants.SELECTED_STILLAGE, putExtraData));
            editTextScanStillage.setText("");
            overridePendingTransition(0, 0);
        } else {
            editTextScanStillage.setText("");
            CustomToast.showToast(getApplicationContext(), body.getMessage());

        }

    }

    //on get data failure
    @Override
    public void onFailure(String message) {
        hideProgress();
        CustomToast.showToast(this, message);
    }

    @Override
    public void onAssignedFailure(String message) {
        hideProgress();

    }

    @Override
    public void onAssignedSuccess(AssignedStillages body) {
        hideProgress();
        if (body.getStatus().equals(getString(R.string.success))) {
            CustomToast.showToast(this, body.getMessage());
            if (body.getStillageList().size() > 0) {
                setAdapter(body.getStillageList());
            }
        } else {
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
        if (NetworkChangeReceiver.isInternetConnected(PlannedAndUnPlannedMoveActivity.this)) {
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
