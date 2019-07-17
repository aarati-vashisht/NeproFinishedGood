package com.neprofinishedgood.receivereturnstillage.model;

public class TransferInput {
    String StickerNo;

    public TransferInput(String stickerNo, String wareHouseID, String userId) {
        StickerNo = stickerNo;
        WareHouseID = wareHouseID;
        UserId = userId;
    }

    String WareHouseID;
    String UserId;

}
