package com.neprofinishedgood.qualitycheck.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;
import com.neprofinishedgood.raf.model.ScanCountingResponse;

public interface IQAView {
    void onFailure(String message);

    void onSuccess(ScanStillageResponse body);

    void onUpdateRejectedFailure(String message);

    void onUpdateRejectedSuccess(UniversalResponse body);

    void onLotScanFailure(String message);

    void onLotScanSuccess(UniversalResponse body);
}
