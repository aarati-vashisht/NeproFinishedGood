package com.neprofinishedgood.qualitycheck.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.qualitycheck.model.RejectedInput;
import com.neprofinishedgood.raf.model.ScanCountingResponse;

public interface IQAInterface {
    void callScanStillageService(MoveInput moveInput);

    void getScanStillageResponse(ScanCountingResponse body);

    void callUpdateRejectedService(RejectedInput rejectedInput);

    void getUpdateRejectedResponse(UniversalResponse body);

}