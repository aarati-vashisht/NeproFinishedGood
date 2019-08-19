package com.neprofinishedgood.login.presenter;

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

    public ILoginPresenter(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
    }

    @Override
    public void getLoginResponse(LoginResponse body) {
        if (body == null) {
        } else {
            if (body.getStatus() == null) {
                iLoginView.onFailure();
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
