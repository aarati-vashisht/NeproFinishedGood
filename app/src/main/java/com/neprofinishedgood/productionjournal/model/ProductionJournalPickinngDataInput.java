package com.neprofinishedgood.productionjournal.model;

import java.util.ArrayList;

public class ProductionJournalPickinngDataInput {

    String WorkOrderNo, UserId, ItemId;

    ArrayList<ItemPicked> PickingList;

    public ProductionJournalPickinngDataInput(String workOrderNo, String userId, String itemId, ArrayList<ItemPicked> pickingListData) {
        WorkOrderNo = workOrderNo;
        UserId = userId;
        ItemId = itemId;
        PickingList = pickingListData;
    }
}
