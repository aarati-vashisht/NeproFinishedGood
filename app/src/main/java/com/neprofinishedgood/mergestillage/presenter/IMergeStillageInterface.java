package com.neprofinishedgood.mergestillage.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.mergestillage.model.UpgradeMergeInput;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;

public interface IMergeStillageInterface {
    void callScanStillageService(MoveInput moveInput);

    void getScanMergeStillageResponse(ScanStillageResponse body);

    void callUpdateMergeStillage(UpgradeMergeInput upgradeMergeInput);

    void getUpdateMergeStillageResponse(UniversalResponse body);
}
