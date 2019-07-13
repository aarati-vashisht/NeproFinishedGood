package com.neprofinishedgood.plannedandunplannedmove.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.AllAssignedDataInput;
import com.neprofinishedgood.plannedandunplannedmove.model.AssignedStillages;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.UpdateMoveLocationInput;

public interface IPLannedUnplannedInterface {


    void callGetAllAssignedData(AllAssignedDataInput allAssignedDataInput);
    void getAllAssignedData(AssignedStillages body);
    void callGetMoveDataService(MoveInput moveInput);
    void getMoveResponse(MoveResponse body);
}
