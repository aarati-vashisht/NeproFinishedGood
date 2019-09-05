package com.neprofinishedgood.pickandload.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoadingPlanList {

    @SerializedName("WorkOrderQty")
    @Expose
    private String workOrderQty;
    @SerializedName("ItemName")
    @Expose
    private String itemName;
    @SerializedName("ItemId")
    @Expose
    private String itemId;
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
    @SerializedName("Zone")
    @Expose
    private String zone;
    @SerializedName("StillageQty")
    @Expose
    private Integer stillageQty;
    @SerializedName("StillageStdQty")
    @Expose
    private Integer stillageStdQty;
    @SerializedName("WareHouseID")
    @Expose
    private Integer wareHouseID;

    public Integer getPickingQty() {
        return PickingQty;
    }

    public void setPickingQty(Integer pickingQty) {
        PickingQty = pickingQty;
    }

    @SerializedName("PickingQty")
    @Expose
    private Integer PickingQty;

    public String getStillageNO() {
        return StillageNO;
    }

    public void setStillageNO(String stillageNO) {
        StillageNO = stillageNO;
    }

    @SerializedName("StickerID")
    @Expose
    private String StillageNO = "";

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    String Status = "";

    public String getLoadingNumber() {
        return LoadingNumber;
    }

    public void setLoadingNumber(String loadingNumber) {
        LoadingNumber = loadingNumber;
    }

    String LoadingNumber;

    public String getWorkOrderQty() {
        return workOrderQty;
    }

    public void setWorkOrderQty(String workOrderQty) {
        this.workOrderQty = workOrderQty;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
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

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}
