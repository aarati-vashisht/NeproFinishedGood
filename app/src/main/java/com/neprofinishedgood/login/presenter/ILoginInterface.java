package com.neprofinishedgood.login.presenter;

public interface ILoginInterface {
    void setLoginUser();

    void getLoginSuccess();

    void getLoginFailure();

    void callLoginService();
}
