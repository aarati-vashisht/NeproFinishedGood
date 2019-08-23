package com.neprofinishedgood.assign.model;

public class AssignedUnAssignedInput {
    String StickerNo;
    String Aisle;

    public AssignedUnAssignedInput(String stickerNo, String aisle, String rack, String bin, String userId, String assignedFLT, String wareHouseID) {
        StickerNo = stickerNo;
        Aisle = aisle;
        Rack = rack;
        Bin = bin;
        UserId = userId;
        AssignedFLT = assignedFLT;
        WareHouseID = wareHouseID;
    }

    String Rack;
    String Bin;
    String UserId;
    String AssignedFLT;
    String WareHouseID;
}
