package com.neprofinishedgood.putaway.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationData {

    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("Aisle")
    @Expose
    private String aisle;
    @SerializedName("rack")
    @Expose
    private String rack;
    @SerializedName("bin")
    @Expose
    private String bin;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

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

}
