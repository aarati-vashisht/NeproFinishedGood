package com.neprofinishedgood.pickandload.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScanLoadingPlanList {

    @SerializedName("TLPHID")
    @Expose
    private Integer tLPHID;
    @SerializedName("LoadingPlanNo")
    @Expose
    private String loadingPlanNo;
    @SerializedName("CustomerId")
    @Expose
    private String customerId;

    private String status = "";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTLPHID() {
        return tLPHID;
    }

    public void setTLPHID(Integer tLPHID) {
        this.tLPHID = tLPHID;
    }

    public String getLoadingPlanNo() {
        return loadingPlanNo;
    }

    public void setLoadingPlanNo(String loadingPlanNo) {
        this.loadingPlanNo = loadingPlanNo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

}