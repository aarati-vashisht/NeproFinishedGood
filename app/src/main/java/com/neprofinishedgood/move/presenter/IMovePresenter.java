package com.neprofinishedgood.move.presenter;

import android.app.Activity;

import com.neprofinishedgood.R;
import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.assign.model.AisleInput;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.LocationData;
import com.neprofinishedgood.move.model.LocationInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.move.model.UpdateMoveLocationInput;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IMovePresenter implements IMoveInterface {
    IMoveView iMoveView;
    Activity activity;

    public IMovePresenter(IMoveView iLoginView, Activity activity) {
        this.iMoveView = iLoginView;
        this.activity = activity;
    }


    @Override
    public void callMoveServcie(UpdateMoveLocationInput updateMoveLocationInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<UniversalResponse> call = apiInterface.updateMovingStatus(updateMoveLocationInput);
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
            iMoveView.onUpdateMoveFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iMoveView.onUpdateMoveSuccess(body);
        }
    }

    @Override
    public void callLocationService(LocationInput locationInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<LocationData> call = apiInterface.callLocationService(locationInput);
        call.enqueue(new Callback<LocationData>() {
            @Override
            public void onResponse(Call<LocationData> call, Response<LocationData> response) {
                getLocationData(response.body());
            }

            @Override
            public void onFailure(Call<LocationData> call, Throwable t) {
                getLocationData(null);

            }
        });
    }

    @Override
    public void getLocationData(LocationData body) {
        if (body == null) {
            iMoveView.onLocationFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iMoveView.onLocationSuccess(body);
        }
    }



    @Override
    public void callAisleSelectionService(AisleInput aisleInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<ScanStillageResponse> call = apiInterface.getRack(aisleInput);
        call.enqueue(new Callback<ScanStillageResponse>() {
            @Override
            public void onResponse(Call<ScanStillageResponse> call, Response<ScanStillageResponse> response) {
                getAisleSelectionResponse(response.body());
            }

            @Override
            public void onFailure(Call<ScanStillageResponse> call, Throwable t) {
                getAisleSelectionResponse(null);

            }
        });
    }

    @Override
    public void getAisleSelectionResponse(ScanStillageResponse body) {
        if (body == null) {
            iMoveView.onAisleSelectionFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iMoveView.onAisleSelectionSuccess(body);
        }
    }

    @Override
    public void callRackSelectionService(AisleInput aisleInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<ScanStillageResponse> call = apiInterface.getBin(aisleInput);
        call.enqueue(new Callback<ScanStillageResponse>() {
            @Override
            public void onResponse(Call<ScanStillageResponse> call, Response<ScanStillageResponse> response) {
                getRackSelectionResponse(response.body());
            }

            @Override
            public void onFailure(Call<ScanStillageResponse> call, Throwable t) {
                getRackSelectionResponse(null);

            }
        });
    }

    @Override
    public void getRackSelectionResponse(ScanStillageResponse body) {
        if (body == null) {
            iMoveView.onRackSelectionFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iMoveView.onRackSelectionSuccess(body);
        }
    }
}
