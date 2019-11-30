package com.neprofinishedgood.move.presenter;

import com.neprofinishedgood.move.model.AllAssignedDataInput;
import com.neprofinishedgood.move.model.AssignedStillages;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.move.model.TransferDetailInputModel;
import com.neprofinishedgood.move.model.TransferDetailResponseModel;
import com.neprofinishedgood.move.model.TransferListResponseModel;
import com.neprofinishedgood.move.model.UpdateMoveLocationInput;

public interface IPLannedUnplannedInterface {

    void callGetAllAssignedData(AllAssignedDataInput allAssignedDataInput);

    void getAllAssignedData(AssignedStillages body);

    void callScanStillageService(MoveInput moveInput);

    void getMoveResponse(ScanStillageResponse body);

    void callMoveServcie(UpdateMoveLocationInput updateMoveLocationInput);

    void callGetAssignTransferHeader(AllAssignedDataInput allAssignedDataInput);

    void getAssignTransferHeader(TransferListResponseModel body);

    void callGetAssignTransferDetail(TransferDetailInputModel transferDetailInputModel);

    void getAssignTransferDetail(TransferDetailResponseModel body);

}
