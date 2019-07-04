package com.neprofinishedgood.mergestillage;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.counting.model.StillageDatum;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.utils.StillageLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MergeStillageActivity extends BaseActivity {

    @BindView(R.id.relativeLayoutScanChildDetail)
    RelativeLayout relativeLayoutScanChildDetail;

    @BindView(R.id.linearLayoutScanParentDetail)
    LinearLayout linearLayoutScanParentDetail;

    @BindView(R.id.parentStillageDetail)
    View parentStillageDetail;

    @BindView(R.id.childStillageDetail)
    View childStillageDetail;

    @BindView(R.id.editTextScanChildStillage)
    EditText editTextScanChildStillage;

    @BindView(R.id.editTextMergeQuantity)
    EditText editTextMergeQuantity;

    @BindView(R.id.editTextScanParentStillage)
    EditText editTextScanParentStillage;


    StillageLayout parentStillageLayout;
    StillageLayout childStillageLayout;

    Animation fadeOut;
    Animation fadeIn;

    StillageDatum parentStillageDatum;
    StillageDatum childStillageDatum;

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
        if (text.toString().equalsIgnoreCase("S000001")) {
            linearLayoutScanParentDetail.setVisibility(View.VISIBLE);
            linearLayoutScanParentDetail.setAnimation(fadeIn);
            setParentData();
            editTextScanChildStillage.requestFocus();
        } else {
            linearLayoutScanParentDetail.setVisibility(View.GONE);
            linearLayoutScanParentDetail.setAnimation(fadeOut);
        }
    }

    void setParentData() {
        parentStillageDatum = new StillageDatum();
        parentStillageDatum.setItem("1");
        parentStillageDatum.setName("S000001");
        parentStillageDatum.setNumber("S000001");
        parentStillageDatum.setQuantity("100");
        parentStillageDatum.setStdQuantity("100");
        parentStillageDatum.setStillageId("");

        parentStillageLayout.textViewitem.setText(parentStillageDatum.getItem());
        parentStillageLayout.textViewName.setText(parentStillageDatum.getName());
        parentStillageLayout.textViewNumber.setText(parentStillageDatum.getNumber());
        parentStillageLayout.textViewQuantity.setText(parentStillageDatum.getQuantity());
        parentStillageLayout.textViewStdQuatity.setText(parentStillageDatum.getStdQuantity());
    }

    @OnTextChanged(value = R.id.editTextScanChildStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanChildStillageChanged(Editable text) {
        if (text.toString().equalsIgnoreCase("S000002")) {
            relativeLayoutScanChildDetail.setVisibility(View.VISIBLE);
            relativeLayoutScanChildDetail.setAnimation(fadeIn);
            setChildData();
            editTextMergeQuantity.requestFocus();
        } else {
            relativeLayoutScanChildDetail.setVisibility(View.GONE);
            relativeLayoutScanChildDetail.setAnimation(fadeOut);
        }
    }

    void setChildData() {
        childStillageDatum = new StillageDatum();
        childStillageDatum.setItem("1");
        childStillageDatum.setName("S000002");
        childStillageDatum.setNumber("S000002");
        childStillageDatum.setQuantity("50");
        childStillageDatum.setStdQuantity("50");
        childStillageDatum.setStillageId("");

        childStillageLayout.textViewitem.setText(childStillageDatum.getItem());
        childStillageLayout.textViewName.setText(childStillageDatum.getName());
        childStillageLayout.textViewNumber.setText(childStillageDatum.getNumber());
        childStillageLayout.textViewQuantity.setText(childStillageDatum.getQuantity());
        childStillageLayout.textViewStdQuatity.setText(childStillageDatum.getStdQuantity());
    }

    @OnTextChanged(value = R.id.editTextMergeQuantity, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextMergeQuantityChanged(Editable text) {
        if (!text.toString().equals("")) {
            int mergeQty = Integer.parseInt(text.toString());
            int childQty = Integer.parseInt(childStillageLayout.textViewQuantity.getText().toString());

            if (mergeQty > childQty || mergeQty == 0) {
                editTextMergeQuantity.setError(getString(R.string.invalid_merge_quantity));
                editTextMergeQuantity.requestFocus();
            }
        }
    }

    boolean isValidated() {
        String text = editTextMergeQuantity.getText().toString();
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
        if (isValidated()) {
            CustomToast.showToast(MergeStillageActivity.this, getString(R.string.item_merged_successfully));
            finish();
        }
    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        childStillageDatum = new StillageDatum();
        parentStillageDatum = new StillageDatum();

        editTextScanParentStillage.setText("");
        editTextScanChildStillage.setText("");

        linearLayoutScanParentDetail.setVisibility(View.GONE);
        linearLayoutScanParentDetail.setAnimation(fadeOut);

        relativeLayoutScanChildDetail.setVisibility(View.GONE);
        relativeLayoutScanChildDetail.setAnimation(fadeOut);

    }
}
