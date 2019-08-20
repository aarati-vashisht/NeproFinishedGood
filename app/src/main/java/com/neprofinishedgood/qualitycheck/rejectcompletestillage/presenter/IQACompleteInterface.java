package com.neprofinishedgood.qualitycheck.rejectcompletestillage.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.qualitycheck.model.RejectedCompleteInput;
import com.neprofinishedgood.qualitycheck.model.RejectedInput;

public interface IQACompleteInterface {
    void callScanStillageService(MoveInput moveInput);

    void getScanStillageResponse(ScanStillageResponse body);

    void callUpdateRejectedService(RejectedCompleteInput rejectedCompleteInput);

    void getUpdateRejectedResponse(UniversalResponse body);

}
