package com.neprofinishedgood.transferstillage.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.ScanStillageResponse;

public interface ITransferView {
    void onFailure(String message);

    void onSuccess(ScanStillageResponse body);

    void onUpdateTransferFailure(String message);

    void onUpdateTransferSuccess(UniversalResponse body);
}
