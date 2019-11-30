package com.neprofinishedgood.assigntransfer.presenter;

import android.app.Activity;

import com.neprofinishedgood.R;
import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.assigntransfer.model.AssignTransInput;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.transferstillage.model.WareHouseInput;
import com.neprofinishedgood.transferstillage.model.WareHouseResponse;
import com.neprofinishedgood.transferstillage.presenter.ITransferView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IAssignTransPresenter implements IAssignTransInterface {

    private IAssignTransView iAssignTransView;
    private Activity activity;

    public IAssignTransPresenter(IAssignTransView iAssignTransView, Activity activity) {
        this.iAssignTransView = iAssignTransView;
        this.activity = activity;
    }

    @Override
    public void callScanStillageService(MoveInput moveInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<ScanStillageResponse> call = apiInterface.scanTransferStillage(moveInput);
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
            iAssignTransView.onFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iAssignTransView.onSuccess(body);
        }
    }

    @Override
    public void callGetWareHouse(WareHouseInput wareHouseInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<WareHouseResponse> call = apiInterface.getWareHouse(wareHouseInput);
        call.enqueue(new Callback<WareHouseResponse>() {
            @Override
            public void onResponse(Call<WareHouseResponse> call, Response<WareHouseResponse> response) {
                getWareHouseResponse(response.body());
            }

            @Override
            public void onFailure(Call<WareHouseResponse> call, Throwable t) {
                getWareHouseResponse(null);

            }
        });
    }

    @Override
    public void getWareHouseResponse(WareHouseResponse body) {
        if (body == null) {
            iAssignTransView.onGetWareHouseFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iAssignTransView.onGetWareHouseSuccess(body);
        }
    }

    @Override
    public void callUpdateAssignTransfer(AssignTransInput assignTransInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<UniversalResponse> call = apiInterface.callAssignTransferService(assignTransInput);
        call.enqueue(new Callback<UniversalResponse>() {
            @Override
            public void onResponse(Call<UniversalResponse> call, Response<UniversalResponse> response) {
                getUpdateAssignTransferResponse(response.body());
            }

            @Override
            public void onFailure(Call<UniversalResponse> call, Throwable t) {
                getUpdateAssignTransferResponse(null);

            }
        });
    }

    @Override
    public void getUpdateAssignTransferResponse(UniversalResponse body) {
        if (body == null) {
            iAssignTransView.onUpdateAssignTransFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iAssignTransView.onUpdateAssignTransSuccess(body);
        }
    }
}
