package com.neprofinishedgood.pickandload.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.pickandload.model.LoadingPlanDetails;

public interface IPickLoadItemView {
    void onFailure(String message);

    void onSuccess(LoadingPlanDetails body);

    void onUpdateLoadingPlanDetailsFailure(String string);

    void onUpdateLoadingPlanDetailsSuccess(UniversalResponse body);

    void onEndPickFailure(String string);

    void onEndPickSuccess(UniversalResponse body);
}
