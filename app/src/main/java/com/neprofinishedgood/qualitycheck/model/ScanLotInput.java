package com.neprofinishedgood.qualitycheck.model;

public class ScanLotInput {
    String WorkOrderNo, LotNo, UserId;

    public ScanLotInput(String workOrderNo, String lotNo, String userId) {
        WorkOrderNo = workOrderNo;
        LotNo = lotNo;
        UserId = userId;
    }
}
