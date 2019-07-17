package com.neprofinishedgood.receivereturnstillage.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;
import com.neprofinishedgood.receivereturnstillage.model.TransferInput;

public interface IRecieveTransferInterface {
    void callScanStillageService(MoveInput moveInput);

    void getScanMergeStillageResponse(ScanStillageResponse body);

    void callUpdateRecieveTransferStillage(MoveInput moveInput);


    void getUpdateRecieveTransferStillageResponse(UniversalResponse body);
}
