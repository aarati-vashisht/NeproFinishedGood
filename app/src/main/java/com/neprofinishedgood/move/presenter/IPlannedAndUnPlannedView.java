package com.neprofinishedgood.move.presenter;

import com.neprofinishedgood.move.model.AssignedStillages;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.move.model.TransferDetailResponseModel;
import com.neprofinishedgood.move.model.TransferListResponseModel;

public interface IPlannedAndUnPlannedView {

    void onAssignedFailure(String message);

    void onAssignedSuccess(AssignedStillages body);

    void onSuccess(ScanStillageResponse body);

    void onFailure(String message);

    void onGetTransListHeaderSuccess(TransferListResponseModel transferListResponseModel);

    void onGetTransListHeaderFailure(String message);

    void onGetTransListDetailSuccess(TransferDetailResponseModel transferDetailResponseModel);

    void onGetTransListDetailFailure(String message);

}
