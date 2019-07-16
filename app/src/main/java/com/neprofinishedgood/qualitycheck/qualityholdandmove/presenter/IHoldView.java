package com.neprofinishedgood.qualitycheck.qualityholdandmove.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.raf.model.ScanCountingResponse;

public interface IHoldView {
    void onFailure(String message);

    void onSuccess(ScanCountingResponse body);


    void onHoldUnholdFailure(String message);

    void onHoldUnholdSuccess(UniversalResponse body);
}
