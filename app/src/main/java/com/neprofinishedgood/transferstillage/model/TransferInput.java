package com.neprofinishedgood.transferstillage.model;

import java.util.ArrayList;

public class TransferInput {
    ArrayList<String> StickerNoList;
    String StickerNo;

    public TransferInput(String stickerNo, ArrayList<String>  stickerNoList, String wareHouseID, String userId) {
        StickerNo = stickerNo;
        WareHouseID = wareHouseID;
        UserId = userId;
        StickerNoList = stickerNoList;
    }

    String WareHouseID;
    String UserId;

}
