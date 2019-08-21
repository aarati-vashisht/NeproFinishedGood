package com.neprofinishedgood.productionjournal.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.productionjournal.model.WorkOrderResponse;

public interface IProductionJournalView {
    void onFailure(String message);

    void onSuccess(WorkOrderResponse body);

    void onSubmitPickingProcessFailure(String message);

    void onSubmitPickingProcessSuccess(UniversalResponse body);

    void onSubmitRouteProcessFailure(String message);

    void onSubmitRouteProcessSuccess(UniversalResponse body);
}
