package com.neprofinishedgood.move.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.neprofinishedgood.base.model.UniversalSpinner;

import java.util.ArrayList;
import java.util.List;

public class ScanStillageResponse {

    @SerializedName("Status")
    @Expose
    private String status;

    @SerializedName("Message")
    @Expose
    private String message;

    @SerializedName("StickerID")
    @Expose
    private String stickerID;

    @SerializedName("StandardQty")
    @Expose
    private float standardQty;

    @SerializedName("ItemId")
    @Expose
    private String itemId;

    @SerializedName("Description")
    @Expose
    private String description;

    @SerializedName("ItemStdQty")
    @Expose
    private float itemStdQty;

    @SerializedName("AssignedLocation")
    @Expose
    private String assignedLocation;

    @SerializedName("WorkOrderNo")
    @Expose
    private String WorkOrderNo;

    @SerializedName("IsMovedFromProdLine")
    @Expose
    private Integer isMovedFromProdLine;

    @SerializedName("WareHouseID")
    @Expose
    private String wareHouseID;

    @SerializedName("IsTransfered")
    @Expose
    private Integer IsTransfered;

    @SerializedName("IsRecieved")
    @Expose
    private Integer IsRecieved;

    @SerializedName("Location")
    @Expose
    private String Location;

    @SerializedName("WareHouseName")
    @Expose
    private String WareHouseName;

    @SerializedName("isHold")
    @Expose
    private String isHold;



    @SerializedName("AisleList")
    @Expose
    private List<UniversalSpinner> aisleList = null;
    @SerializedName("BinList")
    @Expose
    private List<UniversalSpinner> binList = null;
    @SerializedName("RackList")
    @Expose
    private List<UniversalSpinner> rackList = null;
    @SerializedName("StillageLocation")
    @Expose
    private String stillageLocation;
    @SerializedName("ZoneList")
    @Expose
    private ArrayList<UniversalSpinner> zoneList = null;

    @SerializedName("SiteListData")
    @Expose
    private ArrayList<UniversalSpinner> siteListData = null;







    @SerializedName("AssignedAisleId")
    @Expose
    private String assignedAisleId;
    @SerializedName("AssignedRackId")
    @Expose
    private String assignedRackId;
    @SerializedName("AssignedBinId")
    @Expose
    private String assignedBinId;
    @SerializedName("AssignedZoneId")
    @Expose
    private String assignedZoneId;


    @SerializedName("AssignedAisleName")
    @Expose
    private String assignedAisleName;
    @SerializedName("AssignedRackName")
    @Expose
    private String assignedRackName;
    @SerializedName("AssignedBinName")
    @Expose
    private String assignedBinName;
    @SerializedName("AssignedZoneName")
    @Expose
    private String assignedZoneName;



    @SerializedName("StillageLocationName")
    @Expose
    private String stillageLocationName;


    @SerializedName("LoadingAreaId")
    @Expose
    private String loadingAreaId;



    @SerializedName("IsShiped")
    @Expose
    private String isShiped;
    @SerializedName("TransferId")
    @Expose
    private String transferId;

    public String getIsShiped() {
        return isShiped;
    }

    public void setIsShiped(String isShiped) {
        this.isShiped = isShiped;
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }




    public String getLoadingAreaId() {
        return loadingAreaId;
    }

    public void setLoadingAreaId(String loadingAreaId) {
        this.loadingAreaId = loadingAreaId;
    }


    public String getStillageLocationName() {
        return stillageLocationName;
    }

    public void setStillageLocationName(String stillageLocationName) {
        this.stillageLocationName = stillageLocationName;
    }




    public String getAssignedAisleId() {
        return assignedAisleId;
    }

    public void setAssignedAisleId(String assignedAisleId) {
        this.assignedAisleId = assignedAisleId;
    }

    public String getAssignedRackId() {
        return assignedRackId;
    }

    public void setAssignedRackId(String assignedRackId) {
        this.assignedRackId = assignedRackId;
    }

    public String getAssignedBinId() {
        return assignedBinId;
    }

    public void setAssignedBinId(String assignedBinId) {
        this.assignedBinId = assignedBinId;
    }






    public String getWareHouseID() {
        return wareHouseID;
    }

    public void setWareHouseID(String wareHouseID) {
        this.wareHouseID = wareHouseID;
    }

    public Integer getIsTransfered() {
        return IsTransfered;
    }

    public void setIsTransfered(Integer isTransfered) {
        IsTransfered = isTransfered;
    }

    public Integer getIsRecieved() {
        return IsRecieved;
    }

    public void setIsRecieved(Integer isRecieved) {
        IsRecieved = isRecieved;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getWareHouseName() {
        return WareHouseName;
    }

    public void setWareHouseName(String wareHouseName) {
        WareHouseName = wareHouseName;
    }

    public String getIsHold() {
        return isHold;
    }

    public void setIsHold(String isHold) {
        this.isHold = isHold;
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

    public String getStickerID() {
        return stickerID;
    }

    public void setStickerID(String stickerID) {
        this.stickerID = stickerID;
    }

    public float getStandardQty() {
        return standardQty;
    }

    public void setStandardQty(float standardQty) {
        this.standardQty = standardQty;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getItemStdQty() {
        return itemStdQty;
    }

    public void setItemStdQty(float itemStdQty) {
        this.itemStdQty = itemStdQty;
    }

    public String getAssignedLocation() {
        return assignedLocation;
    }

    public void setAssignedLocation(String assignedLocation) {
        this.assignedLocation = assignedLocation;
    }

    public Integer getIsMovedFromProdLine() {
        return isMovedFromProdLine;
    }

    public void setIsMovedFromProdLine(Integer isMovedFromProdLine) {
        this.isMovedFromProdLine = isMovedFromProdLine;
    }

    public String getWorkOrderNo() {
        return WorkOrderNo;
    }

    public void setWorkOrderNo(String workOrderNo) {
        WorkOrderNo = workOrderNo;
    }


    public List<UniversalSpinner> getAisleList() {
        return aisleList;
    }

    public void setAisleList(List<UniversalSpinner> aisleList) {
        this.aisleList = aisleList;
    }

    public List<UniversalSpinner> getBinList() {
        return binList;
    }

    public void setBinList(List<UniversalSpinner> binList) {
        this.binList = binList;
    }

    public List<UniversalSpinner> getRackList() {
        return rackList;
    }

    public void setRackList(List<UniversalSpinner> rackList) {
        this.rackList = rackList;
    }

    public String getStillageLocation() {
        return stillageLocation;
    }

    public void setStillageLocation(String stillageLocation) {
        this.stillageLocation = stillageLocation;
    }

    public ArrayList<UniversalSpinner> getZoneList() {
        return zoneList;
    }

    public void setZoneList(ArrayList<UniversalSpinner> zoneList) {
        this.zoneList = zoneList;
    }

    public String getAssignedAisleName() {
        return assignedAisleName;
    }

    public void setAssignedAisleName(String assignedAisleName) {
        this.assignedAisleName = assignedAisleName;
    }

    public String getAssignedRackName() {
        return assignedRackName;
    }

    public void setAssignedRackName(String assignedRackName) {
        this.assignedRackName = assignedRackName;
    }

    public String getAssignedBinName() {
        return assignedBinName;
    }

    public void setAssignedBinName(String assignedBinName) {
        this.assignedBinName = assignedBinName;
    }

    public String getAssignedZoneId() {
        return assignedZoneId;
    }

    public void setAssignedZoneId(String assignedZoneId) {
        this.assignedZoneId = assignedZoneId;
    }

    public String getAssignedZoneName() {
        return assignedZoneName;
    }

    public void setAssignedZoneName(String assignedZoneName) {
        this.assignedZoneName = assignedZoneName;
    }

    public ArrayList<UniversalSpinner> getSiteListData() {
        return siteListData;
    }

    public void setSiteListData(ArrayList<UniversalSpinner> siteListData) {
        this.siteListData = siteListData;
    }
}
