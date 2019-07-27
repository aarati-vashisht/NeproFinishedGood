package com.neprofinishedgood.base.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationList {

@SerializedName("LocationID")
@Expose
private String locationID;
@SerializedName("Aisle")
@Expose
private String aisle;
@SerializedName("Rack")
@Expose
private String rack;
@SerializedName("Bin")
@Expose
private String bin;

public String getLocationID() {
return locationID;
}

public void setLocationID(String locationID) {
this.locationID = locationID;
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