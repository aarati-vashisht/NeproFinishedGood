package com.neprofinishedgood.assign.presenter;

import com.neprofinishedgood.assign.model.AssignedUnAssignedInput;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.LocationData;
import com.neprofinishedgood.move.model.LocationInput;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;

public interface IAssignInterFace {
    void callScanStillageService(MoveInput moveInput);

    void getScanStillageResponse(ScanStillageResponse stillageResponse);

    void callLocationService(LocationInput locationInput);

    void getLocationData(LocationData body);

    void callAssigneUnassignedServcie(AssignedUnAssignedInput assignedUnAssignedInput);

    void getAssigneUnassigned(UniversalResponse body);
}
