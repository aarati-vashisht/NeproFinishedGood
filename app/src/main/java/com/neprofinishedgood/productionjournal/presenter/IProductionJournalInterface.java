package com.neprofinishedgood.productionjournal.presenter;

import com.neprofinishedgood.productionjournal.model.WorkOrderInput;
import com.neprofinishedgood.productionjournal.model.WorkOrderResponse;

public interface IProductionJournalInterface {

    void callScanWorkOrderService(WorkOrderInput workOrderInput);

    void getWorkOrderResponse(WorkOrderResponse body);

}
