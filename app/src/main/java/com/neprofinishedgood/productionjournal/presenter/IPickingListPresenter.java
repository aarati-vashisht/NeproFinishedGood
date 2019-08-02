package com.neprofinishedgood.productionjournal.presenter;

import android.app.Activity;

import com.neprofinishedgood.R;
import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.productionjournal.model.PickingListSearchInput;
import com.neprofinishedgood.productionjournal.model.PickingListSearchResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IPickingListPresenter implements IPickingListInterface{

    IPickingListView iPickingListView;
    Activity activity;

    public IPickingListPresenter(IPickingListView iPickingListView, Activity activity) {
        this.iPickingListView = iPickingListView;
        this.activity = activity;
    }


    @Override
    public void callSearchItemService(PickingListSearchInput pickingListSearchInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<PickingListSearchResponse> call = apiInterface.searchItemProcessService(pickingListSearchInput);
        call.enqueue(new Callback<PickingListSearchResponse>() {
            @Override
            public void onResponse(Call<PickingListSearchResponse> call, Response<PickingListSearchResponse> response) {
                getSearchItemResponse(response.body());
            }

            @Override
            public void onFailure(Call<PickingListSearchResponse> call, Throwable t) {
                getSearchItemResponse(null);

            }
        });
    }

    @Override
    public void getSearchItemResponse(PickingListSearchResponse body) {
        if (body == null) {
            iPickingListView.onPickingSearchFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iPickingListView.onPickingSearchSuccess(body);
        }
    }
}
