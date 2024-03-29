package com.neprofinishedgood.qualitycheck.rejectquantity.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.ScanStillageResponse;

public interface IQAView {
    void onFailure(String message);

    void onSuccess(ScanStillageResponse body);

    void onUpdateRejectedFailure(String message);

    void onUpdateRejectedSuccess(UniversalResponse body);

    void onUpdateRejectionListFailure(String message);

    void onUpdateRejectionListSuccess(UniversalResponse body);
}
