package com.neprofinishedgood.move.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.LocationData;
import com.neprofinishedgood.move.model.ScanStillageResponse;

public interface IMoveView {

    void onUpdateMoveSuccess(UniversalResponse response);

    void onUpdateMoveFailure(String message);

    void onLocationSuccess(LocationData response);

    void onLocationFailure(String message);

    void onAisleSelectionSuccess(ScanStillageResponse body);
    void onAisleSelectionFailure(String message);

    void onRackSelectionSuccess(ScanStillageResponse body);
    void onRackSelectionFailure(String message);

}
