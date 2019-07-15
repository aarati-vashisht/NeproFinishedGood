package com.neprofinishedgood.plannedandunplannedmove.presenter;

import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.LocationData;
import com.neprofinishedgood.plannedandunplannedmove.model.LocationInput;
import com.neprofinishedgood.plannedandunplannedmove.model.UpdateMoveLocationInput;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IMovePresenter implements IMoveInterface {
    IMoveView iMoveView;

    public IMovePresenter(IMoveView iLoginView) {
        this.iMoveView = iLoginView;
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
            iMoveView.onUpdateMoveFailure(body.getMessage());
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
            iMoveView.onLocationFailure(body.getMessage());
        } else {
            iMoveView.onLocationSuccess(body);
        }
    }


}
