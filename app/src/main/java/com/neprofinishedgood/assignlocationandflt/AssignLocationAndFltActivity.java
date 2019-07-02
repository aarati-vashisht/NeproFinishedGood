package com.neprofinishedgood.assignlocationandflt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;

public class AssignLocationAndFltActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_location_and_flt);

        setTitle(getString(R.string.assign_location_and_flt));
    }
}
