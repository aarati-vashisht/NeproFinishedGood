package com.neprofinishedgood.productionjournal.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.productionjournal.model.ProductionJournalDataInput;
import com.neprofinishedgood.productionjournal.model.WorkOrderInput;
import com.neprofinishedgood.productionjournal.model.WorkOrderResponse;
import com.neprofinishedgood.productionjournal.model.WorkOrderSubmitInput;

public interface IProductionJournalInterface {

    void callScanWorkOrderService(WorkOrderInput workOrderInput);

    void getWorkOrderResponse(WorkOrderResponse body);

    void callSubmitProductionJournalService(ProductionJournalDataInput productionJournalDataInput);

    void getSubmitWorkOrderResponse(UniversalResponse body);

}
