package com.neprofinishedgood.productionjournal.model;

public class PickingList {
    String itemName, quantity, workOrderNo;

    public PickingList(String itemName, String quantity, String workOrderNo) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.workOrderNo = workOrderNo;
    }

    public String getWorkOrderNo() {
        return workOrderNo;
    }

    public void setWorkOrderNo(String workOrderNo) {
        this.workOrderNo = workOrderNo;
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
}
