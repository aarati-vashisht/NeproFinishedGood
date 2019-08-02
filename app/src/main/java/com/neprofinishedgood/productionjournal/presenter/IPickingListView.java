package com.neprofinishedgood.productionjournal.presenter;

import com.neprofinishedgood.productionjournal.model.PickingListSearchResponse;

public interface IPickingListView {

    void onPickingSearchFailure(String message);

    void onPickingSearchSuccess(PickingListSearchResponse body);

}
