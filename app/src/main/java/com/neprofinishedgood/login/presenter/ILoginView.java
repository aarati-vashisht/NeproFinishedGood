package com.neprofinishedgood.login.presenter;

import com.neprofinishedgood.login.model.LoginResponse;

public interface ILoginView {
    void onSuccess(LoginResponse body);

    void onFailure();
}
