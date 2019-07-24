package com.neprofinishedgood.pickandload.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoadingPlanList1 {

    @SerializedName("WorkOrderQty")
    @Expose
    private Integer workOrderQty;
    @SerializedName("ItemName")
    @Expose
    private String itemName;
    @SerializedName("ItemId")
    @Expose
    private Integer itemId;
    @SerializedName("SiteName")
    @Expose
    private String siteName;
    @SerializedName("Aisle")
    @Expose
    private String aisle;
    @SerializedName("Rack")
    @Expose
    private String rack;
    @SerializedName("Bin")
    @Expose
    private String bin;
    @SerializedName("StillageQty")
    @Expose
    private Integer stillageQty;
    @SerializedName("StillageStdQty")
    @Expose
    private Integer stillageStdQty;
    @SerializedName("WareHouseID")
    @Expose
    private Integer wareHouseID;

    public Integer getWorkOrderQty() {
        return workOrderQty;
    }

    public void setWorkOrderQty(Integer workOrderQty) {
        this.workOrderQty = workOrderQty;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getAisle() {
        return aisle;
    }

    public void setAisle(String aisle) {
        this.aisle = aisle;
    }

    public String getRack() {
        return rack;
    }

    public void setRack(String rack) {
        this.rack = rack;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public Integer getStillageQty() {
        return stillageQty;
    }

    public void setStillageQty(Integer stillageQty) {
        this.stillageQty = stillageQty;
    }

    public Integer getStillageStdQty() {
        return stillageStdQty;
    }

    public void setStillageStdQty(Integer stillageStdQty) {
        this.stillageStdQty = stillageStdQty;
    }

    public Integer getWareHouseID() {
        return wareHouseID;
    }

    public void setWareHouseID(Integer wareHouseID) {
        this.wareHouseID = wareHouseID;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status = "";

}
