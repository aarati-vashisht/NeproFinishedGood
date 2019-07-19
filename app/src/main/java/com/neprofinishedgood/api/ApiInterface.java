package com.neprofinishedgood.api;

import com.neprofinishedgood.assignplannedunplanned.model.AssignedUnAssignedInput;
import com.neprofinishedgood.base.model.MasterData;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.login.model.LoginResponse;
import com.neprofinishedgood.login.model.LoginUser;
import com.neprofinishedgood.mergestillage.model.UpgradeMergeInput;
import com.neprofinishedgood.pickandload.model.LoadingPlanDetails;
import com.neprofinishedgood.pickandload.model.LoadingPlanInput;
import com.neprofinishedgood.pickandload.model.LoadingPlanResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.AllAssignedDataInput;
import com.neprofinishedgood.plannedandunplannedmove.model.AssignedStillages;
import com.neprofinishedgood.plannedandunplannedmove.model.LocationData;
import com.neprofinishedgood.plannedandunplannedmove.model.LocationInput;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.UpdateMoveLocationInput;
import com.neprofinishedgood.qualitycheck.model.RejectedInput;
import com.neprofinishedgood.raf.model.RafInput;
import com.neprofinishedgood.transferstillage.model.TransferInput;
import com.neprofinishedgood.updatequantity.model.UpdateQtyInput;

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
    Call<ScanStillageResponse> scanStillageCount(@Body MoveInput moveInput);

    @POST("UpdateRAF")
    Call<UniversalResponse> updateRAF(@Body RafInput rafInput);

    @POST("RejectedStillageDetails")
    Call<ScanStillageResponse> rejectedStillageDetails(@Body MoveInput moveInput);

    @POST("UpdatedRejectedStillage")
    Call<UniversalResponse> updatedRejectedStillage(@Body RejectedInput rejectedInput);

    @POST("UpdatedHoldUnHoldStillage")
    Call<UniversalResponse> updatedHoldUnHoldStillage(@Body MoveInput moveInput);

    @POST("GetAssigningData")
    Call<ScanStillageResponse> getAssigningData(@Body MoveInput moveInput);

    @POST("UpdateAssigningData")
    Call<UniversalResponse> updateAssigningData(@Body AssignedUnAssignedInput assignedUnAssignedInput);

    @POST("ScanLookUpStillage")
    Call<ScanStillageResponse> scanLookUpStillage(@Body MoveInput moveInput);

    @POST("ScanMergeStillage")
    Call<ScanStillageResponse> scanMergeStillage(@Body MoveInput moveInput);

    @POST("UpdateMergeStillage")
    Call<UniversalResponse> updateMergeStillage(@Body UpgradeMergeInput upgradeMergeInput);

    @POST("UpdateTransferStillage")
    Call<UniversalResponse> updateTransferStillage(@Body TransferInput moveInput);

    @POST("ScanRecievedTransfer")
    Call<ScanStillageResponse> scanRecievedTransfer(@Body MoveInput moveInput);

    @POST("UpdateRecievedTransfer")
    Call<UniversalResponse> updateRecievedTransfer(@Body MoveInput moveInput);

    @POST("ScanUpdateQuantity")
    Call<ScanStillageResponse> scanUpdateQuantity(@Body MoveInput moveInput);

    @POST("UpdateStillageQuantity")
    Call<UniversalResponse> updateStillageQuantity(@Body UpdateQtyInput updateQtyInput);

    @POST("GetLoadingPlan")
    Call<LoadingPlanResponse> getLoadingPlan(@Body AllAssignedDataInput allAssignedDataInput);

    @POST("GetLoadingPlanDetails")
    Call<LoadingPlanDetails> getLoadingPlanDetails(@Body LoadingPlanInput loadingPlanInput);
}
