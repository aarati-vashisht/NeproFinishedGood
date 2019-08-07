package com.neprofinishedgood.qualitycheck.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.qualitycheck.model.RejectedInput;

public interface IQAInterface {
    void callScanStillageService(MoveInput moveInput);

    void getScanStillageResponse(ScanStillageResponse body);

    void callUpdateRejectedService(RejectedInput rejectedInput);

    void getUpdateRejectedResponse(UniversalResponse body);

}
