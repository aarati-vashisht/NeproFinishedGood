package com.neprofinishedgood.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.chaos.view.PinView;
import com.google.gson.Gson;
import com.neprofinishedgood.R;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.dashboard.DashBoardAcivity;
import com.neprofinishedgood.login.model.LoginResponse;
import com.neprofinishedgood.login.model.LoginUser;
import com.neprofinishedgood.login.presenter.ILoginInterface;
import com.neprofinishedgood.login.presenter.ILoginPresenter;
import com.neprofinishedgood.login.presenter.ILoginView;
import com.neprofinishedgood.utils.SharedPref;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements ILoginView {
    public static LoginActivity instance;
    @BindView(R.id.buttonLogin)
    Button buttonLogin;
    @BindView(R.id.pinViewLogin)
    PinView pinViewLogin;
    @BindView(R.id.coordinateLayout)
    CoordinatorLayout coordinateLayout;
    ILoginInterface iLoginInterface;

    public static LoginActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        instance = this;
        getSupportActionBar().hide();
        overridePendingTransition(0, 0);
        iLoginInterface = new ILoginPresenter(this);

    }

    @OnClick(R.id.buttonLogin)
    public void onLoginButtonClick() {
        if (isValidate()) {
            showProgress(this);
            LoginUser loginUser = new LoginUser(pinViewLogin.getText().toString());
            iLoginInterface.callLoginService(loginUser);
        }

    }

    private boolean isValidate() {
        if (pinViewLogin.getText().toString().equals("")) {
            CustomToast.showToast(this, getString(R.string.plz_enter_pin));
            return false;
        }
        return true;

    }

    @Override
    public void onSuccess(LoginResponse body) {
        hideProgress();
        if (body.getStatus().equals(getResources().getString(R.string.success))) {
            CustomToast.showToast(this, getString(R.string.login_successfully));
            Gson gson = new Gson();
            String loginData = gson.toJson(body);
            SharedPref.saveLoginUSer(this, loginData);
            startActivity(new Intent(this, DashBoardAcivity.class));
            finishAffinity();
        } else {
            CustomToast.showToast(this, getString(R.string.invalid_pin));
            pinViewLogin.requestFocus(3);
        }

    }

    @Override
    public void onFailure() {
        hideProgress();
        CustomToast.showToast(this, getString(R.string.invalid_pin));
    }
}
