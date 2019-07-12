package com.neprofinishedgood.api;

import com.neprofinishedgood.login.model.LoginResponse;
import com.neprofinishedgood.login.model.LoginUser;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("Login")
    Call<LoginResponse> loginUser(@Body LoginUser loginUser);

    @POST("StillageMoving")
    Call<MoveResponse> stillageMoving(@Body MoveInput moveInput);

}
