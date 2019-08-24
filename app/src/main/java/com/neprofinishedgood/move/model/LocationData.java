package com.neprofinishedgood.move.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationData {


    @SerializedName("Aisle")
    @Expose
    private String aisle;
    @SerializedName("Rack")
    @Expose
    private String rack;
    @SerializedName("Bin")
    @Expose
    private String bin;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Message")
    @Expose
    private String message;

    public String getAisle() {
        return aisle;
    }

    public void setAisle(String aisle) {
        this.aisle = aisle;
    }

    public String getRack() {
        return rack;
    }

    public void setRack(String rack) {
        this.rack = rack;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
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
