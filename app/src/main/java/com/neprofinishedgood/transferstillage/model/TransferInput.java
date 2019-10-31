package com.neprofinishedgood.transferstillage.model;

import java.util.ArrayList;

public class TransferInput {
    ArrayList<TransferStillageDetail> StillageDetailsData;
    String StickerNo;
    String WareHouseID;
    String UserId;
    String MakeTJ;

    public TransferInput(String stickerNo, ArrayList<TransferStillageDetail>  stillageDetailsData, String wareHouseID, String userId, String makeTJ) {
        StickerNo = stickerNo;
        WareHouseID = wareHouseID;
        UserId = userId;
        StillageDetailsData = stillageDetailsData;
        MakeTJ = makeTJ;
    }



}
