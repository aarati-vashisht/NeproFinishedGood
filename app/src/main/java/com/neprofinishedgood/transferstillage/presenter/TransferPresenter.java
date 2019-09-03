package com.neprofinishedgood.transferstillage.presenter;

import android.app.Activity;

import com.neprofinishedgood.R;
import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.transferstillage.model.ShipInput;
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
        Call<ScanStillageResponse> call = apiInterface.scanLookUpStillage(moveInput);
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

    @Override
    public void callShipStillage(ShipInput shipInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<UniversalResponse> call = apiInterface.shipTransfer(shipInput);
        call.enqueue(new Callback<UniversalResponse>() {
            @Override
            public void onResponse(Call<UniversalResponse> call, Response<UniversalResponse> response) {
                getShipStillageResponse(response.body());
            }

            @Override
            public void onFailure(Call<UniversalResponse> call, Throwable t) {
                getShipStillageResponse(null);

            }
        });
    }

    @Override
    public void getShipStillageResponse(UniversalResponse body) {
        if (body == null) {
            iTransferView.onShipFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iTransferView.onShipSuccess(body);
        }
    }

}
