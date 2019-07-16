package com.neprofinishedgood.qualitycheck.qualityholdandmove.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.raf.model.ScanCountingResponse;

public interface IHoldInterface {
    void callScanStillageService(MoveInput moveInput);

    void getScanStillageResponse(ScanCountingResponse body);

    void callHoldUnholdService(MoveInput moveInput);

    void getHoldUnholdResponse(UniversalResponse body);

}