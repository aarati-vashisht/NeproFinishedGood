package com.neprofinishedgood.plannedandunplannedmove.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationData {


    @SerializedName("Aisle")
    @Expose
    private Integer aisle;
    @SerializedName("Rack")
    @Expose
    private Integer rack;
    @SerializedName("Bin")
    @Expose
    private Integer bin;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Message")
    @Expose
    private String message;

    public Integer getAisle() {
        return aisle;
    }

    public void setAisle(Integer aisle) {
        this.aisle = aisle;
    }

    public Integer getRack() {
        return rack;
    }

    public void setRack(Integer rack) {
        this.rack = rack;
    }

    public Integer getBin() {
        return bin;
    }

    public void setBin(Integer bin) {
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
