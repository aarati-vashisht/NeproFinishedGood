package com.neprofinishedgood.move.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransferDetailResponseModel {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("StillageList")
    @Expose
    private List<TransferStillageList> stillageList = null;

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

    public List<TransferStillageList> getStillageList() {
        return stillageList;
    }

    public void setStillageList(List<TransferStillageList> stillageList) {
        this.stillageList = stillageList;
    }

}