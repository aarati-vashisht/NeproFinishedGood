package com.neprofinishedgood.assigntransfer.presenter;

import com.neprofinishedgood.assigntransfer.model.AssignTransInput;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.transferstillage.model.WareHouseInput;
import com.neprofinishedgood.transferstillage.model.WareHouseResponse;

public interface IAssignTransInterface {

    void callScanStillageService(MoveInput moveInput);

    void getScanStillageResponse(ScanStillageResponse body);

    void callGetWareHouse(WareHouseInput wareHouseInput);

    void getWareHouseResponse(WareHouseResponse body);

    void callUpdateAssignTransfer(AssignTransInput assignTransInput);

    void getUpdateAssignTransferResponse(UniversalResponse body);
}
