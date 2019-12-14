package com.neprofinishedgood.transferstillage.presenter;

import android.app.Activity;

import com.neprofinishedgood.R;
import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.transferstillage.model.LocationResponse;
import com.neprofinishedgood.transferstillage.model.LocationsInput;
import com.neprofinishedgood.transferstillage.model.ShipInput;
import com.neprofinishedgood.transferstillage.model.TransferInput;
import com.neprofinishedgood.transferstillage.model.WareHouseInput;
import com.neprofinishedgood.transferstillage.model.WareHouseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransferPresenter implements ITransferInterface {
    ITransferView iTransferView;
    Activity activity;

    public TransferPresenter(ITransferView iTransferView, Activity activity) {
        this.iTransferView = iTransferView;
        this.activity = activity;
    }

    @Override
    public void callScanStillageService(MoveInput moveInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<ScanStillageResponse> call = apiInterface.scanTransferStillage(moveInput);
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
            try {
                iTransferView.onUpdateTransferFailure(activity.getString(R.string.something_went_wrong_please_try_again));
            }catch (Exception e){
                iTransferView.onUpdateTransferFailure("Something Went Wrong. Please Try Again");
                e.printStackTrace();
            }

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
            iTransferView.onGetWareHouseFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iTransferView.onGetWareHouseSuccess(body);
        }
    }

    @Override
    public void callNewTranferStillage(TransferInput transferInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<UniversalResponse> call = apiInterface.callNewTranferStillage(transferInput);
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
    public void callGetLocation(LocationsInput locationsInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<LocationResponse> call = apiInterface.getLocation(locationsInput);
        call.enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                getLocationResponse(response.body());
            }

            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {
                getLocationResponse(null);

            }
        });
    }

    @Override
    public void getLocationResponse(LocationResponse body) {
        if (body == null) {
            iTransferView.onGetLocationFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iTransferView.onGetLocationSuccess(body);
        }
    }

}
