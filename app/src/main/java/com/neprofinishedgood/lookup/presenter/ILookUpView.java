package com.neprofinishedgood.lookup.presenter;

import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;

public interface ILookUpView {
    void onFailure(String message);

    void onSuccess(ScanStillageResponse body);
}
