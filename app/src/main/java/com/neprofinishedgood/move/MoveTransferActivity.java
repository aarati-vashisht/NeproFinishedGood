package com.neprofinishedgood.move;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.neprofinishedgood.R;
import com.neprofinishedgood.assigntransfer.AssignTransferActivity;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.dashboard.DashBoardAcivity;
import com.neprofinishedgood.move.adapter.TransferDetailAdapter;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.TransDoneInputModel;
import com.neprofinishedgood.move.model.TransferDetailResponseModel;
import com.neprofinishedgood.move.model.TransferList;
import com.neprofinishedgood.move.model.TransferStillageList;
import com.neprofinishedgood.move.presenter.IPlannedUnplannedPresenter;
import com.neprofinishedgood.move.transferpresenter.IMoveTransferInterface;
import com.neprofinishedgood.move.transferpresenter.IMoveTransferPresenter;
import com.neprofinishedgood.move.transferpresenter.IMoveTransferView;
import com.neprofinishedgood.pickandload.PickAndLoadStillageActivity;
import com.neprofinishedgood.transferstillage.presenter.ITransferView;
import com.neprofinishedgood.utils.NetworkChangeReceiver;
import com.neprofinishedgood.utils.SharedPref;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MoveTransferActivity extends BaseActivity implements IMoveTransferView {

    @BindView(R.id.recyclerViewTransferStillageList)
    RecyclerView recyclerViewTransferStillageList;

    @BindView(R.id.buttonDone)
    CustomButton buttonDone;

    @BindView(R.id.textViewTransType)
    TextView textViewTransType;

    @BindView(R.id.textViewToSite)
    TextView textViewToSite;

    @BindView(R.id.textViewToWarehouse)
    TextView textViewToWarehouse;

    @BindView(R.id.textViewFromWarehouse)
    TextView textViewFromWarehouse;

    @BindView(R.id.editTextScanStillage)
    EditText editTextScanStillage;

    static MoveTransferActivity instance;

    boolean doneSuccess = false;

    String TRANSFERDATA = "TRANSFERDATA", TRANSFERDETAILDATA = "TRANSFERDETAILDATA";

    public TransferList transferData;
    TransferDetailResponseModel body;
    private List<TransferStillageList> stillageList;
    private TransferDetailAdapter adapter;

    static List<TransferList> transferLists;
    public static TransferList savedTransferData = new TransferList();
    static int positionOfActivity = -1;

    public IMoveTransferInterface iMoveTransferInterface;

    public static MoveTransferActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_transfer);
        ButterKnife.bind(this);
        instance = this;
        setTitle(getString(R.string.move));
        initLayout();
        iMoveTransferInterface = new IMoveTransferPresenter(this, this);

    }

    void initLayout() {
        Gson gson = new Gson();
        transferData = gson.fromJson(getIntent().getStringExtra(TRANSFERDATA), TransferList.class);
        body = gson.fromJson(getIntent().getStringExtra(TRANSFERDETAILDATA), TransferDetailResponseModel.class);
        if (transferData.getIsTj().equals("1")) {
            textViewTransType.setText(getResources().getString(R.string.transfer_journal_process));
        } else {
            textViewTransType.setText(getResources().getString(R.string.transfer_order_process));
        }
        textViewToSite.setText(transferData.getSiteId());
        textViewToWarehouse.setText(transferData.getWareHouseId());
        textViewFromWarehouse.setText(transferData.getFromWareHouseId());

    }

    @Override
    protected void onResume() {
        super.onResume();
        setRecyclerViewAdapter();
    }

    private void setRecyclerViewAdapter() {
        transferLists = SharedPref.getTransferDetailList();
        boolean isDetailSaved = false;
        for (int i = 0; i < transferLists.size(); i++) {
            if (transferLists.get(i).getActivityId().equals(transferData.getActivityId())) {
                savedTransferData = transferLists.get(i);
                stillageList = savedTransferData.getStillageList();
                positionOfActivity = i;
                isDetailSaved = true;
                break;
            }
        }
        savedTransferData.setActivityId(transferData.getActivityId());
        if (!isDetailSaved) {
            stillageList = body.getStillageList();
            savedTransferData.setStillageList(body.getStillageList());
        }

        recyclerViewTransferStillageList.setVisibility(View.VISIBLE);
        adapter = new TransferDetailAdapter(stillageList);
        recyclerViewTransferStillageList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewTransferStillageList.setAdapter(adapter);
        recyclerViewTransferStillageList.setHasFixedSize(true);
    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanStillageChanged(Editable text) {
        if (!text.toString().trim().equals("")) {
            if (text.toString().trim().length() == scanStillageLength) {
                adapter.getFilter().filter(text.toString());
                editTextScanStillage.setText("");
            }
        }
    }

    @OnClick(R.id.buttonDone)
    void onButtonDoneClick() {
        for (int i = 0; i < savedTransferData.getStillageList().size(); i++) {
            if (!savedTransferData.getStillageList().get(i).getStatus().equals("2")) {
                showSuccessDialog("Please transfer all stillages first.");
                return;
            }
        }
        if (NetworkChangeReceiver.isInternetConnected(MoveTransferActivity.this)) {
            showProgress(this);
            TransDoneInputModel transDoneInputModel = new TransDoneInputModel(transferData.getIsTj() + "", transferData.getTATHID(), userId);
            iMoveTransferInterface.callPostAssignTransfer(transDoneInputModel);
        } else {
            showSuccessDialog(getString(R.string.no_internet));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!doneSuccess) {
            savePrefs();
        }
    }

    void savePrefs() {
        if (positionOfActivity > -1) {
            transferLists.remove(positionOfActivity);
        }
        positionOfActivity = -1;
        Gson gson = new Gson();
        transferLists.add(savedTransferData);
        SharedPref.saveTransferDetailList(gson.toJson(transferLists));
    }

    public void imageButtonHomeClick(View view) {
        finishAffinity();
        startActivity(new Intent(MoveTransferActivity.this, DashBoardAcivity.class));
    }

    @Override
    public void onGetTransDoneSuccess(UniversalResponse body) {
        hideProgress();
        if (body.getStatus().equals(getString(R.string.success))) {
            showDialog(body.getMessage());
            transferLists.remove(positionOfActivity);
            Gson gson = new Gson();
            SharedPref.saveTransferDetailList(gson.toJson(transferLists));
            doneSuccess = true;
        }else{
            showSuccessDialog(body.getMessage());
        }
    }

    void showDialog(String message) {
        builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(false)
                .setPositiveButton(getString(R.string.ok), (dialog, id) -> {
                    dialog.cancel();
                    finish();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onGetTransDoneFailure(String message) {
        hideProgress();
        showSuccessDialog(body.getMessage());
    }
}