package com.neprofinishedgood.productionjournal.model;

import java.util.ArrayList;

public class WorkOrderSubmitInput {

    String UserId,WorkOrderNo;
    ArrayList<PickingModel> PickingList;
    ArrayList<RouteModel> RoutingList;

    public WorkOrderSubmitInput(String userId, String workOrderNo, ArrayList<PickingModel> pickingModelList, ArrayList<RouteModel> routeModelList) {
        this.UserId = userId;
        this.WorkOrderNo = workOrderNo;
        this.PickingList = pickingModelList;
        this.RoutingList = routeModelList;
    }
}
