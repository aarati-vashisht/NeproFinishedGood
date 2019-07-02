package com.neprofinishedgood.mergestillage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;

public class MergeStillageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merge_stillage);

        setTitle(getString(R.string.merge_stillage));
    }
}
