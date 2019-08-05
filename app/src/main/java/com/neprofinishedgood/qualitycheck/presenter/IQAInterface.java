package com.neprofinishedgood.qualitycheck.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;
import com.neprofinishedgood.qualitycheck.model.RejectedInput;
import com.neprofinishedgood.qualitycheck.model.ScanLotInput;
import com.neprofinishedgood.raf.model.ScanCountingResponse;

public interface IQAInterface {
    void callScanStillageService(MoveInput moveInput);

    void getScanStillageResponse(ScanStillageResponse body);

    void callUpdateRejectedService(RejectedInput rejectedInput);

    void getUpdateRejectedResponse(UniversalResponse body);

    void callScanLotService(ScanLotInput scanLotInput);

    void getScanLotResponse(UniversalResponse body);

}
