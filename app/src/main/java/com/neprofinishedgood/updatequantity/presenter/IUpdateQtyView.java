package com.neprofinishedgood.updatequantity.presenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.ScanStillageResponse;

public interface IUpdateQtyView {

    void onFailure(String message);

    void onSuccess(ScanStillageResponse body);


    void onUpdateQtyFailure(String message);

    void onUpdateQtySuccess(UniversalResponse body);
}
