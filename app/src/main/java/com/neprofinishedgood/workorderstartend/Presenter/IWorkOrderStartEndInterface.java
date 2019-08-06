package com.neprofinishedgood.workorderstartend.Presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.workorderstartend.model.WorkOrderScanInput;
import com.neprofinishedgood.workorderstartend.model.WorkOrderScanResponse;

public interface IWorkOrderStartEndInterface {

    void callScanWorkOrderService(WorkOrderScanInput workOrderScanInput);

    void getScanWorkOrderResponse(WorkOrderScanResponse body);

    void callWorkOrderStartService(WorkOrderScanInput workOrderScanInput);

    void getWorkOrderStartResponse(UniversalResponse body);

    void callWorkOrderEndService(WorkOrderScanInput workOrderScanInput);

    void getWorkOrderEndResponse(UniversalResponse body);
}
