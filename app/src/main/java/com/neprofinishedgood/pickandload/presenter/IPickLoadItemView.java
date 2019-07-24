package com.neprofinishedgood.pickandload.presenter;

import com.neprofinishedgood.pickandload.model.LoadingPlanDetails;

public interface IPickLoadItemView {
    void onFailure(String message);

    void onSuccess(LoadingPlanDetails body);
}
