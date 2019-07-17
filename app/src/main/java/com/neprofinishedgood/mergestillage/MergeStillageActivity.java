package com.neprofinishedgood.mergestillage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.mergestillage.model.UpgradeMergeInput;
import com.neprofinishedgood.mergestillage.presenter.IMergeStillageInterface;
import com.neprofinishedgood.mergestillage.presenter.IMergeStillageView;
import com.neprofinishedgood.mergestillage.presenter.MergeStillagePresenter;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;
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

    @BindView(R.id.textViewQuantitySum)
    TextView textViewQuantitySum;

    StillageLayout parentStillageLayout;
    StillageLayout childStillageLayout;

    int mergeQty, childQty, parentQty, parentStdQty;
    private IMergeStillageInterface iMergeStillageInterface;
    long delay = 1000;
    long scanStillageLastTexxt = 0;
    private boolean isChild = false;
    String childToSend, childWareHouse, parentWareHouse = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merge_stillage);
        ButterKnife.bind(this);
        parentStillageLayout = new StillageLayout();
        childStillageLayout = new StillageLayout();
        ButterKnife.bind(parentStillageLayout, parentStillageDetail);
        ButterKnife.bind(childStillageLayout, childStillageDetail);
        setTitle(getString(R.string.merge_stillage));
        iMergeStillageInterface = new MergeStillagePresenter(this,this);
    }


    @OnTextChanged(value = R.id.editTextScanParentStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanStillageChanged(Editable text) {
        if (!text.toString().trim().equals("")) {
            scanStillagehandler.postDelayed(stillageRunnable, delay);
        }

    }

    @OnTextChanged(value = R.id.editTextScanParentStillage, callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void onEditTextScanStillageTEXTCHANGED(Editable text) {
        scanStillagehandler.removeCallbacks(stillageRunnable);

    }

    //for call service on text change
    Handler scanStillagehandler = new Handler();
    private Runnable stillageRunnable = new Runnable() {
        public void run() {
            showProgress(MergeStillageActivity.this);
            if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
                iMergeStillageInterface.callScanStillageService(new MoveInput(editTextScanParentStillage.getText().toString().trim(), userId));

            }
        }
    };

    @OnTextChanged(value = R.id.editTextScanChildStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void editTextScanChildStillageChanged(Editable text) {
        if (!text.toString().trim().equals("")) {
            scanStillagehandler2.postDelayed(stillageRunnable2, delay);
        }

    }

    @OnTextChanged(value = R.id.editTextScanChildStillage, callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void editTextScanChildStillageTEXTCHANGED(Editable text) {
        scanStillagehandler2.removeCallbacks(stillageRunnable2);

    }

    //for call service on text change
    Handler scanStillagehandler2 = new Handler();
    private Runnable stillageRunnable2 = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > (scanStillageLastTexxt + delay - 500)) {
                isChild = true;
                if (childToSend == null) {
                    childToSend = "";
                }
                if (childToSend.length() > 0) {
                    if (!childToSend.contains(editTextScanChildStillage.getText().toString().trim())) {
                        showProgress(MergeStillageActivity.this);
                        iMergeStillageInterface.callScanStillageService(new MoveInput(editTextScanChildStillage.getText().toString().trim(), userId));
                    } else {
                        editTextScanChildStillage.setError(getString(R.string.this_stillage_already_merged));
                    }
                } else {
                    showProgress(MergeStillageActivity.this);
                    iMergeStillageInterface.callScanStillageService(new MoveInput(editTextScanChildStillage.getText().toString().trim(), userId));
                }
            }
        }
    };


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
        if (!text.toString().equals("")) {
            mergeQty = Integer.parseInt(text.toString());
            childQty = Integer.parseInt(childStillageLayout.textViewQuantity.getText().toString());
            parentQty = Integer.parseInt(parentStillageLayout.textViewQuantity.getText().toString());
            parentStdQty = Integer.parseInt(parentStillageLayout.textViewStdQuantity.getText().toString());
            if (mergeQty > childQty || mergeQty == 0) {
                editTextMergeQuantity.setError(getString(R.string.invalid_merge_quantity));
                editTextMergeQuantity.requestFocus();
            } else if ((mergeQty + parentQty) > parentStdQty) {
                editTextMergeQuantity.setError(getString(R.string.invalid_merge_quantity));
                editTextMergeQuantity.requestFocus();
            }
        }
    }

    boolean isValidated() {
        String text = editTextMergeQuantity.getText().toString().trim();
        if (!text.equals("")) {
            int mergeQty = Integer.parseInt(text);
            int childQty = Integer.parseInt(childStillageLayout.textViewQuantity.getText().toString());

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

        String childItem, parentItem;
        childItem = childStillageLayout.textViewitem.getText().toString().trim();
        parentItem = parentStillageLayout.textViewitem.getText().toString().trim();
        if (editTextScanParentStillage.getText().toString().trim().equals(editTextScanChildStillage.getText().toString().trim())) {
            CustomToast.showToast(MergeStillageActivity.this, getResources().getString(R.string.child_and_parent_not_matched));
            return false;
        }
        if (!childItem.equals(parentItem)) {
            CustomToast.showToast(MergeStillageActivity.this, getResources().getString(R.string.child_and_parent_not_matched));
            return false;
        }
        if (!childWareHouse.equals(parentWareHouse)) {
            CustomToast.showToast(MergeStillageActivity.this, getResources().getString(R.string.child_and_parent_warehouse_not_matched));
            return false;
        }
        return true;
    }

    @OnClick(R.id.buttonMerge)
    public void onButtonMergeClick() {
        if (parentQty == parentStdQty) {
            buttonMerge.setEnabled(false);
            relativeLayoutScanChildDetail.setVisibility(View.GONE);
            relativeLayoutScanChildDetail.setAnimation(fadeOut);
        } else {
            if (isValidated()) {
                if (mergeQty == childQty) {
                    showDiscardStickerAlertDialog();
                } else {
                    mergeClick("");
                }
            }
        }

    }

    private void showDiscardStickerAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Discard Sticker '" + childStillageLayout.textViewNumber.getText().toString() + "'").setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                childToSend += editTextScanChildStillage.getText().toString() + ":" + editTextMergeQuantity.getText().toString().trim() + ",";
                mergeClick(getResources().getString(R.string.discard));
            }
        }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                childToSend += editTextScanChildStillage.getText().toString() + ":" + editTextMergeQuantity.getText().toString().trim() + ",";
                mergeClick(getResources().getString(R.string.nodiscard));
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    void mergeClick(String discard) {
        String changeParentQty = mergeQty + parentQty + "";
        parentStillageLayout.textViewQuantity.setText(changeParentQty);
        String changeChildQty = Integer.parseInt(childStillageLayout.textViewQuantity.getText().toString()) - mergeQty + "";
        childStillageLayout.textViewQuantity.setText(changeChildQty);
        if ((mergeQty + parentQty) == parentStdQty) {
            //nothing to merge parent is full
            //call service to add
            childToSend += editTextScanChildStillage.getText().toString() + ":" + editTextMergeQuantity.getText().toString().trim() + ",";
            linearLayoutScanChild.setVisibility(View.VISIBLE);
            relativeLayoutScanChildDetail.setVisibility(View.GONE);
            relativeLayoutScanChildDetail.setAnimation(fadeOut);
            buttonMerge.setText(getString(R.string.merge));
            linearLayoutMergeStillage.setVisibility(View.GONE);
            linearLayoutMergeStillage.setAnimation(fadeOut);
            linearLayoutAssignLocationButtons.setAnimation(fadeOut);
            linearLayoutAssignLocationButtons.setVisibility(View.VISIBLE);
            showProgress(this);
            UpgradeMergeInput upgradeMergeInput = new UpgradeMergeInput(editTextScanParentStillage.getText().toString().trim(), userId, childToSend, textViewQuantitySum.getText().toString());
            iMergeStillageInterface.callUpdateMergeStillage(upgradeMergeInput);
        } else {
            ///add more items to merge
            showAddMoreALert();
        }
        textViewQuantitySum.setText(mergeQty + parentQty + "");
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
                linearLayoutAssignLocationButtons.setVisibility(View.GONE);
                linearLayoutAssignLocationButtons.setAnimation(fadeOut);
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.exit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                /// call merge service
                childToSend += editTextScanChildStillage.getText().toString() + ":" + editTextMergeQuantity.getText().toString().trim() + ",";
                showProgress(MergeStillageActivity.this);
                UpgradeMergeInput upgradeMergeInput = new UpgradeMergeInput(editTextScanParentStillage.getText().toString().trim(), userId, childToSend, textViewQuantitySum.getText().toString());
                iMergeStillageInterface.callUpdateMergeStillage(upgradeMergeInput);
                dialogInterface.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        isChild = false;
        editTextScanParentStillage.setText("");
        editTextScanChildStillage.setText("");
        editTextMergeQuantity.setText("");

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


    }

    @Override
    public void onFailure(String message) {
        hideProgress();
        CustomToast.showToast(this, message);
    }

    @Override
    public void onSuccess(ScanStillageResponse body) {
        hideProgress();
        //  CustomToast.showToast(this, body.getMessage());
        if (isChild) {
            childWareHouse = body.getWareHouseName();
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

            int parentRemainQty, childQty;

            parentRemainQty = Integer.parseInt(parentStillageLayout.textViewStdQuantity.getText().toString())
                    - Integer.parseInt(parentStillageLayout.textViewQuantity.getText().toString());

            childQty = Integer.parseInt(childStillageLayout.textViewQuantity.getText().toString());

            if (childQty <= parentRemainQty) {
                editTextMergeQuantity.setText(childQty + "");
            } else if (childQty > parentRemainQty) {
                editTextMergeQuantity.setText(parentRemainQty + "");
            }
            editTextMergeQuantity.setSelection(editTextMergeQuantity.getText().toString().length());
        } else {
            parentWareHouse = body.getWareHouseName();
            linearLayoutScanParentDetail.setVisibility(View.VISIBLE);
            linearLayoutScanParentDetail.setAnimation(fadeIn);
            linearLayoutScanChild.setVisibility(View.VISIBLE);
            linearLayoutScanChild.setAnimation(fadeIn);
            setParentData(body);
            editTextScanParentStillage.setEnabled(false);
            editTextScanChildStillage.requestFocus();
        }
    }

    @Override
    public void onUpdateMergeStillageFailure(String message) {
        hideProgress();
        CustomToast.showToast(this, message);

    }

    @Override
    public void onUpdateMergeStillageSuccess(UniversalResponse body) {
        hideProgress();
        childToSend = "";
        CustomToast.showToast(this, body.getMessage());
        editTextScanParentStillage.setText("");
        editTextScanChildStillage.setText("");
        editTextMergeQuantity.setText("");

        editTextScanParentStillage.setEnabled(true);
        editTextScanChildStillage.setEnabled(false);

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
        linearLayoutAssignLocationButtons.setVisibility(View.GONE);
        linearLayoutQuantitySum.setVisibility(View.GONE);
    }
}
