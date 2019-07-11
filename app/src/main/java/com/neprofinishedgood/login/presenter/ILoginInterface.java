package com.neprofinishedgood.login.presenter;

import com.neprofinishedgood.login.model.LoginResponse;
import com.neprofinishedgood.login.model.LoginUser;

public interface ILoginInterface {

    void getLoginResponse(LoginResponse body);


    void callLoginService(LoginUser loginUser);
}
