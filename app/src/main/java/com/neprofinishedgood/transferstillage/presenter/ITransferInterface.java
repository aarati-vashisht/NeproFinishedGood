package com.neprofinishedgood.transferstillage.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;
import com.neprofinishedgood.transferstillage.model.TransferInput;

public interface ITransferInterface {
    void callScanStillageService(MoveInput moveInput);

    void getScanMergeStillageResponse(ScanStillageResponse body);

    void callUpdateTransferStillage(TransferInput transferInput);

    void getUpdateTransferStillageResponse(UniversalResponse body);
}
