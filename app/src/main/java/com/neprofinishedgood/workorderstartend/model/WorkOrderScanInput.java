package com.neprofinishedgood.workorderstartend.model;

public class WorkOrderScanInput {
    private String WorkOrderNo, UserId;
    private String AutoRoute;
    private String AutoPick;
    private String StartQty;

    public WorkOrderScanInput(String workOrderNo, String userId, String autoRoute, String autoPick, String startQty) {
        WorkOrderNo = workOrderNo;
        UserId = userId;
        AutoRoute = autoRoute;
        AutoPick = autoPick;
        StartQty = startQty;
    }

    //    public WorkOrderScanInput(String workOrderNo, String userId) {
//        WorkOrderNo = workOrderNo;
//        UserId = userId;
//    }
}
