package com.neprofinishedgood.productionjournal.model;

public class ItemPicked {

    String Shift, Date, ItemName, ItemId, Quantity;

    public ItemPicked(String shift, String date, String itemName, String itemId, String quantity) {
        Shift = shift;
        Date = date;
        ItemName = itemName;
        ItemId = itemId;
        Quantity = quantity;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getShift() {
        return Shift;
    }

    public void setShift(String shift) {
        Shift = shift;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }
}
