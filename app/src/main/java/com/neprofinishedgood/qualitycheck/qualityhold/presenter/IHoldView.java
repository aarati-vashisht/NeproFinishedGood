package com.neprofinishedgood.qualitycheck.qualityhold.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.ScanStillageResponse;

public interface IHoldView {
    void onFailure(String message);

    void onSuccess(ScanStillageResponse body);


    void onHoldUnholdFailure(String message);

    void onHoldUnholdSuccess(UniversalResponse body);
}
