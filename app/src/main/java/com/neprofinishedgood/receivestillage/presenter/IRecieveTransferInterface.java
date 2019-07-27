package com.neprofinishedgood.receivestillage.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;

public interface IRecieveTransferInterface {
    void callScanStillageService(MoveInput moveInput);

    void getScanMergeStillageResponse(ScanStillageResponse body);

    void callUpdateRecieveTransferStillage(MoveInput moveInput);


    void getUpdateRecieveTransferStillageResponse(UniversalResponse body);
}
