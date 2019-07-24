package com.neprofinishedgood.pickandload.presenter;

import android.app.Activity;

import com.neprofinishedgood.R;
import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.pickandload.model.LoadingPlanResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ILoadingPlanPresenter implements ILoadingPlanInterface {

    Activity activity;
    ILoadingPlanView iLoadingPlanView;

    public ILoadingPlanPresenter(ILoadingPlanView iLoadingPlanView, Activity activity) {
        this.iLoadingPlanView = iLoadingPlanView;
        this.activity = activity;
    }

    @Override
    public void callLoadingPlanService(MoveInput moveInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<LoadingPlanResponse> call = apiInterface.getLoadingPlan(moveInput);
        call.enqueue(new Callback<LoadingPlanResponse>() {
            @Override
            public void onResponse(Call<LoadingPlanResponse> call, Response<LoadingPlanResponse> response) {
                getLoadingPlanResponse(response.body());
            }

            @Override
            public void onFailure(Call<LoadingPlanResponse> call, Throwable t) {
                getLoadingPlanResponse(null);

            }
        });
    }

    @Override
    public void getLoadingPlanResponse(LoadingPlanResponse body) {
        if (body == null) {
            iLoadingPlanView.onLoadingPlanFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iLoadingPlanView.onLoadingPlanSuccess(body);
        }
    }
}
