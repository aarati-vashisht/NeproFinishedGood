package com.neprofinishedgood.productionjournal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PickingListSearchResponse {

    @SerializedName("ItemName")
    @Expose
    private String itemName;
    @SerializedName("WorkOrderId")
    @Expose
    private String workOrderId;
    @SerializedName("ItemId")
    @Expose
    private String itemId;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Message")
    @Expose
    private String message;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(String workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

}