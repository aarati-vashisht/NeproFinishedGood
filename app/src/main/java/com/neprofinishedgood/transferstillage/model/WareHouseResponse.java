package com.neprofinishedgood.transferstillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.neprofinishedgood.base.model.UniversalSpinner;

import java.util.ArrayList;

public class WareHouseResponse {

    @SerializedName("Status")
    @Expose
    private String status;

    @SerializedName("Message")
    @Expose
    private String message;

    @SerializedName("WareHouseListData")
    @Expose
    private ArrayList<UniversalSpinner> wareHouseListData = null;

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

    public ArrayList<UniversalSpinner> getWareHouseListData() {
        return wareHouseListData;
    }

    public void setWareHouseListData(ArrayList<UniversalSpinner> wareHouseListData) {
        this.wareHouseListData = wareHouseListData;
    }
}
