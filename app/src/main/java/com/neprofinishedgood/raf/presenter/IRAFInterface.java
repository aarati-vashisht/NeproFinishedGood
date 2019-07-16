package com.neprofinishedgood.raf.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.raf.model.RafInput;
import com.neprofinishedgood.raf.model.ScanCountingResponse;

public interface IRAFInterface {
    void callScanStillageService(MoveInput moveInput);

    void getRAFResponse(ScanCountingResponse body);

    void callRAFServcie(RafInput updateMoveLocationInput);

    void getUpdateMoveResponse(UniversalResponse body);

}
