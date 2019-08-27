package com.neprofinishedgood.move.presenter;

import com.neprofinishedgood.assign.model.AisleInput;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.LocationData;
import com.neprofinishedgood.move.model.LocationInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.move.model.UpdateMoveLocationInput;

public interface IMoveInterface {
    void callMoveServcie(UpdateMoveLocationInput updateMoveLocationInput);

    void getUpdateMoveResponse(UniversalResponse body);

    void callLocationService(LocationInput locationInput);

    void getLocationData(LocationData body);

    void callAisleSelectionService(AisleInput aisleInput);
    void getAisleSelectionResponse(ScanStillageResponse stillageResponse);

    void callRackSelectionService(AisleInput aisleInput);
    void getRackSelectionResponse(ScanStillageResponse stillageResponse);

}
