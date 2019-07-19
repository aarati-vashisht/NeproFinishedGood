package com.neprofinishedgood.plannedandunplannedmove.presenter;

import com.neprofinishedgood.plannedandunplannedmove.model.AllAssignedDataInput;
import com.neprofinishedgood.plannedandunplannedmove.model.AssignedStillages;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.UpdateMoveLocationInput;

public interface IPLannedUnplannedInterface {
    void callGetAllAssignedData(AllAssignedDataInput allAssignedDataInput);
    void getAllAssignedData(AssignedStillages body);
    void callScanStillageService(MoveInput moveInput);
    void getMoveResponse(ScanStillageResponse body);

    void callMoveServcie(UpdateMoveLocationInput updateMoveLocationInput);

}
