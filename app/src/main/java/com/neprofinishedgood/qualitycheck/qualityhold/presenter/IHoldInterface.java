package com.neprofinishedgood.qualitycheck.qualityhold.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;
import com.neprofinishedgood.raf.model.ScanCountingResponse;

public interface IHoldInterface {
    void callScanStillageService(MoveInput moveInput);

    void getScanStillageResponse(ScanStillageResponse body);

    void callHoldUnholdService(MoveInput moveInput);

    void getHoldUnholdResponse(UniversalResponse body);

}
