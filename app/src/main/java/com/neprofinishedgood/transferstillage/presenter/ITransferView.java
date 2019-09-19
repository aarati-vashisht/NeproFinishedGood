package com.neprofinishedgood.transferstillage.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.transferstillage.model.WareHouseResponse;

public interface ITransferView {
    void onFailure(String message);

    void onSuccess(ScanStillageResponse body);

    void onUpdateTransferFailure(String message);

    void onUpdateTransferSuccess(UniversalResponse body);

    void onShipFailure(String message);

    void onShipSuccess(UniversalResponse body);

    void onGetWareHouseFailure(String message);

    void onGetWareHouseSuccess(WareHouseResponse body);
}
