package com.neprofinishedgood.lookup.presenter;

import com.neprofinishedgood.move.model.ScanStillageResponse;

public interface ILookUpView {
    void onFailure(String message);

    void onSuccess(ScanStillageResponse body);
}
