package com.neprofinishedgood.workorderstartend.model;

public class WorkOrderScanInput {
    String WorkOrderNo, UserId;

    public WorkOrderScanInput(String workOrderNo, String userId) {
        WorkOrderNo = workOrderNo;
        UserId = userId;
    }
}
