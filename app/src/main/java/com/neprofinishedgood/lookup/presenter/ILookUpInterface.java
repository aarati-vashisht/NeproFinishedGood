package com.neprofinishedgood.lookup.presenter;

import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;

public interface ILookUpInterface {
    void callScanStillageService(MoveInput moveInput);

    void getScanMergeStillageResponse(ScanStillageResponse body);


}
