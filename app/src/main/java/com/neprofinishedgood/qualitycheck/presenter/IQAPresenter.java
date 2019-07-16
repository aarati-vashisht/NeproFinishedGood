package com.neprofinishedgood.qualitycheck.presenter;

import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;
import com.neprofinishedgood.qualitycheck.model.RejectedInput;
import com.neprofinishedgood.raf.model.ScanCountingResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IQAPresenter implements IQAInterface {
    IQAView iqaView;

    public IQAPresenter(IQAView iqaView) {
        this.iqaView = iqaView;
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
            iqaView.onFailure(body.getMessage());
        } else {
            iqaView.onSuccess(body);
        }
    }

    @Override
    public void callUpdateRejectedService(RejectedInput rejectedInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<UniversalResponse> call = apiInterface.updatedRejectedStillage(rejectedInput);
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
            iqaView.onUpdateRejectedFailure(body.getMessage());
        } else {
            iqaView.onUpdateRejectedSuccess(body);
        }
    }


}
