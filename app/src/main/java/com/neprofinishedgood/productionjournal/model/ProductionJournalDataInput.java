package com.neprofinishedgood.productionjournal.model;

import java.util.ArrayList;

public class ProductionJournalDataInput {

    String WorkOrderNo, UserId, ItemId;

    ArrayList<ItemPicked> PickingList;

    ArrayList<ItemPicked> RoutingCardList;

    public ProductionJournalDataInput(String workOrderNo, String userId, String itemId, ArrayList<ItemPicked> pickingListData, ArrayList<ItemPicked> routingCardListData) {
        WorkOrderNo = workOrderNo;
        UserId = userId;
        ItemId = itemId;
        PickingList = pickingListData;
        RoutingCardList = routingCardListData;
    }
}
