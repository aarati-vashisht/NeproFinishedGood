package com.neprofinishedgood.productionjournal.model;

public class PickingListSearchInput {

    String WorkOrderId, UserId, ItemName;

    public PickingListSearchInput(String workOrderId, String userId, String itemName) {
        WorkOrderId = workOrderId;
        UserId = userId;
        ItemName = itemName;
    }
}
