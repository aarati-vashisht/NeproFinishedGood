package com.neprofinishedgood.transferstillage.model;

import java.util.ArrayList;

public class TransferInput {
    ArrayList<TransferStillageDetail> StillageDetailsData;
    String StickerNo;

    public TransferInput(String stickerNo, ArrayList<TransferStillageDetail>  stillageDetailsData, String wareHouseID, String userId) {
        StickerNo = stickerNo;
        WareHouseID = wareHouseID;
        UserId = userId;
        StillageDetailsData = stillageDetailsData;
    }

    String WareHouseID;
    String UserId;

}
