package com.neprofinishedgood.receivestillage.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;

public interface IRecieveTransferView {
    void onFailure(String message);

    void onSuccess(ScanStillageResponse body);

    void onUpdateRecieveTransferFailure(String message);

    void onUpdateRecieveTransferSuccess(UniversalResponse body);
}
