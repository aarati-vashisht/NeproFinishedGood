package com.neprofinishedgood.updatequantity.presenter;

import android.app.Activity;

import com.neprofinishedgood.R;
import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;
import com.neprofinishedgood.updatequantity.model.UpdateQtyInput;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IUpdateQtyPresenter implements IUpdateQtyInterface{

    IUpdateQtyView iUpdateQtyView;
    Activity activity;

    public IUpdateQtyPresenter(IUpdateQtyView iUpdateQtyView, Activity activity) {
        this.iUpdateQtyView = iUpdateQtyView;
        this.activity = activity;

    }

    @Override
    public void callScanStillageService(MoveInput moveInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<ScanStillageResponse> call = apiInterface.scanUpdateQuantity(moveInput);
        call.enqueue(new Callback<ScanStillageResponse>() {
            @Override
            public void onResponse(Call<ScanStillageResponse> call, Response<ScanStillageResponse> response) {
                getScanUpdateQtyResponse(response.body());
            }

            @Override
            public void onFailure(Call<ScanStillageResponse> call, Throwable t) {
                getScanUpdateQtyResponse(null);

            }
        });
    }

    @Override
    public void getScanUpdateQtyResponse(ScanStillageResponse body) {
        if (body == null) {
            iUpdateQtyView.onFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iUpdateQtyView.onSuccess(body);
        }
    }

    @Override
    public void callUpdateQtyStillageService(UpdateQtyInput updateQtyInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<UniversalResponse> call = apiInterface.updateStillageQuantity(updateQtyInput);
        call.enqueue(new Callback<UniversalResponse>() {
            @Override
            public void onResponse(Call<UniversalResponse> call, Response<UniversalResponse> response) {
                getUpdateQtyStillageResponse(response.body());
            }

            @Override
            public void onFailure(Call<UniversalResponse> call, Throwable t) {
                getUpdateQtyStillageResponse(null);

            }
        });
    }

    @Override
    public void getUpdateQtyStillageResponse(UniversalResponse body) {

    }
}
