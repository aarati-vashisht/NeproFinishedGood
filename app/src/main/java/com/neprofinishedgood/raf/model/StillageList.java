package com.neprofinishedgood.raf.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StillageList {

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

    public String getIsHold() {
        return isHold;
    }

    public void setIsHold(String isHold) {
        this.isHold = isHold;
    }

    @SerializedName("isHold")
    @Expose
    private String isHold;

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

}
