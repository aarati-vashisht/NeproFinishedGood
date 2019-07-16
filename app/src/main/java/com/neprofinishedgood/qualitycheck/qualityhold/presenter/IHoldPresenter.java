package com.neprofinishedgood.qualitycheck.qualityhold.presenter;

import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;
import com.neprofinishedgood.raf.model.ScanCountingResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IHoldPresenter implements IHoldInterface {
    IHoldView iHoldView;

    public IHoldPresenter(IHoldView iHoldView) {
        this.iHoldView = iHoldView;
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
            iHoldView.onFailure(body.getMessage());
        } else {
            iHoldView.onSuccess(body);
        }
    }

    @Override
    public void callHoldUnholdService(MoveInput moveInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<UniversalResponse> call = apiInterface.updatedHoldUnHoldStillage(moveInput);
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
            iHoldView.onHoldUnholdFailure(body.getMessage());
        } else {
            iHoldView.onHoldUnholdSuccess(body);
        }
    }


}
