package com.neprofinishedgood.productionjournal.presenter;

import com.neprofinishedgood.productionjournal.model.PickingListSearchInput;
import com.neprofinishedgood.productionjournal.model.PickingListSearchResponse;

public interface IPickingListInterface {

    void callSearchItemService(PickingListSearchInput pickingListSearchInput);

    void getSearchItemResponse(PickingListSearchResponse body);
}
