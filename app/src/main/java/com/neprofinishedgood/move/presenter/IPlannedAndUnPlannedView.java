package com.neprofinishedgood.move.presenter;

import com.neprofinishedgood.move.model.AssignedStillages;
import com.neprofinishedgood.move.model.ScanStillageResponse;

public interface IPlannedAndUnPlannedView {
    void onAssignedFailure(String message);

    void onAssignedSuccess(AssignedStillages body);

    void onSuccess(ScanStillageResponse body);

    void onFailure(String message);

}
