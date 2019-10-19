package com.neprofinishedgood.qualitycheck.qualityhold.presenter;

import android.app.Activity;

import com.neprofinishedgood.R;
import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.qualitycheck.model.QualityInput;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IHoldPresenter implements IHoldInterface {
    IHoldView iHoldView;
    Activity activity;

    public IHoldPresenter(IHoldView iHoldView, Activity activity) {
        this.iHoldView = iHoldView;
        this.activity = activity;
    }


    @Override
    public void callScanStillageService(MoveInput moveInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<ScanStillageResponse> call = apiInterface.scanLookUpStillage(moveInput);
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
            iHoldView.onFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iHoldView.onSuccess(body);
        }
    }

    @Override
    public void callHoldUnholdService(QualityInput qualityInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<UniversalResponse> call = apiInterface.updatedHoldUnHoldStillage(qualityInput);
        call.enqueue(new Callback<UniversalResponse>() {
            @Override
            public void onResponse(Call<UniversalResponse> call, Response<UniversalResponse> response) {
                getHoldUnholdResponse(response.body());
            }

            @Override
            public void onFailure(Call<UniversalResponse> call, Throwable t) {
                getHoldUnholdResponse(null);

            }
        });
    }

    @Override
    public void getHoldUnholdResponse(UniversalResponse body) {
        if (body == null) {
            iHoldView.onHoldUnholdFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iHoldView.onHoldUnholdSuccess(body);
        }
    }


}
