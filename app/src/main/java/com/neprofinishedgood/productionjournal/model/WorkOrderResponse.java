package com.neprofinishedgood.productionjournal.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkOrderResponse {

    @SerializedName("PickingListData")
    @Expose
    private ArrayList<PickingListDatum> pickingListData = null;
    @SerializedName("RoutingListData")
    @Expose
    private ArrayList<RoutingListDatum> routingListData = null;
    @SerializedName("ItemId")
    @Expose
    private String itemId;
    @SerializedName("ItemName")
    @Expose
    private String itemName;
    @SerializedName("Quantity")
    @Expose
    private String quantity;
    @SerializedName("WorkOrderNo")
    @Expose
    private String workOrderNo;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("WareHouseId")
    @Expose
    private String wareHouseId;
    @SerializedName("SiteID")
    @Expose
    private String siteID;

    public ArrayList<PickingListDatum> getPickingListData() {
        return pickingListData;
    }

    public void setPickingListData(ArrayList<PickingListDatum> pickingListData) {
        this.pickingListData = pickingListData;
    }

    public ArrayList<RoutingListDatum> getRoutingListData() {
        return routingListData;
    }

    public void setRoutingListData(ArrayList<RoutingListDatum> routingListData) {
        this.routingListData = routingListData;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getWorkOrderNo() {
        return workOrderNo;
    }

    public void setWorkOrderNo(String workOrderNo) {
        this.workOrderNo = workOrderNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getWareHouseId() {
        return wareHouseId;
    }

    public void setWareHouseId(String wareHouseId) {
        this.wareHouseId = wareHouseId;
    }

    public String getSiteID() {
        return siteID;
    }

    public void setSiteID(String siteID) {
        this.siteID = siteID;
    }
}