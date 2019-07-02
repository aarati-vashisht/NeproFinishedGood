package com.neprofinishedgood.rejectquantity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.counting.model.StillageDatum;
import com.neprofinishedgood.putaway.PutAwayActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static com.neprofinishedgood.utils.Constants.SELECTED_STILLAGE;

public class RejectQuantityActivity extends BaseActivity {

    @BindView(R.id.linearLayoutScanDetail)
    LinearLayout linearLayoutScanDetail;

    @BindView(R.id.textViewName)
    TextView textViewName;

    @BindView(R.id.textViewitem)
    TextView textViewitem;

    @BindView(R.id.textViewNumber)
    TextView textViewNumber;

    @BindView(R.id.textViewQuantity)
    TextView textViewQuantity;

    @BindView(R.id.textViewStdQuatity)
    TextView textViewStdQuatity;

    @BindView(R.id.editTextRejectQuantity)
    EditText editTextRejectQuantity;

    @BindView(R.id.spinnerRejectReason)
    Spinner spinnerRejectReason;

    @BindView(R.id.buttonReject)
    Button buttonReject;

    Animation fadeOut;
    Animation fadeIn;

    float stillageQtyNo = 0;
    float editQtyNo = 0;

    StillageDatum stillageDatum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reject_quantity);

        ButterKnife.bind(this);

        setTitle(getString(R.string.reject_quantity));
    }

    void initData() {
        fadeOut = AnimationUtils.loadAnimation(RejectQuantityActivity.this, R.anim.animate_fade_out);
        fadeIn = AnimationUtils.loadAnimation(RejectQuantityActivity.this, R.anim.animate_fade_in);
    }

    @OnTextChanged(value = R.id.editTextScanStillage, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextScanStillageChanged(Editable text) {
        if (text.toString().equalsIgnoreCase("S000001")) {
            linearLayoutScanDetail.setVisibility(View.VISIBLE);
            linearLayoutScanDetail.setAnimation(fadeIn);
            setData();
        } else {
            linearLayoutScanDetail.setVisibility(View.GONE);
            linearLayoutScanDetail.setAnimation(fadeOut);
        }
    }

    @OnTextChanged(value = R.id.editTextRejectQuantity, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onEditTextRejectQuantityChanged(Editable text){
        String stillageQty, editQty;
        stillageQty = textViewQuantity.getText().toString();
        editQty = text.toString();

        stillageQtyNo = Float.parseFloat(stillageQty);

        if (!editQty.equals("")) {
            editQtyNo = Float.parseFloat(editQty);
            if(editQtyNo == 0){
                buttonReject.setEnabled(false);
            }
            if (editQtyNo > stillageQtyNo) {
                editTextRejectQuantity.setError(getResources().getString(R.string.quantity_must_not_greater_than_stillage_qty));
                editTextRejectQuantity.requestFocus();
                buttonReject.setEnabled(false);
            } else {
                buttonReject.setEnabled(true);
            }
        } else {
            editTextRejectQuantity.setError("Quantity must not blank!");
            editTextRejectQuantity.requestFocus();
            buttonReject.setEnabled(false);
        }
    }

    void setData() {
//        Gson gson = new Gson();
//        StillageDatum stillageDatum = gson.fromJson(JsonString, StillageDatum.class);
        stillageDatum = new StillageDatum();
        stillageDatum.setItem("1");
        stillageDatum.setName("S000001");
        stillageDatum.setNumber("S000001");
        stillageDatum.setQuantity("100");
        stillageDatum.setStdQuantity("100");
        stillageDatum.setStillageId("");

        textViewitem.setText(stillageDatum.getItem());
        textViewName.setText(stillageDatum.getName());
        textViewNumber.setText(stillageDatum.getNumber());
        textViewQuantity.setText(stillageDatum.getQuantity());
        textViewStdQuatity.setText(stillageDatum.getStdQuantity());
    }

    @OnClick(R.id.buttonReject)
    public void onButtonRejectClick() {
        if (isValidated()) {
            float oldQty = Float.parseFloat(stillageDatum.getQuantity());
            float lessQty = Float.parseFloat(editTextRejectQuantity.getText().toString());

            stillageDatum.setQuantity(oldQty-lessQty+"");
        }
    }

    @OnClick(R.id.buttonCancel)
    public void onButtonCancelClick() {
        linearLayoutScanDetail.setVisibility(View.GONE);
        linearLayoutScanDetail.setAnimation(fadeOut);
    }

    boolean isValidated() {
        if (editTextRejectQuantity.getText().toString().equals("0")) {
            editTextRejectQuantity.setError(getResources().getString(R.string.enter_reject_quantity));
            editTextRejectQuantity.requestFocus();
            return false;
        }
        if (spinnerRejectReason.getSelectedItem().toString().equals("Select Reason")) {
            TextView textView = (TextView) spinnerRejectReason.getSelectedView();
            textView.setError(getString(R.string.select_reason));
            textView.requestFocus();
            return false;
        }
        return true;
    }
}
