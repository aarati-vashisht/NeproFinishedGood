package com.neprofinishedgood.plannedandunplannedmove.presenter;

import com.neprofinishedgood.plannedandunplannedmove.model.AssignedStillages;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;

public interface IPlannedAndUnPlannedView {
    void onAssignedFailure(String message);

    void onAssignedSuccess(AssignedStillages body);

    void onSuccess(ScanStillageResponse body);

    void onFailure(String message);

}
