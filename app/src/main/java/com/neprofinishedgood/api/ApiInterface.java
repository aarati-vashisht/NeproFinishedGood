package com.neprofinishedgood.api;

import com.neprofinishedgood.base.model.MasterData;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.login.model.LoginResponse;
import com.neprofinishedgood.login.model.LoginUser;
import com.neprofinishedgood.plannedandunplannedmove.model.AllAssignedDataInput;
import com.neprofinishedgood.plannedandunplannedmove.model.AssignedStillages;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.UpdateMoveLocationInput;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("Login")
    Call<LoginResponse> loginUser(@Body LoginUser loginUser);

    @POST("StillageMoving")
    Call<MoveResponse> stillageMoving(@Body MoveInput moveInput);

    @POST("UpdateMovingStatus")
    Call<UniversalResponse> updateMovingStatus(@Body UpdateMoveLocationInput moveInput);

    @POST("MasterData")
    Call<MasterData> masterData();

    @POST("MovingStillageList")
    Call<AssignedStillages> getAllAssignedStillages(@Body AllAssignedDataInput moveInput);


}
