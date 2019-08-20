package com.neprofinishedgood.productionjournal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PickingListDatum {

    public PickingListDatum(String itemName, String unit, String itemId) {
        this.itemName = itemName;
        this.unit = unit;
        this.itemId = itemId;
    }

    @SerializedName("ItemName")
    @Expose
    private String itemName;
    @SerializedName("Unit")
    @Expose
    private String unit;
    @SerializedName("ItemId")
    @Expose
    private String itemId;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

}
