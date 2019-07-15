package com.neprofinishedgood.mergestillage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatEditText;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.raf.model.StillageDatum;
import com.neprofinishedgood.utils.StillageLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MergeStillageActivity extends BaseActivity {

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
    StillageLayout parentStillageLayout;
    StillageLayout childStillageLayout;

    Animation fadeOut;
    Animation fadeIn;

    StillageDatum parentStillageDatum;
    StillageDatum childStillageDatum;
    int mergeQty, childQty, parentQty, parentStdQty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merge_stillage);

        ButterKnife.bind(this);
        setTitle(getString(R.string.merge_stillage));

        initData();
    }

    void initData() {

        parentStillageLayout = new StillageLayout();
        childStillageLayout = new StillageLayout();

        ButterKnife.bind(parentStillageLayout, parentStillageDetail);
        ButterKnife.bind(childStillageLayout, childStillageDetail);

        fadeOut = AnimationUtils.loadAnimation(MergeStillageActivity.this, R.anim.animate_fade_out);
        fadeIn = AnimationUtils.loadAnimation(MergeStillageActivity.this, R.anim.animate_fade_in);
    }

    @OnTextChanged(value = R.id.editTextScanParentStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanParentStillageChanged(Editable text) {
        if (text.toString().equalsIgnoreCase("S00001")) {
            linearLayoutScanParentDetail.setVisibility(View.VISIBLE);
            linearLayoutScanParentDetail.setAnimation(fadeIn);
            linearLayoutScanChild.setVisibility(View.VISIBLE);
            linearLayoutScanChild.setAnimation(fadeIn);
            setParentData();
            editTextScanParentStillage.setEnabled(false);
            editTextScanChildStillage.requestFocus();
        } else {
            linearLayoutScanParentDetail.setVisibility(View.GONE);
            linearLayoutScanParentDetail.setAnimation(fadeOut);
        }
    }

    void setParentData() {
        parentStillageDatum = new StillageDatum();
        parentStillageDatum.setItem("1");
        parentStillageDatum.setName("S00001");
        parentStillageDatum.setNumber("S00001");
        parentStillageDatum.setQuantity("30");
        parentStillageDatum.setStdQuantity("100");
        parentStillageDatum.setStillageId("");

        parentStillageLayout.textViewitem.setText(parentStillageDatum.getItem());
        parentStillageLayout.textViewNumber.setText(parentStillageDatum.getNumber());
        parentStillageLayout.textViewQuantity.setText(parentStillageDatum.getQuantity());
        parentStillageLayout.textViewStdQuantity.setText(parentStillageDatum.getStdQuantity());
    }

    @OnTextChanged(value = R.id.editTextScanChildStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanChildStillageChanged(Editable text) {
        if (text.toString().equalsIgnoreCase("S00002")) {
            relativeLayoutScanChildDetail.setVisibility(View.VISIBLE);
            relativeLayoutScanChildDetail.setAnimation(fadeIn);
            linearLayoutMergeStillage.setVisibility(View.VISIBLE);
            linearLayoutMergeStillage.setAnimation(fadeIn);
            linearLayoutAssignLocationButtons.setVisibility(View.VISIBLE);
            linearLayoutAssignLocationButtons.setAnimation(fadeIn);
            setChildData();
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
            relativeLayoutScanChildDetail.setVisibility(View.GONE);
            relativeLayoutScanChildDetail.setAnimation(fadeOut);
        }
    }

    void setChildData() {
        childStillageDatum = new StillageDatum();
        childStillageDatum.setItem("1");
        childStillageDatum.setName("S00002");
        childStillageDatum.setNumber("S00002");
        childStillageDatum.setQuantity("50");
        childStillageDatum.setStdQuantity("50");
        childStillageDatum.setStillageId("");

        childStillageLayout.textViewitem.setText(childStillageDatum.getItem());
        childStillageLayout.textViewNumber.setText(childStillageDatum.getNumber());
        childStillageLayout.textViewQuantity.setText(childStillageDatum.getQuantity());
        childStillageLayout.textViewStdQuantity.setText(childStillageDatum.getStdQuantity());
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
            return false;
        }

        return true;
    }

    @OnClick(R.id.buttonMerge)
    public void onButtonMergeClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (parentQty == parentStdQty) {
            buttonMerge.setEnabled(false);
            relativeLayoutScanChildDetail.setVisibility(View.GONE);
            relativeLayoutScanChildDetail.setAnimation(fadeOut);
        } else {
            if (isValidated()) {
                String changeParentQty = mergeQty + parentQty + "";
                parentStillageDatum.setQuantity(changeParentQty);
                parentStillageLayout.textViewQuantity.setText(changeParentQty);

                String changeChildQty = Integer.parseInt(childStillageLayout.textViewQuantity.getText().toString()) - mergeQty + "";
                childStillageDatum.setQuantity(changeChildQty);
                childStillageLayout.textViewQuantity.setText(changeChildQty);

                if ((mergeQty + parentQty) == parentStdQty) {
                    linearLayoutScanChild.setVisibility(View.GONE);
                    relativeLayoutScanChildDetail.setVisibility(View.GONE);
                    relativeLayoutScanChildDetail.setAnimation(fadeOut);
                    buttonMerge.setText(getString(R.string.merge));
                    linearLayoutMergeStillage.setVisibility(View.GONE);
                    linearLayoutMergeStillage.setAnimation(fadeOut);
                    linearLayoutAssignLocationButtons.setAnimation(fadeOut);
                    linearLayoutAssignLocationButtons.setVisibility(View.VISIBLE);
                    CustomToast.showToast(MergeStillageActivity.this, getString(R.string.data_saved_successfully));
                    finish();
                } else {
                    builder.setMessage(R.string.stillage_merge_message).setCancelable(false);
                    builder.setPositiveButton(getResources().getString(R.string.add_more), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
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
                            finish();
                            CustomToast.showToast(MergeStillageActivity.this, getString(R.string.data_saved_successfully));
                            dialogInterface.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            } else {
                editTextMergeQuantity.setError(getString(R.string.enter_merge_quantity));
            }
        }
    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        childStillageDatum = new StillageDatum();
        parentStillageDatum = new StillageDatum();

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

    }
}
