package com.neprofinishedgood.mergestillage.presenter;

import android.app.Activity;

import com.neprofinishedgood.R;
import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.mergestillage.model.UpgradeMergeInput;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MergeStillagePresenter implements IMergeStillageInterface {
    IMergeStillageView iMergeStillageView;
    Activity activity;

    public MergeStillagePresenter(IMergeStillageView iMergeStillageView, Activity activity) {
        this.iMergeStillageView = iMergeStillageView;
        this.activity = activity;

    }

    @Override
    public void callScanStillageService(MoveInput moveInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<ScanStillageResponse> call = apiInterface.scanMergeStillage(moveInput);
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
            iMergeStillageView.onFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iMergeStillageView.onSuccess(body);
        }
    }

    @Override
    public void callUpdateMergeStillage(UpgradeMergeInput upgradeMergeInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<UniversalResponse> call = apiInterface.updateMergeStillage(upgradeMergeInput);
        call.enqueue(new Callback<UniversalResponse>() {
            @Override
            public void onResponse(Call<UniversalResponse> call, Response<UniversalResponse> response) {
                getUpdateMergeStillageResponse(response.body());
            }

            @Override
            public void onFailure(Call<UniversalResponse> call, Throwable t) {
                getUpdateMergeStillageResponse(null);

            }
        });
    }

    @Override
    public void getUpdateMergeStillageResponse(UniversalResponse body) {
        if (body == null) {
            iMergeStillageView.onUpdateMergeStillageFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iMergeStillageView.onUpdateMergeStillageSuccess(body);
        }
    }
}
