package com.neprofinishedgood.pickandload.presenter;

import android.app.Activity;

import com.neprofinishedgood.R;
import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.pickandload.model.LoadingPlanDetails;
import com.neprofinishedgood.pickandload.model.LoadingPlanInput;
import com.neprofinishedgood.pickandload.model.UpdateLoadInput;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PickAndLoadItemPresenter implements IPickLoadItemInterface {
    IPickLoadItemView iPickLoadItemView;
    Activity activity;

    public PickAndLoadItemPresenter(IPickLoadItemView iPickLoadItemView, Activity activity) {
        this.activity = activity;
        this.iPickLoadItemView = iPickLoadItemView;
    }

    @Override
    public void callGetLoadingPlanDetails(LoadingPlanInput loadingPlanInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<LoadingPlanDetails> call = apiInterface.getLoadingPlanDetails(loadingPlanInput);
        call.enqueue(new Callback<LoadingPlanDetails>() {
            @Override
            public void onResponse(Call<LoadingPlanDetails> call, Response<LoadingPlanDetails> response) {
                getGetLoadingPlanDetailsResponse(response.body());
            }

            @Override
            public void onFailure(Call<LoadingPlanDetails> call, Throwable t) {
                getGetLoadingPlanDetailsResponse(null);

            }
        });
    }

    @Override
    public void getGetLoadingPlanDetailsResponse(LoadingPlanDetails body) {
        if (body == null) {
            iPickLoadItemView.onFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iPickLoadItemView.onSuccess(body);
        }
    }

    @Override
    public void callUpdateLoadService(UpdateLoadInput updateLoadInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<UniversalResponse> call = apiInterface.getupdateLoadInput(updateLoadInput);
        call.enqueue(new Callback<UniversalResponse>() {
            @Override
            public void onResponse(Call<UniversalResponse> call, Response<UniversalResponse> response) {
                getUpdateLoadingPlanDetailsResponse(response.body());
            }

            @Override
            public void onFailure(Call<UniversalResponse> call, Throwable t) {
                getUpdateLoadingPlanDetailsResponse(null);

            }
        });
    }

    @Override
    public void getUpdateLoadingPlanDetailsResponse(UniversalResponse body) {
        if (body == null) {
            iPickLoadItemView.onUpdateLoadingPlanDetailsFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iPickLoadItemView.onUpdateLoadingPlanDetailsSuccess(body);
        }
    }

    @Override
    public void callEndPickService(LoadingPlanInput loadingPlanInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<UniversalResponse> call = apiInterface.endPickInput(loadingPlanInput);
        call.enqueue(new Callback<UniversalResponse>() {
            @Override
            public void onResponse(Call<UniversalResponse> call, Response<UniversalResponse> response) {
                getEndPickResponse(response.body());
            }

            @Override
            public void onFailure(Call<UniversalResponse> call, Throwable t) {
                getEndPickResponse(null);
            }
        });
    }

    @Override
    public void getEndPickResponse(UniversalResponse body) {
        if (body == null) {
            iPickLoadItemView.onEndPickFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iPickLoadItemView.onEndPickSuccess(body);
        }
    }


}
