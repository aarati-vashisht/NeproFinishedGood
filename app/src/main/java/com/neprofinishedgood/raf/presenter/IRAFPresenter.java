package com.neprofinishedgood.raf.presenter;

import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.raf.model.RafInput;
import com.neprofinishedgood.raf.model.ScanCountingResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IRAFPresenter implements IRAFInterface {
    IRAFView irafView;

    public IRAFPresenter(IRAFView irafView) {
        this.irafView = irafView;
    }


    @Override
    public void callScanStillageService(MoveInput moveInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<ScanCountingResponse> call = apiInterface.scanStillageCount(moveInput);
        call.enqueue(new Callback<ScanCountingResponse>() {
            @Override
            public void onResponse(Call<ScanCountingResponse> call, Response<ScanCountingResponse> response) {
                getRAFResponse(response.body());
            }

            @Override
            public void onFailure(Call<ScanCountingResponse> call, Throwable t) {
                getRAFResponse(null);

            }
        });
    }

    @Override
    public void getRAFResponse(ScanCountingResponse body) {
        if (body == null) {
            irafView.onFailure(body.getMessage());
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
