package com.neprofinishedgood.pickandload.presenter;

import com.neprofinishedgood.pickandload.model.LoadingPlanDetails;
import com.neprofinishedgood.pickandload.model.LoadingPlanInput;

public interface IPickLoadItemInterface {
    void callGetLoadingPlanDetails(LoadingPlanInput loadingPlanInput);

    void getGetLoadingPlanDetailsResponse(LoadingPlanDetails body);
}
