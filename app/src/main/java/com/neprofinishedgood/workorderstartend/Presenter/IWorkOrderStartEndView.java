package com.neprofinishedgood.workorderstartend.Presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.workorderstartend.model.WorkOrderScanResponse;

public interface IWorkOrderStartEndView {

    void onWorkOrderScanFailure(String message);

    void onWorkOrderScanSuccess(WorkOrderScanResponse body);

    void onWorkOrderStartFailure(String message);

    void onWorkOrderStartSuccess(UniversalResponse body);

    void onWorkOrderEndFailure(String message);

    void onWorkOrderEndSuccess(UniversalResponse body);
}
