package com.neprofinishedgood.lookup.presenter;

import android.app.Activity;

import com.neprofinishedgood.R;
import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LookUpPresenter implements ILookUpInterface {
    ILookUpView iLookUpView;
    Activity activity;

    public LookUpPresenter(ILookUpView iLookUpView, Activity activity) {
        this.iLookUpView = iLookUpView;
        this.activity = activity;

    }

    @Override
    public void callScanStillageService(MoveInput moveInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<ScanStillageResponse> call = apiInterface.scanLookUpStillage(moveInput);
        call.enqueue(new Callback<ScanStillageResponse>() {
            @Override
            public void onResponse(Call<ScanStillageResponse> call, Response<ScanStillageResponse> response) {
                getScanMergeStillageResponse(response.body());
            }

            @Override
            public void onFailure(Call<ScanStillageResponse> call, Throwable t) {
                getScanMergeStillageResponse(null);

            }
        });
    }

    @Override
    public void getScanMergeStillageResponse(ScanStillageResponse body) {
        if (body == null) {
            iLookUpView.onFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iLookUpView.onSuccess(body);
        }
    }

}
