package com.neprofinishedgood.putaway;


import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PutAwayActivity extends BaseActivity {


    @BindView(R.id.frameEnterQuantity)
    FrameLayout frameEnterQuantity;

    @BindView(R.id.frameAssignFlt)
    FrameLayout frameAssignFlt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_away);
        ButterKnife.bind(this);
        setTitle(getString(R.string.put_away));
    }

    @OnClick(R.id.buttonConfirm)
    public void onButtonConfirmClick() {
        frameEnterQuantity.setVisibility(View.GONE);
        frameAssignFlt.setVisibility(View.VISIBLE);
    }
}
