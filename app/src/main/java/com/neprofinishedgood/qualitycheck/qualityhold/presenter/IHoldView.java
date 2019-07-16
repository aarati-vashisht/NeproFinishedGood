package com.neprofinishedgood.qualitycheck.qualityhold.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;
import com.neprofinishedgood.raf.model.ScanCountingResponse;

public interface IHoldView {
    void onFailure(String message);

    void onSuccess(ScanStillageResponse body);


    void onHoldUnholdFailure(String message);

    void onHoldUnholdSuccess(UniversalResponse body);
}
