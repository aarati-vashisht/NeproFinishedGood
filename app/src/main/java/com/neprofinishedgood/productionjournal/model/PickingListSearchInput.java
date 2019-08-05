package com.neprofinishedgood.productionjournal.model;

public class PickingListSearchInput {

    String WorkOrderNo, UserId, ItemName;

    public PickingListSearchInput(String workOrderNo, String userId, String itemName) {
        WorkOrderNo = workOrderNo;
        UserId = userId;
        ItemName = itemName;
    }
}
