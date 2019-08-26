package com.neprofinishedgood.assign.model;

public class AssignedUnAssignedInput {
    String StickerNo;
    String Aisle;
    String Rack;
    String Bin;
    String UserId;
    String AssignedFLT;
    String WareHouseID;
    String Zone;


    public AssignedUnAssignedInput(String stickerNo, String aisle, String rack, String bin, String userId, String assignedFLT, String wareHouseID, String zone) {
        StickerNo = stickerNo;
        Aisle = aisle;
        Rack = rack;
        Bin = bin;
        UserId = userId;
        AssignedFLT = assignedFLT;
        WareHouseID = wareHouseID;
        Zone = zone;
    }
}
