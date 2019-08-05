package com.neprofinishedgood.workorderstartend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkOrderStartEndActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_order_start_end);
        setTitle(getString(R.string.workorder_start_end));
        ButterKnife.bind(this);
    }
}
