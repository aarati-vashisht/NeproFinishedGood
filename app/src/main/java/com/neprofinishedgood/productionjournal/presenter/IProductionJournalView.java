package com.neprofinishedgood.productionjournal.presenter;

import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;
import com.neprofinishedgood.productionjournal.model.WorkOrderResponse;

public interface IProductionJournalView {
    void onFailure(String message);

    void onSuccess(WorkOrderResponse body);
}
