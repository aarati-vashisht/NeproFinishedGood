package com.neprofinishedgood.move.transferpresenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.AssignedStillages;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.move.model.TransferDetailResponseModel;
import com.neprofinishedgood.move.model.TransferListResponseModel;

public interface IMoveTransferView {

    void onGetTransDoneSuccess(UniversalResponse universalResponse);

    void onGetTransDoneFailure(String message);

}
