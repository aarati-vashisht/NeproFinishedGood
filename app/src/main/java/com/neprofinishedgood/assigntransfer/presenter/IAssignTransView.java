package com.neprofinishedgood.assigntransfer.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.transferstillage.model.LocationResponse;
import com.neprofinishedgood.transferstillage.model.WareHouseResponse;

public interface IAssignTransView {

    void onFailure(String message);

    void onSuccess(ScanStillageResponse body);

    void onGetWareHouseFailure(String message);

    void onGetWareHouseSuccess(WareHouseResponse body);

    void onGetLocationFailure(String message);

    void onGetLocationSuccess(LocationResponse body);

    void onUpdateAssignTransFailure(String message);

    void onUpdateAssignTransSuccess(UniversalResponse body);

}
