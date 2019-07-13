package com.neprofinishedgood.plannedandunplannedmove.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.AssignedStillages;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveResponse;

public interface IMoveView {

    void onUpdateMoveSuccess(UniversalResponse response);

    void onUpdateMoveFailure();


}
