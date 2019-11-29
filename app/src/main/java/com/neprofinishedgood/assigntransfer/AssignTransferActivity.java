package com.neprofinishedgood.assigntransfer;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.custom_views.CustomButton;
import com.neprofinishedgood.transferstillage.presenter.TransferPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AssignTransferActivity extends BaseActivity {

    @BindView(R.id.editTextScanStillage)
    EditText editTextScanStillage;
    private String makeTJ = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_transfer);

        ButterKnife.bind(this);

        setTitle(getString(R.string.assign_planned_transfer));
//        iTransferInterface = new TransferPresenter(this, this);
        editTextScanStillage.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        initAlert(this);
    }

    public void initAlert(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_alert_to_tj_selection);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        CustomButton buttonOk = dialog.findViewById(R.id.buttonOk);
        RadioGroup radioGroupTransferType = dialog.findViewById(R.id.radioGroupTransferType);
        RadioButton radioButtonTJ = dialog.findViewById(R.id.radioButtonTJ);
        RadioButton radioButtonTO = dialog.findViewById(R.id.radioButtonTO);
        LinearLayout linearLayoutTransType = dialog.findViewById(R.id.linearLayoutTransType);
        LinearLayout linearLayoutLocationSelection = dialog.findViewById(R.id.linearLayoutLocationSelection);
        TextView textViewHead = dialog.findViewById(R.id.textViewHead);

        radioGroupTransferType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == radioButtonTJ.getId()) {
                makeTJ = "1";
            } else if (checkedId == radioButtonTO.getId()) {
                makeTJ = "0";
            }
        });

        radioButtonTJ.setChecked(true);

        buttonOk.setOnClickListener(v -> {
            if(linearLayoutTransType.getVisibility() == View.VISIBLE){
                linearLayoutTransType.setVisibility(View.GONE);
                linearLayoutLocationSelection.setVisibility(View.VISIBLE);
                textViewHead.setText(getResources().getString(R.string.select_site_warehouse));
            }
            else{
                dialog.cancel();
            }
        });

        dialog.show();
    }
}
