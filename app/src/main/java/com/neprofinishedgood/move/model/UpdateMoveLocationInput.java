package com.neprofinishedgood.move.model;

public class UpdateMoveLocationInput {
    String StickerNo;
    String Aisle;
    String Rack;
    String Bin;

    String LoadingAreaId, WareHouseID;

    public UpdateMoveLocationInput(String stickerNo, String aisle, String rack, String bin, String userId, String loadingAreaId, String wareHouseID) {
        StickerNo = stickerNo;
        Aisle = aisle;
        Rack = rack;
        Bin = bin;
        UserId = userId;
        LoadingAreaId = loadingAreaId;
        WareHouseID = wareHouseID;
    }

    String UserId;

}
