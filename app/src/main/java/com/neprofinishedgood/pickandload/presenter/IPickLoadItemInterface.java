package com.neprofinishedgood.pickandload.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.pickandload.model.LoadingPlanDetails;
import com.neprofinishedgood.pickandload.model.LoadingPlanInput;
import com.neprofinishedgood.pickandload.model.UpdateLoadInput;

public interface IPickLoadItemInterface {
    void callGetLoadingPlanDetails(LoadingPlanInput loadingPlanInput);

    void getGetLoadingPlanDetailsResponse(LoadingPlanDetails body);

    void callUpdateLoadService(UpdateLoadInput updateLoadInput);

    void getUpdateLoadingPlanDetailsResponse(UniversalResponse body);

}
