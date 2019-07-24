package com.neprofinishedgood.pickandload.presenter;

import android.app.Activity;

import com.neprofinishedgood.R;
import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.pickandload.model.LoadingPlanDetailInput;
import com.neprofinishedgood.pickandload.model.LoadingPlanDetailResponse;
import com.neprofinishedgood.pickandload.model.LoadingPlanResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ILoadingPlanDetailPresenter implements ILoadingPlanDetailInterface {

    Activity activity;
    ILoadingPlanDetailView iLoadingPlanDetailView;

    public ILoadingPlanDetailPresenter(ILoadingPlanDetailView iLoadingPlanDetailView, Activity activity) {
        this.iLoadingPlanDetailView = iLoadingPlanDetailView;
        this.activity = activity;
    }

    @Override
    public void callLoadingPlanDetailService(LoadingPlanDetailInput loadingPlanDetailInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<LoadingPlanDetailResponse> call = apiInterface.getLoadingPlanDetails(loadingPlanDetailInput);
        call.enqueue(new Callback<LoadingPlanDetailResponse>() {
            @Override
            public void onResponse(Call<LoadingPlanDetailResponse> call, Response<LoadingPlanDetailResponse> response) {
                getLoadingPlanDetailResponse(response.body());
            }

            @Override
            public void onFailure(Call<LoadingPlanDetailResponse> call, Throwable t) {
                getLoadingPlanDetailResponse(null);

            }
        });
    }

    @Override
    public void getLoadingPlanDetailResponse(LoadingPlanDetailResponse body) {
        if (body == null) {
            iLoadingPlanDetailView.onLoadingPlanDetailFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iLoadingPlanDetailView.onLoadingPlanDetailSuccess(body);
        }
    }
}
