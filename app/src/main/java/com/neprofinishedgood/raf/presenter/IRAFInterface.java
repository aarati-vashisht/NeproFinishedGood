package com.neprofinishedgood.raf.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.raf.model.RafInput;

public interface IRAFInterface {
    void callScanStillageService(MoveInput moveInput);

    void getRAFResponse(ScanStillageResponse body);

    void callRAFServcie(RafInput updateMoveLocationInput);

    void getUpdateMoveResponse(UniversalResponse body);

}
