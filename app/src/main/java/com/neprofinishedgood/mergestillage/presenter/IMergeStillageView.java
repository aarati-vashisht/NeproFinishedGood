package com.neprofinishedgood.mergestillage.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.ScanStillageResponse;

public interface IMergeStillageView {
    void onFailure(String message);

    void onSuccess(ScanStillageResponse body);

    void onUpdateMergeStillageFailure(String message);

    void onUpdateMergeStillageSuccess(UniversalResponse body);
}
