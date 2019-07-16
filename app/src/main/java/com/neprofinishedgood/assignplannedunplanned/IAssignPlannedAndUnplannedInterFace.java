package com.neprofinishedgood.assignplannedunplanned;

import com.neprofinishedgood.assignplannedunplanned.model.AssignedUnAssignedInput;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.LocationData;
import com.neprofinishedgood.plannedandunplannedmove.model.LocationInput;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;

public interface IAssignPlannedAndUnplannedInterFace {
    void callScanStillageService(MoveInput moveInput);

    void getScanStillageResponse(ScanStillageResponse stillageResponse);

    void callLocationService(LocationInput locationInput);

    void getLocationData(LocationData body);

    void callAssigneUnassignedServcie(AssignedUnAssignedInput assignedUnAssignedInput);

    void getAssigneUnassigned(UniversalResponse body);
}
