package com.neprofinishedgood.move.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationData {


    @SerializedName("Aisle")
    @Expose
    private String aisle;
    @SerializedName("AisleName")
    @Expose
    private String aisleName;
    @SerializedName("Rack")
    @Expose
    private String rack;
    @SerializedName("RackName")
    @Expose
    private String rackName;
    @SerializedName("Bin")
    @Expose
    private String bin;
    @SerializedName("BinName")
    @Expose
    private String binName;
    @SerializedName("Zone")
    @Expose
    private String zone;
    @SerializedName("ZoneName")
    @Expose
    private String zoneName;
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

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getAisleName() {
        return aisleName;
    }

    public void setAisleName(String aisleName) {
        this.aisleName = aisleName;
    }

    public String getRackName() {
        return rackName;
    }

    public void setRackName(String rackName) {
        this.rackName = rackName;
    }

    public String getBinName() {
        return binName;
    }

    public void setBinName(String binName) {
        this.binName = binName;
    }
}
