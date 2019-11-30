package com.neprofinishedgood.move.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransferStillageList {
    @SerializedName("StillageID")
    @Expose
    private String stillageID;
    @SerializedName("ItemID")
    @Expose
    private String itemID;
    @SerializedName("ItemDesc")
    @Expose
    private String itemDesc;
    @SerializedName("StillageQty")
    @Expose
    private String stillageQty;

    public String getStillageID() {
        return stillageID;
    }

    public void setStillageID(String stillageID) {
        this.stillageID = stillageID;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getStillageQty() {
        return stillageQty;
    }

    public void setStillageQty(String stillageQty) {
        this.stillageQty = stillageQty;
    }
}
