package com.neprofinishedgood.plannedandunplannedmove.presenter;

import com.neprofinishedgood.plannedandunplannedmove.model.AssignedStillages;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveResponse;

public interface IPlannedAndUnPlannedView {
    void onAssignedFailure();

    void onAssignedSuccess(AssignedStillages body);

    void onSuccess(MoveResponse body);

    void onFailure();

}
