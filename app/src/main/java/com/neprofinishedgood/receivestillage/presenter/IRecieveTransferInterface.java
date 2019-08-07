package com.neprofinishedgood.receivestillage.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;

public interface IRecieveTransferInterface {
    void callScanStillageService(MoveInput moveInput);

    void getScanMergeStillageResponse(ScanStillageResponse body);

    void callUpdateRecieveTransferStillage(MoveInput moveInput);


    void getUpdateRecieveTransferStillageResponse(UniversalResponse body);
}
