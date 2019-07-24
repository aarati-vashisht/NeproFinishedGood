package com.neprofinishedgood.pickandload.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoadingPlanDetailInput {

    @SerializedName("LPID")
    @Expose
    private String lPID;
    @SerializedName("UserId")
    @Expose
    private String userId;

    public LoadingPlanDetailInput(String lPID, String userId) {
        this.lPID = lPID;
        this.userId = userId;
    }

    public String getLPID() {
        return lPID;
    }

    public void setLPID(String lPID) {
        this.lPID = lPID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}