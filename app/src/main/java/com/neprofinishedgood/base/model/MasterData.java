package com.neprofinishedgood.base.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MasterData {

    @SerializedName("AisleList")
    @Expose
    private List<UniversalSpinner> aisleList = null;
    @SerializedName("RackList")
    @Expose
    private List<UniversalSpinner> rackList = null;
    @SerializedName("BinList")
    @Expose
    private List<UniversalSpinner> binList = null;
    @SerializedName("ReasonList")
    @Expose
    private List<UniversalSpinner> reasonList = null;
    @SerializedName("FLTList")
    @Expose
    private List<UniversalSpinner> fLTList = null;

    public List<UniversalSpinner> getfLTList() {
        return fLTList;
    }

    public void setfLTList(List<UniversalSpinner> fLTList) {
        this.fLTList = fLTList;
    }

    public List<UniversalSpinner> getWareHouseList() {
        return WareHouseList;
    }

    public void setWareHouseList(List<UniversalSpinner> wareHouseList) {
        WareHouseList = wareHouseList;
    }

    @SerializedName("WareHouseList")
    @Expose
    private List<UniversalSpinner> WareHouseList = null;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Message")
    @Expose
    private String message;

    public List<UniversalSpinner> getAisleList() {
        return aisleList;
    }

    public void setAisleList(List<UniversalSpinner> aisleList) {
        this.aisleList = aisleList;
    }

    public List<UniversalSpinner> getRackList() {
        return rackList;
    }

    public void setRackList(List<UniversalSpinner> rackList) {
        this.rackList = rackList;
    }

    public List<UniversalSpinner> getBinList() {
        return binList;
    }

    public void setBinList(List<UniversalSpinner> binList) {
        this.binList = binList;
    }

    public List<UniversalSpinner> getReasonList() {
        return reasonList;
    }

    public void setReasonList(List<UniversalSpinner> reasonList) {
        this.reasonList = reasonList;
    }

    public List<UniversalSpinner> getFLTList() {
        return fLTList;
    }

    public void setFLTList(List<UniversalSpinner> fLTList) {
        this.fLTList = fLTList;
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
