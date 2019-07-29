package com.neprofinishedgood.pickandload.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.pickandload.model.LoadingPlanResponse;

public interface IPickAndLoadVIew {
    void onFailure(String string);

    void onSuccess(LoadingPlanResponse body);

    void onCancelFailure(String string);

    void onCancelSuccess(UniversalResponse body);
}
