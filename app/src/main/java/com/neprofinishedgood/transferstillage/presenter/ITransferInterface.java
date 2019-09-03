package com.neprofinishedgood.transferstillage.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.transferstillage.model.ShipInput;
import com.neprofinishedgood.transferstillage.model.TransferInput;

public interface ITransferInterface {
    void callScanStillageService(MoveInput moveInput);

    void getScanMergeStillageResponse(ScanStillageResponse body);

    void callUpdateTransferStillage(TransferInput transferInput);

    void getUpdateTransferStillageResponse(UniversalResponse body);

    void callShipStillage(ShipInput shipInput);

    void getShipStillageResponse(UniversalResponse body);
}
