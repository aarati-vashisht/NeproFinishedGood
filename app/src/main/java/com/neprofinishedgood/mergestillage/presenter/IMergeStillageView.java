package com.neprofinishedgood.mergestillage.presenter;

import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;

public interface IMergeStillageView {
    void onFailure(String message);

    void onSuccess(ScanStillageResponse body);
}
