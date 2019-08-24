package com.neprofinishedgood.login.presenter;

import android.app.Activity;

import com.neprofinishedgood.R;
import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.base.BaseActivity;
import com.neprofinishedgood.login.model.LoginResponse;
import com.neprofinishedgood.login.model.LoginUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ILoginPresenter extends BaseActivity implements ILoginInterface {
    ILoginView iLoginView;
    Activity activity;

    public ILoginPresenter(ILoginView iLoginView, Activity activity) {
        this.iLoginView = iLoginView;
        this.activity = activity;
    }

    @Override
    public void getLoginResponse(LoginResponse body) {
        if (body == null) {
            iLoginView.onFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            if (body.getStatus() == null) {
                iLoginView.onFailure(activity.getString(R.string.something_went_wrong_please_try_again));
            } else {
                iLoginView.onSuccess(body);
            }
        }
    }


    @Override
    public void callLoginService(LoginUser loginUser) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiInterface.loginUser(loginUser);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                getLoginResponse(response.body());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                getLoginResponse(null);
            }
        });

    }
}
