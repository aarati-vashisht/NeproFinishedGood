package com.neprofinishedgood.qualitycheck.rejectcompletestillage.presenter;

import android.app.Activity;

import com.neprofinishedgood.R;
import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.qualitycheck.model.RejectedCompleteInput;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IQACompletePresenter implements IQACompleteInterface {
    IQACompleteView iqaView;
    Activity activity;

    public IQACompletePresenter(IQACompleteView iqaView, Activity activity) {
        this.iqaView = iqaView;
        this.activity = activity;
    }


    @Override
    public void callScanStillageService(MoveInput moveInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<ScanStillageResponse> call = apiInterface.rejectedStillageDetails(moveInput);
        call.enqueue(new Callback<ScanStillageResponse>() {
            @Override
            public void onResponse(Call<ScanStillageResponse> call, Response<ScanStillageResponse> response) {
                getScanStillageResponse(response.body());
            }

            @Override
            public void onFailure(Call<ScanStillageResponse> call, Throwable t) {
                getScanStillageResponse(null);

            }
        });
    }

    @Override
    public void getScanStillageResponse(ScanStillageResponse body) {
        if (body == null) {
            iqaView.onFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iqaView.onSuccess(body);
        }
    }

    @Override
    public void callUpdateRejectedService(RejectedCompleteInput rejectedCompleteInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<UniversalResponse> call = apiInterface.completeRejectedStillage(rejectedCompleteInput);
        call.enqueue(new Callback<UniversalResponse>() {
            @Override
            public void onResponse(Call<UniversalResponse> call, Response<UniversalResponse> response) {
                getUpdateRejectedResponse(response.body());
            }

            @Override
            public void onFailure(Call<UniversalResponse> call, Throwable t) {
                getUpdateRejectedResponse(null);

            }
        });
    }

    @Override
    public void getUpdateRejectedResponse(UniversalResponse body) {
        if (body == null) {
            iqaView.onUpdateRejectedFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iqaView.onUpdateRejectedSuccess(body);
        }
    }

}
