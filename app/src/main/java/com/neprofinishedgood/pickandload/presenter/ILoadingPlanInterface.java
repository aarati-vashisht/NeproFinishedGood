package com.neprofinishedgood.pickandload.presenter;

import com.neprofinishedgood.pickandload.model.LoadingPlanResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;

public interface ILoadingPlanInterface {
    void callLoadingPlanService(MoveInput moveInput);

    void getLoadingPlanResponse(LoadingPlanResponse body);
}
