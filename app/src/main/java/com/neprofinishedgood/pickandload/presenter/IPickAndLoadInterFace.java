package com.neprofinishedgood.pickandload.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.pickandload.model.LoadingPlanInput;
import com.neprofinishedgood.pickandload.model.LoadingPlanResponse;
import com.neprofinishedgood.move.model.AllAssignedDataInput;

public interface IPickAndLoadInterFace {
    void callGetLoadingPlan(AllAssignedDataInput allAssignedDataInput);

    void getGetLoadingPlanResponse(LoadingPlanResponse body);


    void callCancelLoadingPlan(LoadingPlanInput loadingPlanInput);

    void getCancelLoadingResponse(UniversalResponse body);
}
