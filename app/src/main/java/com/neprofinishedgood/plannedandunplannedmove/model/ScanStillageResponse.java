package com.neprofinishedgood.plannedandunplannedmove.model;


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
    @SerializedName("IsMovedFromProdLine")
    @Expose
    private Integer isMovedFromProdLine;

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

}