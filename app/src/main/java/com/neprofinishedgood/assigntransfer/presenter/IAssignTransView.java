package com.neprofinishedgood.assigntransfer.presenter;

import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.transferstillage.model.WareHouseResponse;

public interface IAssignTransView {

    void onFailure(String message);

    void onSuccess(ScanStillageResponse body);

    void onGetWareHouseFailure(String message);

    void onGetWareHouseSuccess(WareHouseResponse body);

}
