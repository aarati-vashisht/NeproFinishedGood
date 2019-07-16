package com.neprofinishedgood.plannedandunplannedmove.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.LocationData;
import com.neprofinishedgood.plannedandunplannedmove.model.LocationInput;
import com.neprofinishedgood.plannedandunplannedmove.model.UpdateMoveLocationInput;

public interface IMoveInterface {
    void callMoveServcie(UpdateMoveLocationInput updateMoveLocationInput);

    void getUpdateMoveResponse(UniversalResponse body);

    void callLocationService(LocationInput locationInput);

    void getLocationData(LocationData body);

}
