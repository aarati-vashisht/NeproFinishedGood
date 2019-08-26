package com.neprofinishedgood.api;

import com.neprofinishedgood.assign.model.AssignedUnAssignedInput;
import com.neprofinishedgood.base.model.MasterData;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.login.model.LoginResponse;
import com.neprofinishedgood.login.model.LoginUser;
import com.neprofinishedgood.mergestillage.model.UpgradeMergeInput;
import com.neprofinishedgood.pickandload.model.LoadingPlanDetails;
import com.neprofinishedgood.pickandload.model.LoadingPlanInput;
import com.neprofinishedgood.pickandload.model.LoadingPlanResponse;
import com.neprofinishedgood.pickandload.model.UpdateLoadInput;
import com.neprofinishedgood.move.model.AllAssignedDataInput;
import com.neprofinishedgood.move.model.AssignedStillages;
import com.neprofinishedgood.move.model.LocationData;
import com.neprofinishedgood.move.model.LocationInput;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.move.model.UpdateMoveLocationInput;
import com.neprofinishedgood.productionjournal.model.PickingListSearchInput;
import com.neprofinishedgood.productionjournal.model.PickingListSearchResponse;
import com.neprofinishedgood.productionjournal.model.ProductionJournalPickinngDataInput;
import com.neprofinishedgood.productionjournal.model.ProductionJournalRouteDataInput;
import com.neprofinishedgood.productionjournal.model.WorkOrderInput;
import com.neprofinishedgood.productionjournal.model.WorkOrderResponse;
import com.neprofinishedgood.qualitycheck.model.QualityInput;
import com.neprofinishedgood.qualitycheck.model.RejectedCompleteInput;
import com.neprofinishedgood.qualitycheck.model.RejectedInput;
import com.neprofinishedgood.qualitycheck.model.ScanLotInput;
import com.neprofinishedgood.raf.model.RafInput;
import com.neprofinishedgood.transferstillage.model.TransferInput;
import com.neprofinishedgood.updatequantity.model.UpdateQtyInput;
import com.neprofinishedgood.workorderstartend.model.WorkOrderScanInput;
import com.neprofinishedgood.workorderstartend.model.WorkOrderScanResponse;

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

    @POST("CompleteRejectedStillage")
    Call<UniversalResponse> completeRejectedStillage(@Body RejectedCompleteInput rejectedCompleteInput);

    @POST("UpdatedHoldUnHoldStillage")
    Call<UniversalResponse> updatedHoldUnHoldStillage(@Body QualityInput qualityInput);

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

    @POST("UpdateStillageDetails")
    Call<UniversalResponse> updateStillageDetails(@Body UpdateQtyInput updateQtyInput);

    @POST("GetLoadingPlan")
    Call<LoadingPlanResponse> getLoadingPlan(@Body AllAssignedDataInput allAssignedDataInput);

    @POST("GetLoadingPlanDetails")
    Call<LoadingPlanDetails> getLoadingPlanDetails(@Body LoadingPlanInput loadingPlanDetailInput);

    @POST("UpdatePickAndLoading")
    Call<UniversalResponse> getupdateLoadInput(@Body UpdateLoadInput updateLoadInput);



    @POST("EndPick")
    Call<UniversalResponse> endPickInput(@Body LoadingPlanInput loadingPlanInput);

    @POST("CancelLoading")
    Call<UniversalResponse> cancelLoadingInput(@Body LoadingPlanInput loadingPlanInput);

    @POST("GetProductionJournalData")
    Call<WorkOrderResponse> getProductionJournalData(@Body WorkOrderInput workOrderInput);

    @POST("SearchItemProcess")
    Call<PickingListSearchResponse> searchItemProcessService(@Body PickingListSearchInput pickingListSearchInput);

    @POST("LotScan")
    Call<UniversalResponse> lotScan(@Body ScanLotInput scanLotInput);

    @POST("WorkOrder_Start_EndProcess")
    Call<WorkOrderScanResponse> workOrderStartEndScan(@Body WorkOrderScanInput workOrderScanInput);

    @POST("WorkOrderStartService")
    Call<UniversalResponse> workOrderStartService(@Body WorkOrderScanInput workOrderScanInput);

    @POST("WorkOrderEndService")
    Call<UniversalResponse> workOrderEndService(@Body WorkOrderScanInput workOrderScanInput);

    @POST("SubmitPickingJournalData")
    Call<UniversalResponse> submitPickingJournalData(@Body ProductionJournalPickinngDataInput productionJournalPickinngDataInput);

    @POST("SubmitRouteJournalData")
    Call<UniversalResponse> submitRouteJournalData(@Body ProductionJournalRouteDataInput productionJournalRouteDataInput);

}
