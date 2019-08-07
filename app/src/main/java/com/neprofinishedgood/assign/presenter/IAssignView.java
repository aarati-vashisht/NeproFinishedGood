package com.neprofinishedgood.assign.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.LocationData;
import com.neprofinishedgood.move.model.ScanStillageResponse;

public interface IAssignView {

    void onFailure(String message);

    void onSuccess(ScanStillageResponse body);

    void onLocationFailure(String message);

    void onLocationSuccess(LocationData body);

    void onAssigneUnassignedFailure(String message);

    void onAssigneUnassignedSuccess(UniversalResponse body);
}
