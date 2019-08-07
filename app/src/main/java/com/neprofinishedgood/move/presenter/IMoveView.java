package com.neprofinishedgood.move.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.LocationData;

public interface IMoveView {

    void onUpdateMoveSuccess(UniversalResponse response);

    void onUpdateMoveFailure(String message);

    void onLocationSuccess(LocationData response);

    void onLocationFailure(String message);

}
