package com.neprofinishedgood.lookup.presenter;

import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LookUpPresenter implements ILookUpInterface {
    ILookUpView iLookUpView;

    public LookUpPresenter(ILookUpView iLookUpView) {
        this.iLookUpView = iLookUpView;

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
            iLookUpView.onFailure(body.getMessage());
        } else {
            iLookUpView.onSuccess(body);
        }
    }

}
