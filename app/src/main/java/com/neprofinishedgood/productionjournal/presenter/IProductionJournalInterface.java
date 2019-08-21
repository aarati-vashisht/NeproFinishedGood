package com.neprofinishedgood.productionjournal.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.productionjournal.model.ProductionJournalPickinngDataInput;
import com.neprofinishedgood.productionjournal.model.ProductionJournalRouteDataInput;
import com.neprofinishedgood.productionjournal.model.WorkOrderInput;
import com.neprofinishedgood.productionjournal.model.WorkOrderResponse;

public interface IProductionJournalInterface {

    void callScanWorkOrderService(WorkOrderInput workOrderInput);

    void getWorkOrderResponse(WorkOrderResponse body);

    void callSubmitProductionJournalPickingService(ProductionJournalPickinngDataInput productionJournalPickinngDataInput);

    void getSubmitPickingResponse(UniversalResponse body);

    void callSubmitProductionJournalRouteService(ProductionJournalRouteDataInput productionJournalRouteDataInput);

    void getSubmitRouteResponse(UniversalResponse body);

}
