package com.neprofinishedgood.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.chaos.view.PinView;
import com.google.gson.Gson;
import com.neprofinishedgood.R;
import com.neprofinishedgood.assign.AssignActivity;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.custom_views.CustomToast;
import com.neprofinishedgood.dashboard.DashBoardAcivity;
import com.neprofinishedgood.login.model.LoginResponse;
import com.neprofinishedgood.login.model.LoginUser;
import com.neprofinishedgood.login.presenter.ILoginInterface;
import com.neprofinishedgood.login.presenter.ILoginPresenter;
import com.neprofinishedgood.login.presenter.ILoginView;
import com.neprofinishedgood.utils.NetworkChangeReceiver;
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
    @BindView(R.id.imgShowPass)
    ImageView imgShowPass;
    @BindView(R.id.imgHidePass)
    ImageView imgHidePass;
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
        iLoginInterface = new ILoginPresenter(this, this);
        SharedPref.clearPrefs();
    }

    @OnClick(R.id.buttonLogin)
    public void onLoginButtonClick() {
        if (isValidate()) {
            if (NetworkChangeReceiver.isInternetConnected(LoginActivity.this)) {
                showProgress(this);
                LoginUser loginUser = new LoginUser(pinViewLogin.getText().toString());
                iLoginInterface.callLoginService(loginUser);
            }
            else{
                showSuccessDialog(getString(R.string.no_internet));
            }
        }

    }

    private boolean isValidate() {
        if (pinViewLogin.getText().toString().equals("") || pinViewLogin.getText().toString().length() != 4) {
            showSuccessDialog(getString(R.string.plz_enter_pin));
//            CustomToast.showToast(this, getString(R.string.plz_enter_pin));
            return false;
        }
        return true;

    }

    @OnClick(R.id.imgShowPass)
    void onShowPassClick(){
        pinViewLogin.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        imgShowPass.setVisibility(View.GONE);
        imgHidePass.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.imgHidePass)
    void onHidePassClick(){
        pinViewLogin.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        imgShowPass.setVisibility(View.VISIBLE);
        imgHidePass.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess(LoginResponse body) {
        hideProgress();
        if (body.getStatus().equals(getResources().getString(R.string.success))) {
            CustomToast.showToast(this, getString(R.string.login_successfully));
            Gson gson = new Gson();
            String loginData = gson.toJson(body);
            SharedPref.saveLoginUSer(loginData);
            startActivity(new Intent(this, DashBoardAcivity.class));
            finishAffinity();
        } else {
            showSuccessDialog(body.getMessage());
            pinViewLogin.requestFocus(3);
            pinViewLogin.setText("");
        }

    }

    @Override
    public void onFailure(String message) {
        hideProgress();
        showSuccessDialog(message);
        pinViewLogin.setText("");
        pinViewLogin.requestFocus(3);
//        CustomToast.showToast(this, message);
    }
}
