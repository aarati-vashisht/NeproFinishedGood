package com.neprofinishedgood.workorderstartend.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkOrderScanResponse {

    @SerializedName("WorkOrderNo")
    @Expose
    private String workOrderNo;
    @SerializedName("ItemId")
    @Expose
    private String itemId;
    @SerializedName("ItemName")
    @Expose
    private String itemName;
    @SerializedName("ItemDescription")
    @Expose
    private String itemDescription;
    @SerializedName("ProductionLine")
    @Expose
    private String productionLine;
    @SerializedName("WareHouse")
    @Expose
    private String wareHouse;
    @SerializedName("Quantity")
    @Expose
    private String quantity;
    @SerializedName("Site")
    @Expose
    private String site;
    @SerializedName("WOStatus")
    @Expose
    private String wOStatus;
    @SerializedName("StatusId")
    @Expose
    private String statusId;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("BalanceQuantity")
    @Expose
    private String balanceQuantity;
    @SerializedName("RafQuantity")
    @Expose
    private String rafQuantity;
    @SerializedName("StartedQty")
    @Expose
    private String startedQty;
    @SerializedName("WareHouseId")
    @Expose
    private String wareHouseId;
    @SerializedName("SiteID")
    @Expose
    private String siteID;

   @SerializedName("IsFinancialEnd")
    @Expose
    private String IsFinancialEnd;

    public String getBalanceQuantity() {
        return balanceQuantity;
    }

    public void setBalanceQuantity(String balanceQuantity) {
        this.balanceQuantity = balanceQuantity;
    }

    public String getRafQuantity() {
        return rafQuantity;
    }

    public void setRafQuantity(String rafQuantity) {
        this.rafQuantity = rafQuantity;
    }

    public String getWorkOrderNo() {
        return workOrderNo;
    }

    public void setWorkOrderNo(String workOrderNo) {
        this.workOrderNo = workOrderNo;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getProductionLine() {
        return productionLine;
    }

    public void setProductionLine(String productionLine) {
        this.productionLine = productionLine;
    }

    public String getWareHouse() {
        return wareHouse;
    }

    public void setWareHouse(String wareHouse) {
        this.wareHouse = wareHouse;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getWOStatus() {
        return wOStatus;
    }

    public void setWOStatus(String wOStatus) {
        this.wOStatus = wOStatus;
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

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getStartedQty() {
        return startedQty;
    }

    public void setStartedQty(String startedQty) {
        this.startedQty = startedQty;
    }

    public String getWareHouseID() {
        return wareHouseId;
    }

    public void setWareHouseID(String wareHouseID) {
        this.wareHouseId = wareHouseID;
    }

    public String getSiteID() {
        return siteID;
    }

    public void setSiteID(String siteID) {
        this.siteID = siteID;
    }

    public String getIsFinancialEnd() {
        return IsFinancialEnd;
    }

    public void setIsFinancialEnd(String isFinancialEnd) {
        IsFinancialEnd = isFinancialEnd;
    }
}