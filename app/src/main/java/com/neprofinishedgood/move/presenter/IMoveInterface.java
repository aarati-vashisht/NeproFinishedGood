package com.neprofinishedgood.move.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.LocationData;
import com.neprofinishedgood.move.model.LocationInput;
import com.neprofinishedgood.move.model.UpdateMoveLocationInput;

public interface IMoveInterface {
    void callMoveServcie(UpdateMoveLocationInput updateMoveLocationInput);

    void getUpdateMoveResponse(UniversalResponse body);

    void callLocationService(LocationInput locationInput);

    void getLocationData(LocationData body);

}
