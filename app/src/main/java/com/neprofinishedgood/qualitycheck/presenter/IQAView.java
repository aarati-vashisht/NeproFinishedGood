package com.neprofinishedgood.qualitycheck.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.raf.model.ScanCountingResponse;

public interface IQAView {
    void onFailure(String message);

    void onSuccess(ScanCountingResponse body);

    void onUpdateRejectedFailure(String message);

    void onUpdateRejectedSuccess(UniversalResponse body);
}