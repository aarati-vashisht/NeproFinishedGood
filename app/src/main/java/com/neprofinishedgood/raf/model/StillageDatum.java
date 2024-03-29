package com.neprofinishedgood.raf.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StillageDatum {

    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("stdQuantity")
    @Expose
    private String stdQuantity;

    public String getLoadingPlan() {
        return loadingPlan;
    }

    public void setLoadingPlan(String loadingPlan) {
        this.loadingPlan = loadingPlan;
    }

    @SerializedName("loadingPlan")
    @Expose
    private String loadingPlan;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status = "";

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getStdQuantity() {
        return stdQuantity;
    }

    public void setStdQuantity(String stdQuantity) {
        this.stdQuantity = stdQuantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("item")
    @Expose
    private String item;

    public String getStillageId() {
        return stillageId;
    }

    public void setStillageId(String stillageId) {
        this.stillageId = stillageId;
    }

    @SerializedName("stillageId")
    @Expose
    private String stillageId;

}