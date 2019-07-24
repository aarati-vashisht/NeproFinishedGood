package com.neprofinishedgood.pickandload.presenter;

import com.neprofinishedgood.pickandload.model.LoadingPlanResponse;

public interface ILoadingPlanView {
    void onLoadingPlanSuccess(LoadingPlanResponse response);

    void onLoadingPlanFailure(String message);
}
