package com.neprofinishedgood.plannedandunplannedmove.presenter;

import com.neprofinishedgood.login.model.LoginResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveResponse;

public interface IMoveView {
    void onSuccess(MoveResponse body);

    void onFailure();
}
