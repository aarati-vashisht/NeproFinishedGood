package com.neprofinishedgood.transferstillage.model;

import java.util.ArrayList;

public class TransferInput {
    ArrayList<TransferStillageDetail> StillageDetailsData;
    String StickerNo;
    String WareHouseID;
    String SiteID;
    String UserId;
    String TJ;
    String LocationId;

    public TransferInput(String stickerNo, ArrayList<TransferStillageDetail>  stillageDetailsData, String wareHouseID, String userId, String tj, String siteID, String locationId) {
        StickerNo = stickerNo;
        WareHouseID = wareHouseID;
        UserId = userId;
        StillageDetailsData = stillageDetailsData;
        TJ = tj;
        SiteID = siteID;
        LocationId = locationId;
    }



}
