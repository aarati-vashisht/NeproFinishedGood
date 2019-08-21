package com.neprofinishedgood.productionjournal.model;

import java.util.ArrayList;

public class ProductionJournalRouteDataInput {

    String WorkOrderNo, UserId, ItemId;

    ArrayList<RouteCardPicked> RoutingCardList;

    public ProductionJournalRouteDataInput(String workOrderNo, String userId, String itemId, ArrayList<RouteCardPicked> routingCardListData) {
        WorkOrderNo = workOrderNo;
        UserId = userId;
        ItemId = itemId;
        RoutingCardList = routingCardListData;
    }
}
