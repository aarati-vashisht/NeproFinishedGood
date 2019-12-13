package com.neprofinishedgood.transferstillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.neprofinishedgood.base.model.UniversalSpinner;

import java.util.List;

public class LocationResponse {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("LocationData")
    @Expose
    private List<UniversalSpinner> locationData = null;

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

    public List<UniversalSpinner> getLocationData() {
        return locationData;
    }

    public void setLocationData(List<UniversalSpinner> locationData) {
        this.locationData = locationData;
    }

}