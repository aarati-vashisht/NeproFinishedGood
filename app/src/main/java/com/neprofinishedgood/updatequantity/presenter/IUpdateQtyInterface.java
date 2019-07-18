package com.neprofinishedgood.updatequantity.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;
import com.neprofinishedgood.updatequantity.model.UpdateQtyInput;

public interface IUpdateQtyInterface {

    void callScanStillageService(MoveInput moveInput);

    void getScanUpdateQtyResponse(ScanStillageResponse body);

    void callUpdateQtyStillageService(UpdateQtyInput updateQtyInput);

    void getUpdateQtyStillageResponse(UniversalResponse body);

}
