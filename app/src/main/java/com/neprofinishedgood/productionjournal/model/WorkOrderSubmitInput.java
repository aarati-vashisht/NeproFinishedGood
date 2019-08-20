package com.neprofinishedgood.productionjournal.model;

import java.util.ArrayList;

public class WorkOrderSubmitInput {

    String UserId,WorkOrderNo;
    ArrayList<PickingListDatum> PickingList;
    ArrayList<RoutingListDatum> RoutingList;

    public WorkOrderSubmitInput(String userId, String workOrderNo, ArrayList<PickingListDatum> pickingListDatumList, ArrayList<RoutingListDatum> routingListDatumList) {
        this.UserId = userId;
        this.WorkOrderNo = workOrderNo;
        this.PickingList = pickingListDatumList;
        this.RoutingList = routingListDatumList;
    }
}
