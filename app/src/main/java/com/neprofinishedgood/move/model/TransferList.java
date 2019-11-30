package com.neprofinishedgood.move.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransferList {

    @SerializedName("IsTj")
    @Expose
    private Integer isTj;
    @SerializedName("SiteId")
    @Expose
    private String siteId;
    @SerializedName("ActivityId")
    @Expose
    private String activityId;
    @SerializedName("TATHID")
    @Expose
    private String tATHID;
    @SerializedName("NoOfStillages")
    @Expose
    private String noOfStillages;
    @SerializedName("WareHouseId")
    @Expose
    private String wareHouseId;
    @SerializedName("FromWareHouseId")
    @Expose
    private String fromWareHouseId;

    public Integer getIsTj() {
        return isTj;
    }

    public void setIsTj(Integer isTj) {
        this.isTj = isTj;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getTATHID() {
        return tATHID;
    }

    public void setTATHID(String tATHID) {
        this.tATHID = tATHID;
    }

    public String getNoOfStillages() {
        return noOfStillages;
    }

    public void setNoOfStillages(String noOfStillages) {
        this.noOfStillages = noOfStillages;
    }

    public String getWareHouseId() {
        return wareHouseId;
    }

    public void setWareHouseId(String wareHouseId) {
        this.wareHouseId = wareHouseId;
    }

    public String getFromWareHouseId() {
        return fromWareHouseId;
    }

    public void setFromWareHouseId(String fromWareHouseId) {
        this.fromWareHouseId = fromWareHouseId;
    }

}
