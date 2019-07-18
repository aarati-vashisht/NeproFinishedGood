package com.neprofinishedgood.transferstillage.presenter;

import android.app.Activity;

import com.neprofinishedgood.R;
import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.lookup.presenter.ILookUpInterface;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;
import com.neprofinishedgood.transferstillage.TransferStillageActivity;
import com.neprofinishedgood.transferstillage.model.TransferInput;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransferPresenter implements ITransferInterface {
    ITransferView iTransferView;
    Activity activity;

    public TransferPresenter(ITransferView iTransferView, Activity activity) {
        this.iTransferView = iTransferView;

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
            iTransferView.onFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iTransferView.onSuccess(body);
        }
    }

    @Override
    public void callUpdateTransferStillage(TransferInput transferInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<UniversalResponse> call = apiInterface.updateTransferStillage(transferInput);
        call.enqueue(new Callback<UniversalResponse>() {
            @Override
            public void onResponse(Call<UniversalResponse> call, Response<UniversalResponse> response) {
                getUpdateTransferStillageResponse(response.body());
            }

            @Override
            public void onFailure(Call<UniversalResponse> call, Throwable t) {
                getUpdateTransferStillageResponse(null);

            }
        });
    }

    @Override
    public void getUpdateTransferStillageResponse(UniversalResponse body) {
        if (body == null) {
            iTransferView.onUpdateTransferFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iTransferView.onUpdateTransferSuccess(body);
        }
    }

}
