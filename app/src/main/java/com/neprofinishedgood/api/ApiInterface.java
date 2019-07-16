package com.neprofinishedgood.api;

import com.neprofinishedgood.base.model.MasterData;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.login.model.LoginResponse;
import com.neprofinishedgood.login.model.LoginUser;
import com.neprofinishedgood.plannedandunplannedmove.model.AllAssignedDataInput;
import com.neprofinishedgood.plannedandunplannedmove.model.AssignedStillages;
import com.neprofinishedgood.plannedandunplannedmove.model.LocationData;
import com.neprofinishedgood.plannedandunplannedmove.model.LocationInput;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.UpdateMoveLocationInput;
import com.neprofinishedgood.qualitycheck.model.RejectedInput;
import com.neprofinishedgood.raf.model.RafInput;
import com.neprofinishedgood.raf.model.ScanCountingResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("Login")
    Call<LoginResponse> loginUser(@Body LoginUser loginUser);

    @POST("StillageMoving")
    Call<ScanStillageResponse> stillageMoving(@Body MoveInput moveInput);

    @POST("UpdateMovingStatus")
    Call<UniversalResponse> updateMovingStatus(@Body UpdateMoveLocationInput moveInput);

    @POST("MasterData")
    Call<MasterData> masterData();

    @POST("MovingStillageList")
    Call<AssignedStillages> getAllAssignedStillages(@Body AllAssignedDataInput moveInput);

    @POST("GetLocationDetails")
    Call<LocationData> callLocationService(@Body LocationInput locationInput);

    @POST("ScanStillageCount")
    Call<ScanCountingResponse> scanStillageCount(@Body MoveInput moveInput);

    @POST("UpdateRAF")
    Call<UniversalResponse> updateRAF(@Body RafInput rafInput);


    @POST("RejectedStillageDetails")
    Call<ScanCountingResponse> rejectedStillageDetails(@Body MoveInput moveInput);

    @POST("UpdatedRejectedStillage")
    Call<UniversalResponse> updatedRejectedStillage(@Body RejectedInput rejectedInput);

    @POST("UpdatedHoldUnHoldStillage")
    Call<UniversalResponse> updatedHoldUnHoldStillage(@Body MoveInput moveInput);
}
