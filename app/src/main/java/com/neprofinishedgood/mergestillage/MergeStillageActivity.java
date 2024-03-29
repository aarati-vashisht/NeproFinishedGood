package com.neprofinishedgood.mergestillage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.dashboard.DashBoardAcivity;
import com.neprofinishedgood.mergestillage.model.UpgradeMergeInput;
import com.neprofinishedgood.mergestillage.presenter.IMergeStillageInterface;
import com.neprofinishedgood.mergestillage.presenter.IMergeStillageView;
import com.neprofinishedgood.mergestillage.presenter.MergeStillagePresenter;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.utils.NetworkChangeReceiver;
import com.neprofinishedgood.utils.StillageLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MergeStillageActivity extends BaseActivity implements IMergeStillageView {

    @BindView(R.id.relativeLayoutScanChildDetail)
    RelativeLayout relativeLayoutScanChildDetail;
    @BindView(R.id.linearLayoutMergeStillage)
    LinearLayout linearLayoutMergeStillage;
    @BindView(R.id.linearLayoutScanParentDetail)
    LinearLayout linearLayoutScanParentDetail;
    @BindView(R.id.linearLayoutScanChild)
    LinearLayout linearLayoutScanChild;
    @BindView(R.id.linearLayoutAssignLocationButtons)
    LinearLayout linearLayoutAssignLocationButtons;
    @BindView(R.id.linearLayoutQuantitySum)
    LinearLayout linearLayoutQuantitySum;
    @BindView(R.id.parentStillageDetail)
    View parentStillageDetail;

    @BindView(R.id.childStillageDetail)
    View childStillageDetail;

    @BindView(R.id.editTextScanChildStillage)
    AppCompatEditText editTextScanChildStillage;

    @BindView(R.id.editTextMergeQuantity)
    AppCompatEditText editTextMergeQuantity;

    @BindView(R.id.editTextScanParentStillage)
    AppCompatEditText editTextScanParentStillage;

    @BindView(R.id.buttonMerge)
    CustomButton buttonMerge;

    @BindView(R.id.buttonCancel)
    CustomButton buttonCancel;

    @BindView(R.id.buttonFinish)
    CustomButton buttonFinish;

    @BindView(R.id.textViewQuantitySum)
    TextView textViewQuantitySum;

    StillageLayout parentStillageLayout;
    StillageLayout childStillageLayout;

    float mergeQty, childQty, parentQty, parentStdQty;
    private IMergeStillageInterface iMergeStillageInterface;
    long delay = 1000;
    long scanStillageLastTexxt = 0;
    private boolean isChild = false;
    String childToSend, childWareHouse, parentWareHouse = "";
    String isParentRAF = "0";

    static MergeStillageActivity instance;

    public static MergeStillageActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merge_stillage);
        ButterKnife.bind(this);
        instance = this;
        parentStillageLayout = new StillageLayout();
        childStillageLayout = new StillageLayout();
        ButterKnife.bind(parentStillageLayout, parentStillageDetail);
        ButterKnife.bind(childStillageLayout, childStillageDetail);
        setTitle(getString(R.string.merge_stillage));
        editTextScanParentStillage.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        editTextScanChildStillage.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        iMergeStillageInterface = new MergeStillagePresenter(this, this);
    }


    @OnTextChanged(value = R.id.editTextScanParentStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanStillageChanged(Editable text) {
        if (!text.toString().trim().equals("")) {
//            scanStillagehandler.postDelayed(stillageRunnable, delay);
            if (text.toString().trim().length() == scanStillageLength) {
                if (NetworkChangeReceiver.isInternetConnected(MergeStillageActivity.this)) {
                    showProgress(MergeStillageActivity.this);
                    iMergeStillageInterface.callScanStillageService(new MoveInput(editTextScanParentStillage.getText().toString().trim(), userId));

                } else {
                    showSuccessDialog(getString(R.string.no_internet));
                    editTextScanParentStillage.setText("");
//                    CustomToast.showToast(MergeStillageActivity.this, getString(R.string.no_internet));
                }
            }
        }

    }

//    @OnTextChanged(value = R.id.editTextScanParentStillage, callback = OnTextChanged.Callback.TEXT_CHANGED)
//    public void onEditTextScanStillageTEXTCHANGED(Editable text) {
//        scanStillagehandler.removeCallbacks(stillageRunnable);
//
//    }
//
//    //for call service on text change
//    Handler scanStillagehandler = new Handler();
//    private Runnable stillageRunnable = new Runnable() {
//        public void run() {
//            if (NetworkChangeReceiver.isInternetConnected(MergeStillageActivity.this)) {
//                showProgress(MergeStillageActivity.this);
//                if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
//                    iMergeStillageInterface.callScanStillageService(new AisleInput(editTextScanParentStillage.getText().toString().trim(), userId));
//
//                }
//            } else {
//                CustomToast.showToast(MergeStillageActivity.this, getString(R.string.no_internet));
//            }
//        }
//    };

    @OnTextChanged(value = R.id.editTextScanChildStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void editTextScanChildStillageChanged(Editable text) {
        if (!text.toString().trim().equals("")) {
//            scanStillagehandler2.postDelayed(stillageRunnable2, delay);
            if (text.toString().trim().length() == scanStillageLength) {
                if (NetworkChangeReceiver.isInternetConnected(MergeStillageActivity.this)) {
                    if (!editTextScanParentStillage.getText().toString().trim().equalsIgnoreCase(text.toString().trim())) {
                        isChild = true;
                        if (childToSend == null) {
                            childToSend = "";
                        }
                        if (childToSend.length() > 0) {
                            if (!childToSend.contains(editTextScanChildStillage.getText().toString().trim())) {
                                showProgress(MergeStillageActivity.this);
                                iMergeStillageInterface.callScanStillageService(new MoveInput(editTextScanChildStillage.getText().toString().trim(), userId));
                            } else {
                                showSuccessDialog(getString(R.string.this_stillage_already_merged));
                            }
                        } else {
                            showProgress(MergeStillageActivity.this);
                            iMergeStillageInterface.callScanStillageService(new MoveInput(editTextScanChildStillage.getText().toString().trim(), userId));
                        }
                    } else {
                        showSuccessDialog(getString(R.string.child_and_parent_cannot_be_same));
                        editTextScanChildStillage.setText("");
                    }
                } else {
                    editTextScanChildStillage.setText("");
                    showSuccessDialog(getString(R.string.no_internet));
                }
            }

        }

    }

//    @OnTextChanged(value = R.id.editTextScanChildStillage, callback = OnTextChanged.Callback.TEXT_CHANGED)
//    public void editTextScanChildStillageTEXTCHANGED(Editable text) {
//        scanStillagehandler2.removeCallbacks(stillageRunnable2);
//
//    }
//
//    //for call service on text change
//    Handler scanStillagehandler2 = new Handler();
//    private Runnable stillageRunnable2 = new Runnable() {
//        public void run() {
//            if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
//                isChild = true;
//                if (childToSend == null) {
//                    childToSend = "";
//                }
//                if (childToSend.length() > 0) {
//                    if (!childToSend.contains(editTextScanChildStillage.getText().toString().trim())) {
//                        showProgress(MergeStillageActivity.this);
//                        iMergeStillageInterface.callScanStillageService(new MoveInput(editTextScanChildStillage.getText().toString().trim(), userId));
//                    } else {
//                        editTextScanChildStillage.setError(getString(R.string.this_stillage_already_merged));
//                    }
//                } else {
//                    showProgress(MergeStillageActivity.this);
//                    iMergeStillageInterface.callScanStillageService(new MoveInput(editTextScanChildStillage.getText().toString().trim(), userId));
//                }
//            }
//        }
//    };


    void setParentData(ScanStillageResponse body) {
        parentStillageLayout.textViewitem.setText(body.getItemId());
        parentStillageLayout.textViewNumber.setText(body.getStickerID());
        parentStillageLayout.textViewQuantity.setText(body.getStandardQty() + "");
        parentStillageLayout.textViewStdQuantity.setText(body.getItemStdQty() + "");
        parentStillageLayout.textViewitemDesc.setText(body.getDescription());
    }

    void setChildData(ScanStillageResponse response) {
        childStillageLayout.textViewitem.setText(response.getItemId());
        childStillageLayout.textViewNumber.setText(response.getStickerID());
        childStillageLayout.textViewQuantity.setText(response.getStandardQty() + "");
        childStillageLayout.textViewStdQuantity.setText(response.getItemStdQty() + "");
        childStillageLayout.textViewitemDesc.setText(response.getDescription());
    }

    @OnTextChanged(value = R.id.editTextMergeQuantity, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextMergeQuantityChanged(Editable text) {
        if (!text.toString().equals("") && !text.toString().equals(".")) {
            mergeQty = Float.parseFloat(text.toString());
            childQty = Float.parseFloat(childStillageLayout.textViewQuantity.getText().toString());
            parentQty = Float.parseFloat(parentStillageLayout.textViewQuantity.getText().toString());
//            parentStdQty = Float.parseFloat(parentStillageLayout.textViewStdQuantity.getText().toString());
            if (mergeQty != 0) {
                if (mergeQty > childQty) {
                    showSuccessDialog("Merge quantity should be less than or equal to child stillage quantity.");
                    editTextMergeQuantity.requestFocus();
                    buttonMerge.setEnabled(false);
                    textViewQuantitySum.setText(round(parentQty) + "");
//                } else if ((mergeQty + parentQty) > parentStdQty) {
//                    showSuccessDialog("Parent stillage quantity is exceeded parent standard stillage quantity.");
////                    editTextMergeQuantity.setError(getString(R.string.invalid_merge_quantity));
//                    editTextMergeQuantity.requestFocus();
//                    buttonMerge.setEnabled(false);
//                    textViewQuantitySum.setText(round(parentQty) + "");
                } else {
                    buttonMerge.setEnabled(true);
                    float sum = mergeQty + parentQty;
                    textViewQuantitySum.setText(round(sum) + "");
                }
            } else {
                buttonMerge.setEnabled(false);
            }
        } else {
            buttonMerge.setEnabled(false);
            textViewQuantitySum.setText(parentStillageLayout.textViewQuantity.getText().toString());
        }
    }

    boolean isValidated() {
        String text = editTextMergeQuantity.getText().toString().trim();
        if (!text.equals("")) {
            float mergeQty = Float.parseFloat(text);
            float childQty = Float.parseFloat(childStillageLayout.textViewQuantity.getText().toString());

            if (mergeQty > childQty || mergeQty == 0) {
                editTextMergeQuantity.setError(getString(R.string.invalid_merge_quantity));
                editTextMergeQuantity.requestFocus();
                return false;
            }
        } else {
            editTextMergeQuantity.setError(getString(R.string.enter_merge_quantity));
            editTextMergeQuantity.requestFocus();
            return false;
        }

//        String childItem, parentItem;
//        childItem = childStillageLayout.textViewitem.getText().toString().trim();
//        parentItem = parentStillageLayout.textViewitem.getText().toString().trim();
        if (editTextScanParentStillage.getText().toString().trim().equals(editTextScanChildStillage.getText().toString().trim())) {
            showSuccessDialog(getResources().getString(R.string.child_and_parent_cannot_be_same));
            return false;
        }
//        if (!childItem.equals(parentItem)) {
//            showSuccessDialog(getResources().getString(R.string.child_and_parent_not_matched));
//            return false;
//        }
//        if (!childWareHouse.equals(parentWareHouse)) {
//            showSuccessDialog(getResources().getString(R.string.child_and_parent_warehouse_not_matched));
//            return false;
//        }
        return true;
    }

    @OnClick(R.id.buttonMerge)
    public void onButtonMergeClick() {
//        if (parentQty == parentStdQty) {
//            buttonMerge.setEnabled(false);
//            relativeLayoutScanChildDetail.setVisibility(View.GONE);
//            relativeLayoutScanChildDetail.setAnimation(fadeOut);
//        } else {
        if (isValidated()) {
            if (mergeQty == childQty) {
                showDiscardStickerAlertDialog();
            } else {
                mergeClick("");
            }
        }
//        }

    }

    private void showDiscardStickerAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This sticker will be empty and will be discarded").setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mergeClick(getResources().getString(R.string.discard));
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    void mergeClick(String discard) {
        String changeParentQty = round(mergeQty + parentQty) + "";
        parentStillageLayout.textViewQuantity.setText(changeParentQty);
        String changeChildQty = round(Float.parseFloat(childStillageLayout.textViewQuantity.getText().toString())) - mergeQty + "";
        childStillageLayout.textViewQuantity.setText(changeChildQty);
//        if ((mergeQty + parentQty) == parentStdQty) {
//            //nothing to merge parent is full
//            //call service to add
//            childToSend += editTextScanChildStillage.getText().toString() + ":" + editTextMergeQuantity.getText().toString().trim() + ",";
//            linearLayoutScanChild.setVisibility(View.VISIBLE);
//            relativeLayoutScanChildDetail.setVisibility(View.GONE);
//            relativeLayoutScanChildDetail.setAnimation(fadeOut);
//            buttonMerge.setText(getString(R.string.merge));
//            linearLayoutMergeStillage.setVisibility(View.GONE);
//            linearLayoutMergeStillage.setAnimation(fadeOut);
//            linearLayoutAssignLocationButtons.setAnimation(fadeOut);
//            linearLayoutAssignLocationButtons.setVisibility(View.VISIBLE);
//            showProgress(this);
//            UpgradeMergeInput upgradeMergeInput = new UpgradeMergeInput(editTextScanParentStillage.getText().toString().trim(), userId, childToSend, textViewQuantitySum.getText().toString(), "3");
//            iMergeStillageInterface.callUpdateMergeStillage(upgradeMergeInput);
//        } else {
        ///add more items to merge
        showAddMoreALert();
//        }
        textViewQuantitySum.setText(round(mergeQty + parentQty) + "");
    }

    private void showAddMoreALert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.stillage_merge_message).setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string.add_more), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                childToSend += editTextScanChildStillage.getText().toString() + ":" + editTextMergeQuantity.getText().toString().trim() + ",";
                editTextMergeQuantity.setText("");
                editTextScanChildStillage.setEnabled(true);
                editTextScanChildStillage.setText("");
                editTextScanChildStillage.requestFocus();
                relativeLayoutScanChildDetail.setVisibility(View.GONE);
                relativeLayoutScanChildDetail.setAnimation(fadeOut);
                linearLayoutMergeStillage.setVisibility(View.GONE);
                linearLayoutMergeStillage.setAnimation(fadeOut);
                buttonFinish.setVisibility(View.VISIBLE);
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.exit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                /// call merge service
                if (NetworkChangeReceiver.isInternetConnected(MergeStillageActivity.this)) {
                    childToSend += editTextScanChildStillage.getText().toString() + ":" + editTextMergeQuantity.getText().toString().trim() + ",";
                    showProgress(MergeStillageActivity.this);
                    UpgradeMergeInput upgradeMergeInput = new UpgradeMergeInput(editTextScanParentStillage.getText().toString().trim(), userId, childToSend, textViewQuantitySum.getText().toString(), "3");
                    iMergeStillageInterface.callUpdateMergeStillage(upgradeMergeInput);
                    dialogInterface.dismiss();
                } else {
                    showSuccessDialog("No internet, stillges will not merge.");
                }
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {

        showCancelAlert(4);
    }

    public void cancelClick() {
        isChild = false;
        isScanned = false;
        childToSend = "";
        editTextScanParentStillage.setText("");
        editTextScanChildStillage.setText("");
        editTextMergeQuantity.setText("");

        editTextScanParentStillage.setEnabled(true);
        editTextScanChildStillage.setEnabled(true);

        linearLayoutScanParentDetail.setVisibility(View.GONE);
        linearLayoutScanParentDetail.setAnimation(fadeOut);

        relativeLayoutScanChildDetail.setVisibility(View.GONE);
        relativeLayoutScanChildDetail.setAnimation(fadeOut);

        buttonFinish.setVisibility(View.GONE);
        linearLayoutScanChild.setVisibility(View.GONE);
        linearLayoutMergeStillage.setVisibility(View.GONE);
        linearLayoutAssignLocationButtons.setVisibility(View.GONE);
        linearLayoutQuantitySum.setVisibility(View.GONE);
    }

    @OnClick(R.id.buttonFinish)
    public void onButtonFinishClick() {
        if (NetworkChangeReceiver.isInternetConnected(MergeStillageActivity.this)) {
            showProgress(MergeStillageActivity.this);
            UpgradeMergeInput upgradeMergeInput = new UpgradeMergeInput(editTextScanParentStillage.getText().toString().trim(), userId, childToSend, textViewQuantitySum.getText().toString(), "3");
            iMergeStillageInterface.callUpdateMergeStillage(upgradeMergeInput);
        } else {
            showSuccessDialog("No internet, stillges will not merge.");
        }
    }

    public void imageButtonHomeClick(View view) {
        if (isScanned) {
            showBackAlert(new Intent(MergeStillageActivity.this, DashBoardAcivity.class), true);
        } else {
            finishAffinity();
            startActivity(new Intent(MergeStillageActivity.this, DashBoardAcivity.class));
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

    @Override
    public void onFailure(String message) {
        hideProgress();
        showSuccessDialog(message);
        if (isChild) {
            editTextScanChildStillage.setText("");
        } else {
            editTextScanParentStillage.setText("");
        }
    }

    @Override
    public void onSuccess(ScanStillageResponse body) {
        hideProgress();
        if (body.getStatus().equals(getResources().getString(R.string.success))) {
            if (isLocationMatched(body.getWareHouseID())) {
                if (body.getIsAssignTransfer() == 0) {
                    if (isChild) {
                        if (body.getStandardQty() > 0) {
                            if (body.getIsCounted().equals(isParentRAF)) {
                                childWareHouse = body.getWareHouseName();
                                String childItem, parentItem;
                                childItem = body.getItemId().trim();
                                parentItem = parentStillageLayout.textViewitem.getText().toString().trim();

                                if (!childItem.equals(parentItem)) {
                                    showSuccessDialog(getResources().getString(R.string.child_and_parent_not_matched));
                                    editTextScanChildStillage.setText("");
                                } else if (!childWareHouse.equals(parentWareHouse)) {
                                    showSuccessDialog(getResources().getString(R.string.child_and_parent_warehouse_not_matched));
                                    editTextScanChildStillage.setText("");
                                } else {

                                    relativeLayoutScanChildDetail.setVisibility(View.VISIBLE);
                                    relativeLayoutScanChildDetail.setAnimation(fadeIn);
                                    linearLayoutMergeStillage.setVisibility(View.VISIBLE);
                                    linearLayoutMergeStillage.setAnimation(fadeIn);


                                    linearLayoutAssignLocationButtons.setVisibility(View.VISIBLE);
                                    linearLayoutAssignLocationButtons.setAnimation(fadeIn);
                                    linearLayoutQuantitySum.setVisibility(View.VISIBLE);
                                    textViewQuantitySum.setText(parentStillageLayout.textViewQuantity.getText().toString());
                                    setChildData(body);
                                    editTextScanChildStillage.setEnabled(false);
                                    editTextMergeQuantity.requestFocus();

                                    float childQty;

                                    childQty = Float.parseFloat(childStillageLayout.textViewQuantity.getText().toString());

                                    editTextMergeQuantity.setText(round(childQty) + "");

                                    editTextMergeQuantity.setSelection(editTextMergeQuantity.getText().toString().length());
                                }
                            } else {
                                editTextScanChildStillage.setText("");
                                if (isParentRAF.equals("1") && body.getIsCounted().equals("0")) {
                                    alertAttn(MergeStillageActivity.this, "Only RAF stillage can be merged as parent stillage is RAF posted.");
                                }
                                if (isParentRAF.equals("0") && body.getIsCounted().equals("1")) {
                                    alertAttn(MergeStillageActivity.this, "Only Non-RAF stillage can be merged as parent stillage is not RAF posted.");
                                }
                            }
                        } else {
                            showSuccessDialog(getResources().getString(R.string.stillage_discarded));
                            editTextScanChildStillage.setText("");
                        }
                    } else {
                        if (body.getStandardQty() > 0) {
                            isParentRAF = body.getIsCounted();

                            parentWareHouse = body.getWareHouseName();
                            linearLayoutScanParentDetail.setVisibility(View.VISIBLE);
                            linearLayoutScanParentDetail.setAnimation(fadeIn);
                            editTextScanParentStillage.setEnabled(false);
                            setParentData(body);
                            isScanned = true;
                            linearLayoutScanChild.setVisibility(View.VISIBLE);
                            linearLayoutScanChild.setAnimation(fadeIn);
                            editTextScanChildStillage.requestFocus();
                            linearLayoutAssignLocationButtons.setVisibility(View.VISIBLE);
                            linearLayoutAssignLocationButtons.setAnimation(fadeIn);
                            buttonMerge.setEnabled(false);
                        } else {
                            showSuccessDialog(getResources().getString(R.string.stillage_discarded));
                            editTextScanParentStillage.setText("");
                        }
                    }
                } else {
                    showSuccessDialog(getResources().getString(R.string.stillage_assigned_for_transfer));
                    if (isChild) {
                        editTextScanChildStillage.setText("");
                    } else {
                        editTextScanParentStillage.setText("");
                    }
                }
            } else {
                if (isChild) {
                    editTextScanChildStillage.setText("");
                } else {
                    editTextScanParentStillage.setText("");
                }
                showSuccessDialog(getResources().getString(R.string.stillage_not_found));
            }
        } else {
            showSuccessDialog(body.getMessage());
            if (isChild) {
                editTextScanChildStillage.setText("");
            } else {
                editTextScanParentStillage.setText("");
            }
        }
    }

    @Override
    public void onUpdateMergeStillageFailure(String message) {
        hideProgress();
        showSuccessDialog(message);
    }

    @Override
    public void onUpdateMergeStillageSuccess(UniversalResponse body) {
        hideProgress();
        childToSend = "";
        isScanned = false;
        showSuccessDialog(body.getMessage());
        editTextScanParentStillage.setText("");
        editTextScanChildStillage.setText("");
        editTextMergeQuantity.setText("");
        buttonFinish.setVisibility(View.GONE);

        editTextScanParentStillage.setEnabled(true);
        editTextScanChildStillage.setEnabled(true);

        linearLayoutScanParentDetail.setVisibility(View.GONE);
        linearLayoutScanParentDetail.setAnimation(fadeOut);

        relativeLayoutScanChildDetail.setVisibility(View.GONE);
        relativeLayoutScanChildDetail.setAnimation(fadeOut);

        linearLayoutScanChild.setVisibility(View.GONE);
        linearLayoutMergeStillage.setVisibility(View.GONE);
        linearLayoutAssignLocationButtons.setVisibility(View.GONE);
        linearLayoutQuantitySum.setVisibility(View.GONE);
        isChild = false;

    }

    public void imageButtonCloseClick(View view) {
        isChild = false;
        editTextScanChildStillage.requestFocus();
        editTextScanChildStillage.setText("");
        editTextScanChildStillage.setEnabled(true);
        relativeLayoutScanChildDetail.setVisibility(View.GONE);
        relativeLayoutScanChildDetail.setAnimation(fadeOut);
        linearLayoutMergeStillage.setVisibility(View.GONE);
        linearLayoutQuantitySum.setVisibility(View.GONE);
    }

    public void alertAttn(Context context, String message) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_alert_attn_message);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        CustomButton buttonOk = dialog.findViewById(R.id.buttonOk);
        TextView textViewMsg = dialog.findViewById(R.id.textViewMsg);

        textViewMsg.setText(message);

        buttonOk.setOnClickListener(v -> {
            dialog.cancel();
        });

        dialog.show();

    }


}
