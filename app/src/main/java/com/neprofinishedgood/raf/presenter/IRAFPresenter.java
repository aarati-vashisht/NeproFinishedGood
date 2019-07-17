package com.neprofinishedgood.raf.presenter;

import android.app.Activity;

import com.neprofinishedgood.R;
import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;
import com.neprofinishedgood.raf.RAFActivity;
import com.neprofinishedgood.raf.model.RafInput;
import com.neprofinishedgood.raf.model.ScanCountingResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IRAFPresenter implements IRAFInterface {
    IRAFView irafView;
    Activity activity;

    public IRAFPresenter(IRAFView irafView, Activity activity) {
        this.irafView = irafView;
        this.activity = activity;
    }


    @Override
    public void callScanStillageService(MoveInput moveInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<ScanStillageResponse> call = apiInterface.scanStillageCount(moveInput);
        call.enqueue(new Callback<ScanStillageResponse>() {
            @Override
            public void onResponse(Call<ScanStillageResponse> call, Response<ScanStillageResponse> response) {
                getRAFResponse(response.body());
            }

            @Override
            public void onFailure(Call<ScanStillageResponse> call, Throwable t) {
                getRAFResponse(null);

            }
        });
    }

    @Override
    public void getRAFResponse(ScanStillageResponse body) {
        if (body == null) {
            irafView.onFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            irafView.onSuccess(body);
        }
    }

    @Override
    public void callRAFServcie(RafInput rafInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<UniversalResponse> call = apiInterface.updateRAF(rafInput);
        call.enqueue(new Callback<UniversalResponse>() {
            @Override
            public void onResponse(Call<UniversalResponse> call, Response<UniversalResponse> response) {
                getUpdateMoveResponse(response.body());
            }

            @Override
            public void onFailure(Call<UniversalResponse> call, Throwable t) {
                getUpdateMoveResponse(null);

            }
        });
    }

    @Override
    public void getUpdateMoveResponse(UniversalResponse body) {
        if (body == null) {
            irafView.onUpdateRAFFailure(body.getMessage());
        } else {
            irafView.onUpdateRAFSuccess(body);
        }
    }
}
