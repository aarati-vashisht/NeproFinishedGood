package com.neprofinishedgood.mergestillage.presenter;

import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;

public interface IMergeStillageInterface {
    void callScanStillageService(MoveInput moveInput);

    void getScanMergeStillageResponse(ScanStillageResponse body);
}
