package com.neprofinishedgood.move.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AssignedStillages {

    @SerializedName("StillageList")
    @Expose
    private List<ScanStillageResponse> stillageList = null;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Message")
    @Expose
    private String message;

    public List<ScanStillageResponse> getStillageList() {
        return stillageList;
    }

    public void setStillageList(List<ScanStillageResponse> stillageList) {
        this.stillageList = stillageList;
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

}


