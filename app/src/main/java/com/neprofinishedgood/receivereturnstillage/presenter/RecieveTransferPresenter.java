package com.neprofinishedgood.receivereturnstillage.presenter;

import android.app.Activity;

import com.neprofinishedgood.R;
import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;
import com.neprofinishedgood.receivereturnstillage.ReceiveReturnStillageActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecieveTransferPresenter implements IRecieveTransferInterface {
    IRecieveTransferView iRecieveTransferView;
    Activity activity;

    public RecieveTransferPresenter(IRecieveTransferView iRecieveTransferView, Activity activity) {
        this.iRecieveTransferView = iRecieveTransferView;

    }

    @Override
    public void callScanStillageService(MoveInput moveInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<ScanStillageResponse> call = apiInterface.scanRecievedTransfer(moveInput);
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
            iRecieveTransferView.onFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iRecieveTransferView.onSuccess(body);
        }
    }

    @Override
    public void callUpdateRecieveTransferStillage(MoveInput moveInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<UniversalResponse> call = apiInterface.updateRecievedTransfer(moveInput);
        call.enqueue(new Callback<UniversalResponse>() {
            @Override
            public void onResponse(Call<UniversalResponse> call, Response<UniversalResponse> response) {
                getUpdateRecieveTransferStillageResponse(response.body());
            }

            @Override
            public void onFailure(Call<UniversalResponse> call, Throwable t) {
                getUpdateRecieveTransferStillageResponse(null);

            }
        });
    }

    @Override
    public void getUpdateRecieveTransferStillageResponse(UniversalResponse body) {
        if (body == null) {
            iRecieveTransferView.onUpdateRecieveTransferFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iRecieveTransferView.onUpdateRecieveTransferSuccess(body);
        }
    }

}
