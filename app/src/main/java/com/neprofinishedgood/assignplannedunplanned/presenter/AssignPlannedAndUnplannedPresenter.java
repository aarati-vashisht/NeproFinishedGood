package com.neprofinishedgood.assignplannedunplanned.presenter;

import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.assignplannedunplanned.model.AssignedUnAssignedInput;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.LocationData;
import com.neprofinishedgood.plannedandunplannedmove.model.LocationInput;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignPlannedAndUnplannedPresenter implements IAssignPlannedAndUnplannedInterFace {
    IAssignePlannedUnplannedView unplannedView;

    public AssignPlannedAndUnplannedPresenter(IAssignePlannedUnplannedView unplannedView) {
        this.unplannedView = unplannedView;
    }

    @Override
    public void callScanStillageService(MoveInput moveInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<ScanStillageResponse> call = apiInterface.getAssigningData(moveInput);
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
            unplannedView.onFailure(body.getMessage());
        } else {
            unplannedView.onSuccess(body);
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
            unplannedView.onLocationFailure(body.getMessage());
        } else {
            unplannedView.onLocationSuccess(body);
        }
    }

    @Override
    public void callAssigneUnassignedServcie(AssignedUnAssignedInput assignedUnAssignedInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<UniversalResponse> call = apiInterface.updateAssigningData(assignedUnAssignedInput);
        call.enqueue(new Callback<UniversalResponse>() {
            @Override
            public void onResponse(Call<UniversalResponse> call, Response<UniversalResponse> response) {
                getAssigneUnassigned(response.body());
            }

            @Override
            public void onFailure(Call<UniversalResponse> call, Throwable t) {
                getAssigneUnassigned(null);

            }
        });
    }

    @Override
    public void getAssigneUnassigned(UniversalResponse body) {
        if (body == null) {
            unplannedView.onAssigneUnassignedFailure(body.getMessage());
        } else {
            unplannedView.onAssigneUnassignedSuccess(body);
        }
    }
}