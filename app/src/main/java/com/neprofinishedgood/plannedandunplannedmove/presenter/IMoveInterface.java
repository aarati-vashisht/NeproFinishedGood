package com.neprofinishedgood.plannedandunplannedmove.presenter;

import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveResponse;

public interface IMoveInterface {

    void getMoveResponse(MoveResponse body);


    void callMoveService(MoveInput moveInput);
}
