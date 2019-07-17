package com.neprofinishedgood.assignplannedunplanned.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.LocationData;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;

public interface IAssignePlannedUnplannedView {

    void onFailure(String message);

    void onSuccess(ScanStillageResponse body);

    void onLocationFailure(String message);

    void onLocationSuccess(LocationData body);

    void onAssigneUnassignedFailure(String message);

    void onAssigneUnassignedSuccess(UniversalResponse body);
}
