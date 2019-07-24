package com.neprofinishedgood.pickandload.presenter;

import com.neprofinishedgood.pickandload.model.LoadingPlanDetailResponse;

public interface ILoadingPlanDetailView {
    void onLoadingPlanDetailSuccess(LoadingPlanDetailResponse response);

    void onLoadingPlanDetailFailure(String message);
}
