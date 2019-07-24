package com.neprofinishedgood.pickandload.presenter;

import com.neprofinishedgood.pickandload.model.LoadingPlanDetailInput;
import com.neprofinishedgood.pickandload.model.LoadingPlanDetailResponse;

public interface ILoadingPlanDetailInterface {
    void callLoadingPlanDetailService(LoadingPlanDetailInput loadingPlanDetailInput);

    void getLoadingPlanDetailResponse(LoadingPlanDetailResponse body);
}
