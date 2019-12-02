package com.neprofinishedgood.move.transferpresenter;

import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.TransDoneInputModel;

public interface IMoveTransferInterface {

    void callPostAssignTransfer(TransDoneInputModel transDoneInputModel);

    void getPostAssignTransfer(UniversalResponse body);

}
