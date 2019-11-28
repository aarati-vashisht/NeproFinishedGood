package com.neprofinishedgood.qualitycheck.rejectquantity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.dashboard.DashBoardAcivity;
import com.neprofinishedgood.move.adapter.SpinnerAdapter;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.qualitycheck.model.RejectedInput;
import com.neprofinishedgood.qualitycheck.model.RejectionListInput;
import com.neprofinishedgood.qualitycheck.rejectquantity.adapter.RejectionListAdapter;
import com.neprofinishedgood.qualitycheck.rejectquantity.presenter.IQAPresenter;
import com.neprofinishedgood.qualitycheck.rejectquantity.presenter.IQAView;
import com.neprofinishedgood.qualitycheck.qualityhold.QualityHoldActivity;
import com.neprofinishedgood.utils.Constants;
import com.neprofinishedgood.utils.NetworkChangeReceiver;
import com.neprofinishedgood.utils.SharedPref;
import com.neprofinishedgood.utils.StillageLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;

public class RejectQuantityActivity extends BaseActivity implements IQAView {

    @BindView(R.id.linearLayoutScanDetail)
    LinearLayout linearLayoutScanDetail;

    @BindView(R.id.stillageDetail)
    View stillageDetail;

    StillageLayout stillageLayout;

    @BindView(R.id.editTextRejectQuantity)
    AppCompatEditText editTextRejectQuantity;

    @BindView(R.id.spinnerReason)
    Spinner spinnerRejectReason;

    @BindView(R.id.buttonReject)
    CustomButton buttonReject;

    @BindView(R.id.editTextScanStillage)
    AppCompatEditText editTextScanStillage;

    @BindView(R.id.linearLayoutOfflineData)
    LinearLayout linearLayoutOfflineData;

    @BindView(R.id.linearLayout2)
    LinearLayout linearLayoutShift;

    @BindView(R.id.linearLayoutRejectQtyButtons)
    LinearLayout linearLayoutRejectQtyButtons;

    @BindView(R.id.frameEnterQuantity)
    FrameLayout frameEnterQuantity;

    @BindView(R.id.textViewNumberOffline)
    TextView textViewNumberOffline;

    @BindView(R.id.spinnerShift)
    Spinner spinnerShift;

    long scanStillageLastTexxt = 0;
    long delay = 1500;
    private IQAPresenter iqaInterface;
    private String isHold, reason, shift, reasonName;
    ScanStillageResponse body;
    private ArrayList<String> shiftList;

    String isKg = "0", workOrderNo = "";

    static RejectQuantityActivity instance;

    RejectionListAdapter adapter;
    RejectedInput rejectedInput;

    public static RejectQuantityActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reject_quantity);
        ButterKnife.bind(this);
        stillageLayout = new StillageLayout();
        instance = this;
        ButterKnife.bind(stillageLayout, stillageDetail);
        String title = getIntent().getStringExtra("REJECT_TITLE");
        setTitle(title);
        isKg = getIntent().getStringExtra("IsKg");
        iqaInterface = new IQAPresenter(this, this);
        initData();
        editTextScanStillage.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        if (isKg.equals("0")) {
            editTextRejectQuantity.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        } else {
            editTextRejectQuantity.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }

        callService();
    }

    private void initData() {
        SpinnerAdapter reasonListAdapter = new SpinnerAdapter(RejectQuantityActivity.this, R.layout.spinner_layout, reasonList);
        spinnerRejectReason.setAdapter(reasonListAdapter);
        setSpinnerShiftData();
    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanStillageChanged(Editable text) {
        if (!text.toString().trim().equals("")) {
//            scanStillagehandler.postDelayed(stillageRunnable, delay);
            if (text.toString().trim().length() == scanStillageLength) {
                if (NetworkChangeReceiver.isInternetConnected(RejectQuantityActivity.this)) {
                    if (isScanValidated(editTextScanStillage.getText().toString().trim())) {
                        showProgress(RejectQuantityActivity.this);
                        if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
                            iqaInterface.callScanStillageService(new MoveInput(editTextScanStillage.getText().toString().trim(), userId));
                        }
                    }
                } else {
                    showSuccessDialog(getString(R.string.no_internet));
                    editTextScanStillage.setText("");
//                    setDataOffline();
                }
            }
        }

    }

    boolean isScanValidated(String stillageNo) {
        ArrayList<RejectedInput> rejectedInputsPcs = SharedPref.getRejectionDataListPcs();
        ArrayList<RejectedInput> rejectedInputsKg = SharedPref.getRejectionDataListKg();

        if (isKg.equals("0")) {
            if (rejectedInputsPcs.size() >= 50) {
                showSuccessDialog(getResources().getString(R.string.rejection_list_full));
                return false;
            }
        } else {
            if (rejectedInputsKg.size() >= 50) {
                showSuccessDialog(getResources().getString(R.string.rejection_list_full));
                return false;
            }
        }

        for (RejectedInput rejectedInput : rejectedInputsPcs) {
            if (rejectedInput.getStickerNo().equals(stillageNo)) {
                showSuccessDialog(getResources().getString(R.string.rejected_in_pcs));
                return false;
            }
        }
        for (RejectedInput rejectedInput : rejectedInputsKg) {
            if (rejectedInput.getStickerNo().equals(stillageNo)) {
                showSuccessDialog(getResources().getString(R.string.rejected_in_kg));
                return false;
            }
        }
        return true;
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
//            if (NetworkChangeReceiver.isInternetConnected(RejectQuantityActivity.this)) {
//                showProgress(RejectQuantityActivity.this);
//                if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
//                    iqaInterface.callScanStillageService(new AisleInput(editTextScanStillage.getText().toString().trim(), userId));
//                }
//            } else {
//                setDataOffline();
//            }
//        }
//    };

    @Override
    public void onFailure(String message) {
        hideProgress();
        showSuccessDialog(message);
//        CustomToast.showToast(this, message);
    }

    @Override
    public void onSuccess(ScanStillageResponse body) {
        hideProgress();
        // initData();
        if (body.getStatus().equals(getResources().getString(R.string.success))) {
            if (isLocationMatched(body.getWareHouseID())) {
                if (body.getStandardQty() > 0) {
                    if (body.getwOStatusId() != 7) {
                        if (body.getIsCounted().equals("1")) {
                            isScanned = true;
                            setData(body);
                        } else {
                            showSuccessDialog(getResources().getString(R.string.raf_not_posted_reject));
                            editTextScanStillage.setText("");
                        }
                    } else {
                        showSuccessDialog(getResources().getString(R.string.wo_ended));
                        editTextScanStillage.setText("");
                    }
                } else {
                    showSuccessDialog(getResources().getString(R.string.stillage_discarded));
                    editTextScanStillage.setText("");
                }
            } else {
                editTextScanStillage.setText("");
                showSuccessDialog(getResources().getString(R.string.stillage_not_found));
            }
        } else {
            showSuccessDialog(body.getMessage());
//            CustomToast.showToast(getApplicationContext(), body.getMessage());
            editTextScanStillage.setText("");
        }
    }

    @Override
    public void onUpdateRejectedFailure(String message) {
        hideProgress();
        if (linearLayoutScanDetail.getVisibility() == View.VISIBLE) {
            showSuccessDialog(message);
//            CustomToast.showToast(this, message);
        }
    }

    @Override
    public void onUpdateRejectedSuccess(UniversalResponse body) {
        hideProgress();
        // initData();
        if (linearLayoutScanDetail.getVisibility() == View.VISIBLE) {
            if (body.getStatus().equals(getResources().getString(R.string.success))) {
                isScanned = false;
                showSuccessDialog(body.getMessage());
                linearLayoutScanDetail.setVisibility(View.GONE);
                editTextScanStillage.setEnabled(true);
                editTextScanStillage.setText("");
                cancelClick();
                if (isKg.equals("0")) {
                    saveRejectionDataListPcs(rejectedInput);
                } else {
                    saveRejectionDataListKg(rejectedInput);
                }

                if (isHold.equals("1")) {
                    String SELECTED_STILLAGE = new Gson().toJson(this.body, ScanStillageResponse.class);
                    startActivity(new Intent(this, QualityHoldActivity.class).putExtra(Constants.SELECTED_STILLAGE, SELECTED_STILLAGE));
                    finish();
                }
            } else {
                showSuccessDialog(body.getMessage());
//                CustomToast.showToast(getApplicationContext(), body.getMessage());
            }
        }
        spinnerRejectReason.setSelection(0);
    }

    @Override
    public void onUpdateRejectionListFailure(String message) {
        hideProgress();
        if (linearLayoutScanDetail.getVisibility() == View.VISIBLE) {
            showSuccessDialog(message);
        }
    }

    @Override
    public void onUpdateRejectionListSuccess(UniversalResponse body) {
        hideProgress();
        if (linearLayoutScanDetail.getVisibility() == View.VISIBLE) {
            if (body.getStatus().equals(getResources().getString(R.string.success))) {
                showSuccessDialog(body.getMessage());
                if(isKg.equals("0")){
                    deleteRejectionDataListPcs();
                }else{
                    deleteRejectionDataListKg();
                }
            } else {
                showSuccessDialog(body.getMessage());
            }
        }
    }

    void deleteRejectionDataListPcs() {
        ArrayList<RejectedInput> rejectList = SharedPref.getRejectionDataListPcs();
        rejectList.clear();
        SharedPref.saveRejectionDataListPcs(rejectList);
    }

    void deleteRejectionDataListKg() {
        ArrayList<RejectedInput> rejectList = SharedPref.getRejectionDataListKg();
        rejectList.clear();
        SharedPref.saveRejectionDataListKg(rejectList);
    }

    void saveRejectionDataListPcs(RejectedInput data) {
        ArrayList<RejectedInput> rejectList = SharedPref.getRejectionDataListPcs();
        rejectList.add(data);
        SharedPref.saveRejectionDataListPcs(rejectList);
    }

    void saveRejectionDataListKg(RejectedInput data) {
        ArrayList<RejectedInput> rejectList = SharedPref.getRejectionDataListKg();
        rejectList.add(data);
        SharedPref.saveRejectionDataListKg(rejectList);
    }

    @OnItemSelected(R.id.spinnerReason)
    public void spinnerBinSelected(Spinner spinner, int position) {
        reason = reasonList.get(position).getId();
        reasonName = reasonList.get(position).getName();
    }

    void setData(ScanStillageResponse body) {
        workOrderNo = body.getWorkOrderNo();

        this.body = body;
        isHold = body.getIsHold();
        linearLayoutScanDetail.setVisibility(View.VISIBLE);
        linearLayoutRejectQtyButtons.setVisibility(View.VISIBLE);
        editTextScanStillage.setEnabled(false);
        stillageLayout.textViewitem.setText(body.getItemId());
        stillageLayout.textViewQuantity.setText(body.getStandardQty() + "");
        stillageLayout.textViewitemDesc.setText(body.getDescription());
        stillageLayout.textViewStdQuantity.setText(body.getItemStdQty() + "");
        stillageLayout.textViewNumber.setText(body.getStickerID());
        if (isKg.equals("0")) {
            int standardQty = (int) body.getStandardQty();
            editTextRejectQuantity.setText(standardQty + "");
            editTextRejectQuantity.setSelection((standardQty + "").length());
        } else {
            editTextRejectQuantity.setText(body.getStandardQty() + "");
            editTextRejectQuantity.setSelection((body.getStandardQty() + "").length());
        }
        editTextRejectQuantity.requestFocus();
        setSpinnerShiftData();

    }

    @OnTextChanged(value = R.id.editTextRejectQuantity, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextRejectQuantityChanged(Editable text) {
        try {
            if (!text.toString().equals("") && !text.toString().equals(".")) {
                float rejectQty = round(Float.parseFloat(text.toString().trim()));
                float stillageQty = round(Float.parseFloat((this.body.getStandardQty() + "").trim()));
                if (rejectQty > stillageQty) {
                    editTextRejectQuantity.setText("");
                    showSuccessDialog("Reject quantity must be less than stillage quantity!");
                    editTextRejectQuantity.requestFocus();
                    buttonReject.setEnabled(false);
                } else {
                    buttonReject.setEnabled(true);
                }
            } else {
                buttonReject.setEnabled(false);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.buttonReject)
    public void onButtonRejectClick() {
        if (linearLayoutOfflineData.getVisibility() == View.GONE) {
            if (isValidated()) {
                showProgress(this);
                rejectedInput = new RejectedInput(editTextScanStillage.getText().toString().trim(), userId, editTextRejectQuantity.getText().toString().trim(), reason, reasonName, shift, isKg, workOrderNo);
                iqaInterface.callUpdateRejectedService(rejectedInput);
            }
        } else {
            if (isOfflineValidated()) {
                rejectedInput = new RejectedInput(editTextScanStillage.getText().toString().trim(), userId, editTextRejectQuantity.getText().toString().trim(), reason, reasonName, shift, isKg, workOrderNo);
                saveDataOffline(rejectedInput);
            }
        }
    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        showCancelAlert(2);
    }

    @OnClick(R.id.buttonViewList)
    public void onButtonViewListClick() {
        alertDialogRejectionList(this);
    }

    public void alertDialogRejectionList(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_alert_rejection_list);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView imgCloseList = dialog.findViewById(R.id.imgCloseList);
        CustomButton buttonRejectPost = dialog.findViewById(R.id.buttonRejectPost);
        RecyclerView recyclerViewLoadingPlans = dialog.findViewById(R.id.recyclerViewRejectionList);
        recyclerViewLoadingPlans.setVisibility(View.VISIBLE);
        ArrayList<RejectedInput> rejectionList;
        if (isKg.equals("0")) {
            rejectionList = SharedPref.getRejectionDataListPcs();
        } else {
            rejectionList = SharedPref.getRejectionDataListKg();
        }
        adapter = new RejectionListAdapter(rejectionList);

        recyclerViewLoadingPlans.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewLoadingPlans.setAdapter(adapter);
        recyclerViewLoadingPlans.setHasFixedSize(true);

        imgCloseList.setOnClickListener(v -> {
            dialog.cancel();
        });

        buttonRejectPost.setOnClickListener(v -> {
            sendRejectionDataList(rejectionList);
            dialog.cancel();
        });

        dialog.show();

    }

    void sendRejectionDataList(ArrayList<RejectedInput> rejectionList) {
        RejectionListInput rejectionListInput = new RejectionListInput(rejectionList, isKg);
//        showProgress(this);
//        iqaInterface.callUpdateRejectedListService(rejectionListInput);
        Gson gson = new Gson();
        String jsonData = gson.toJson(rejectionListInput);
        Log.d("json", jsonData);
    }

    public void cancelClick() {
        isScanned = false;
        linearLayoutScanDetail.setVisibility(View.GONE);
        linearLayoutScanDetail.setAnimation(fadeOut);
        linearLayoutRejectQtyButtons.setVisibility(View.GONE);
        linearLayoutRejectQtyButtons.setAnimation(fadeOut);
        editTextScanStillage.setText("");
        editTextScanStillage.setEnabled(true);
        spinnerRejectReason.setSelection(0);
    }

    boolean isValidated() {
        if (spinnerShift.getSelectedItemPosition() == 0) {
            TextView textView = (TextView) spinnerShift.getSelectedView();
            textView.setError(getString(R.string.select_shift));
            textView.requestFocus();
            return false;
        }
        if (editTextRejectQuantity.getText().toString().equals("0") || editTextRejectQuantity.getText().toString().equals("")) {
            editTextRejectQuantity.setError(getResources().getString(R.string.enter_reject_quantity));
            editTextRejectQuantity.requestFocus();
            return false;
        }
        if (spinnerRejectReason.getSelectedItemPosition() == 0) {
            TextView textView = (TextView) spinnerRejectReason.getSelectedView();
            textView.setError(getString(R.string.select_reason));
            textView.requestFocus();
            return false;
        }

        return true;
    }

    @OnItemSelected(R.id.spinnerShift)
    public void spinnerShiftSelected(Spinner spinner, int position) {
        shift = shiftList.get(position);
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

    public void imageButtonHomeClick(View view) {
        if (isScanned) {
            showBackAlert(new Intent(RejectQuantityActivity.this, DashBoardAcivity.class), true);
        } else {
            finishAffinity();
            startActivity(new Intent(RejectQuantityActivity.this, DashBoardAcivity.class));
        }
    }

    public void imageButtonBackClick(View view) {
        if (isScanned) {
            showBackAlert(null, false);
        } else {
            finish();
        }
    }

    public void onBackPressed() {
        if (isScanned) {
            showBackAlert(null, false);
        } else {
            finish();
        }
    }

//    @OnTextChanged(value = R.id.editTextScanLot, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
//    public void onEditTextScanLotChanged(Editable text) {
//        if (!text.toString().trim().equals("")) {
//            scanLothandler.postDelayed(lotRunnable, delay);
//        }
//
//    }
//
//    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.TEXT_CHANGED)
//    public void onEditTextScanLotTEXTCHANGED(Editable text) {
//        scanLothandler.removeCallbacks(lotRunnable);
//
//    }
//
//    //for call service on text change
//    Handler scanLothandler = new Handler();
//    private Runnable lotRunnable = new Runnable() {
//        public void run() {
//            if (NetworkChangeReceiver.isInternetConnected(RejectQuantityActivity.this)) {
//                showProgress(RejectQuantityActivity.this);
//                if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
//                    iqaInterface.callScanLotService(new ScanLotInput(body.getWorkOrderNo(),editTextScanLot.getText().toString().trim(), userId));
//                }
//            } else {
//                setDataOffline();
//            }
//        }
//    };
//
//    @Override
//    public void onLotScanFailure(String message) {
//        hideProgress();
//        CustomToast.showToast(this, message);
//    }
//
//    @Override
//    public void onLotScanSuccess(UniversalResponse body) {
//        hideProgress();
//        if (body.getStatus().equals(getResources().getString(R.string.success))) {
//            linearLayoutShift.setVisibility(View.VISIBLE);
//            frameEnterQuantity.setVisibility(View.VISIBLE);
//        } else {
//            CustomToast.showToast(getApplicationContext(), body.getMessage());
//            editTextScanLot.setText("");
//        }
//    }

    void setDataOffline() {
        textViewNumberOffline.setText(editTextScanStillage.getText().toString());
        setVisibilityInOfflineMode();
        initData();
        editTextRejectQuantity.setText("");

    }

    void setVisibilityInOfflineMode() {
        editTextScanStillage.setEnabled(false);
        linearLayoutOfflineData.setVisibility(View.VISIBLE);
        stillageDetail.setVisibility(View.GONE);
        linearLayoutScanDetail.setVisibility(View.VISIBLE);
    }

    void disableVisibility() {
        linearLayoutScanDetail.setVisibility(View.GONE);
        editTextScanStillage.setEnabled(true);
        editTextScanStillage.setText("");
        linearLayoutOfflineData.setVisibility(View.GONE);
        stillageDetail.setVisibility(View.VISIBLE);
        editTextRejectQuantity.setText("");
    }

    void saveDataOffline(RejectedInput data) {
        ArrayList<RejectedInput> rejectList = new ArrayList<>();
        Gson gson = new Gson();
        String rejectData = SharedPref.getRejectData();
        if (!rejectData.equals("")) {
            Type type = new TypeToken<ArrayList<RejectedInput>>() {
            }.getType();
            rejectList = gson.fromJson(rejectData, type);
        }
        rejectList.add(data);
        String json = gson.toJson(rejectList);
        SharedPref.saveRejectData(json);
        showSuccessDialog(getResources().getString(R.string.data_saved_offline));
//        CustomToast.showToast(this, getResources().getString(R.string.data_saved_offline));
        cancelClick();
        disableVisibility();
    }

    boolean isOfflineValidated() {
        if (spinnerRejectReason.getSelectedItemPosition() == 0) {
            TextView textView = (TextView) spinnerRejectReason.getSelectedView();
            textView.setError(getString(R.string.select_shift));
            textView.requestFocus();
            return false;
        }
        if (editTextRejectQuantity.getText().toString().equals("")) {
            editTextRejectQuantity.setError(getString(R.string.enter_reject_quantity));
            editTextRejectQuantity.requestFocus();
            return false;
        }
        return true;
    }

    public void callService() {
        if (NetworkChangeReceiver.isInternetConnected(RejectQuantityActivity.this)) {
            ArrayList<RejectedInput> rejectList = new ArrayList<>();
            Gson gson = new Gson();
            String rejectData = SharedPref.getRejectData();
            if (!rejectData.equals("")) {
                Type type = new TypeToken<ArrayList<RejectedInput>>() {
                }.getType();
                rejectList = gson.fromJson(rejectData, type);

                for (RejectedInput rejectedInput : rejectList) {
                    iqaInterface.callUpdateRejectedService(rejectedInput);
                }
                String json = "";
                SharedPref.saveRejectData(json);
            }
        }
    }
}
