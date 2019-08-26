package com.neprofinishedgood.qualitycheck.qualityhold.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.qualitycheck.model.QualityInput;

public interface IHoldInterface {
    void callScanStillageService(MoveInput moveInput);

    void getScanStillageResponse(ScanStillageResponse body);

    void callHoldUnholdService(QualityInput qualityInput);

    void getHoldUnholdResponse(UniversalResponse body);

}
