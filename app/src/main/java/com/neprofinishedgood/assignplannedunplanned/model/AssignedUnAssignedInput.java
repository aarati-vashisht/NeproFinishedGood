package com.neprofinishedgood.assignplannedunplanned.model;

public class AssignedUnAssignedInput {
    String StickerNo;
    String Aisle;

    public AssignedUnAssignedInput(String stickerNo, String aisle, String rack, String bin, String userId, String assignedFLT) {
        StickerNo = stickerNo;
        Aisle = aisle;
        Rack = rack;
        Bin = bin;
        UserId = userId;
        AssignedFLT = assignedFLT;
    }

    String Rack;
    String Bin;
    String UserId;
    String AssignedFLT;
}
