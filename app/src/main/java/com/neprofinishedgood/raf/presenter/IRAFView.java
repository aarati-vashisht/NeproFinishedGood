package com.neprofinishedgood.raf.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;

public interface IRAFView {
    void onFailure(String message);

    void onSuccess(ScanStillageResponse body);

    void onUpdateRAFFailure(String message);

    void onUpdateRAFSuccess(UniversalResponse body);
}
