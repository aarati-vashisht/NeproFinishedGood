package com.neprofinishedgood.receivereturnstillage;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.counting.model.StillageDatum;
import com.neprofinishedgood.returnstillage.ReturnStillageActivity;
import com.neprofinishedgood.utils.StillageLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReceiveReturnStillage extends BaseActivity {

    @BindView(R.id.relativeLayoutScanDetail)
    RelativeLayout relativeLayoutScanDetail;

    @BindView(R.id.stillageDetail)
    View stillageDetail;

    StillageLayout stillageLayout;

    StillageDatum stillageDatum;

    Animation fadeOut;
    Animation fadeIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_return_stillage);

        ButterKnife.bind(this);
        setTitle(getString(R.string.recieve_return_stillage));
        initData();
    }

    void initData() {
        stillageLayout = new StillageLayout();
        ButterKnife.bind(stillageLayout, stillageDetail);

        fadeOut = AnimationUtils.loadAnimation(ReceiveReturnStillage.this, R.anim.animate_fade_out);
        fadeIn = AnimationUtils.loadAnimation(ReceiveReturnStillage.this, R.anim.animate_fade_in);
    }
}
