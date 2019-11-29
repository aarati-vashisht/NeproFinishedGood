package com.neprofinishedgood.assigntransfer.model;

import com.neprofinishedgood.transferstillage.model.TransferStillageDetail;
import java.util.ArrayList;

public class AssignTransInput {

    String UserId, IsTj, SiteId, WareHouseId, FLT, FromWareHouseId;
    ArrayList<TransferStillageDetail> StillageList;

    public AssignTransInput(String userId, String isTj, String siteId, String wareHouseId, String FLT, String fromWareHouseId, ArrayList<TransferStillageDetail> stillageList) {
        UserId = userId;
        IsTj = isTj;
        SiteId = siteId;
        WareHouseId = wareHouseId;
        this.FLT = FLT;
        FromWareHouseId = fromWareHouseId;
        StillageList = stillageList;
    }
}
