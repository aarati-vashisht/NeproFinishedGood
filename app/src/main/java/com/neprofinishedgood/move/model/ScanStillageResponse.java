package com.neprofinishedgood.move.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScanStillageResponse {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("StickerID")
    @Expose
    private String stickerID;
    @SerializedName("StandardQty")
    @Expose
    private Integer standardQty;
    @SerializedName("ItemId")
    @Expose
    private String itemId;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("ItemStdQty")
    @Expose
    private Integer itemStdQty;
    @SerializedName("AssignedLocation")
    @Expose
    private String assignedLocation;

    @SerializedName("WorkOrderNo")
    @Expose
    private String WorkOrderNo;

    @SerializedName("IsMovedFromProdLine")
    @Expose
    private Integer isMovedFromProdLine;

    public Integer getIsTransfered() {
        return IsTransfered;
    }

    public void setIsTransfered(Integer isTransfered) {
        IsTransfered = isTransfered;
    }

    @SerializedName("IsTransfered")
    @Expose
    private Integer IsTransfered;

    public Integer getIsRecieved() {
        return IsRecieved;
    }

    public void setIsRecieved(Integer isRecieved) {
        IsRecieved = isRecieved;
    }

    @SerializedName("IsRecieved")
    @Expose
    private Integer IsRecieved;

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    @SerializedName("Location")
    @Expose
    private String Location;

    public String getWareHouseName() {
        return WareHouseName;
    }

    public void setWareHouseName(String wareHouseName) {
        WareHouseName = wareHouseName;
    }

    @SerializedName("WareHouseName")
    @Expose
    private String WareHouseName;

    public String getIsHold() {
        return isHold;
    }

    public void setIsHold(String isHold) {
        this.isHold = isHold;
    }

    @SerializedName("isHold")
    @Expose
    private String isHold;

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

    public String getStickerID() {
        return stickerID;
    }

    public void setStickerID(String stickerID) {
        this.stickerID = stickerID;
    }

    public Integer getStandardQty() {
        return standardQty;
    }

    public void setStandardQty(Integer standardQty) {
        this.standardQty = standardQty;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getItemStdQty() {
        return itemStdQty;
    }

    public void setItemStdQty(Integer itemStdQty) {
        this.itemStdQty = itemStdQty;
    }

    public String getAssignedLocation() {
        return assignedLocation;
    }

    public void setAssignedLocation(String assignedLocation) {
        this.assignedLocation = assignedLocation;
    }

    public Integer getIsMovedFromProdLine() {
        return isMovedFromProdLine;
    }

    public void setIsMovedFromProdLine(Integer isMovedFromProdLine) {
        this.isMovedFromProdLine = isMovedFromProdLine;
    }

    public String getWorkOrderNo() {
        return WorkOrderNo;
    }

    public void setWorkOrderNo(String workOrderNo) {
        WorkOrderNo = workOrderNo;
    }
}
