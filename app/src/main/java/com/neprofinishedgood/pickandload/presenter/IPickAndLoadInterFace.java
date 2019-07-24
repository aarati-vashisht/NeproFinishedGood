package com.neprofinishedgood.pickandload.presenter;

import com.neprofinishedgood.pickandload.model.LoadingPlanResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.AllAssignedDataInput;

public interface IPickAndLoadInterFace {
    void callGetLoadingPlan(AllAssignedDataInput allAssignedDataInput);

    void getGetLoadingPlanResponse(LoadingPlanResponse body);
}
