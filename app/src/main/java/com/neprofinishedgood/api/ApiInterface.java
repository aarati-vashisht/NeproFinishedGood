package com.neprofinishedgood.api;

import com.neprofinishedgood.login.model.LoginResponse;
import com.neprofinishedgood.login.model.LoginUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("Login")
    Call<LoginResponse> loginUser(@Body LoginUser insertrefrence);

}
