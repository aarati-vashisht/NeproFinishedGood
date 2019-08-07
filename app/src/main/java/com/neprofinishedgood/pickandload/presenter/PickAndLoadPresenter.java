package com.neprofinishedgood.pickandload.presenter;

import android.app.Activity;

import com.neprofinishedgood.R;
import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.pickandload.model.LoadingPlanInput;
import com.neprofinishedgood.pickandload.model.LoadingPlanResponse;
import com.neprofinishedgood.move.model.AllAssignedDataInput;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PickAndLoadPresenter implements IPickAndLoadInterFace {
    IPickAndLoadVIew iPickAndLoadVIew;
    Activity activity;

    public PickAndLoadPresenter(IPickAndLoadVIew iPickAndLoadVIew, Activity activity) {
        this.iPickAndLoadVIew = iPickAndLoadVIew;
        this.activity = activity;
    }

    @Override
    public void callGetLoadingPlan(AllAssignedDataInput allAssignedDataInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<LoadingPlanResponse> call = apiInterface.getLoadingPlan(allAssignedDataInput);
        call.enqueue(new Callback<LoadingPlanResponse>() {
            @Override
            public void onResponse(Call<LoadingPlanResponse> call, Response<LoadingPlanResponse> response) {
                getGetLoadingPlanResponse(response.body());
            }

            @Override
            public void onFailure(Call<LoadingPlanResponse> call, Throwable t) {
                getGetLoadingPlanResponse(null);

            }
        });
    }

    @Override
    public void getGetLoadingPlanResponse(LoadingPlanResponse body) {
        if (body == null) {
            iPickAndLoadVIew.onFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iPickAndLoadVIew.onSuccess(body);
        }
    }

    @Override
    public void callCancelLoadingPlan(LoadingPlanInput loadingPlanInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<UniversalResponse> call = apiInterface.cancelLoadingInput(loadingPlanInput);
        call.enqueue(new Callback<UniversalResponse>() {
            @Override
            public void onResponse(Call<UniversalResponse> call, Response<UniversalResponse> response) {
                getCancelLoadingResponse(response.body());
            }

            @Override
            public void onFailure(Call<UniversalResponse> call, Throwable t) {
                getCancelLoadingResponse(null);

            }
        });
    }

    @Override
    public void getCancelLoadingResponse(UniversalResponse body) {
        if (body == null) {
            iPickAndLoadVIew.onCancelFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iPickAndLoadVIew.onCancelSuccess(body);
        }
    }
}