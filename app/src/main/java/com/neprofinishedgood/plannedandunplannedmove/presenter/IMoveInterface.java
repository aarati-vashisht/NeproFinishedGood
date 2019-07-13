package com.neprofinishedgood.plannedandunplannedmove.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.AllAssignedDataInput;
import com.neprofinishedgood.plannedandunplannedmove.model.AssignedStillages;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.UpdateMoveLocationInput;

public interface IMoveInterface {
    void callMoveServcie(UpdateMoveLocationInput updateMoveLocationInput);
    void getUpdateMoveResponse(UniversalResponse body);

}
