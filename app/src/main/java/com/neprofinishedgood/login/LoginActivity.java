package com.neprofinishedgood.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.chaos.view.PinView;
import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.dashboard.DashBoardAcivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.buttonLogin)
    Button buttonLogin;
    @BindView(R.id.pinViewLogin)
    PinView pinViewLogin;
    @BindView(R.id.coordinateLayout)
    CoordinatorLayout coordinateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        overridePendingTransition(0, 0);

    }

    @OnClick(R.id.buttonLogin)
    public void onLoginButtonClick() {
        if (pinViewLogin.getText().toString().equals("")) {
            CustomToast.showToast(this, getString(R.string.plz_enter_pin));
        } else if (!pinViewLogin.getText().toString().equals("1234")) {
            CustomToast.showToast(this, getString(R.string.invalid_pin));
        } else if (pinViewLogin.getText().toString().equals("1234")) {
            CustomToast.showToast(this, getString(R.string.login_successfully));
            startActivity(new Intent(this, DashBoardAcivity.class));
            finishAffinity();
            overridePendingTransition(0, 0);
        }
    }

}
