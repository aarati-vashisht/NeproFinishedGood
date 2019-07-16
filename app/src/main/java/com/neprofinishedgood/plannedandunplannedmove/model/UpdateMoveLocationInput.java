package com.neprofinishedgood.plannedandunplannedmove.model;

public class UpdateMoveLocationInput {
    String StickerNo;
    String Aisle;
    String Rack;
    String Bin;

    public UpdateMoveLocationInput(String stickerNo, String aisle, String rack, String bin, String userId) {
        StickerNo = stickerNo;
        Aisle = aisle;
        Rack = rack;
        Bin = bin;
        UserId = userId;
    }

    String UserId;

}
