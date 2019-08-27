package com.neprofinishedgood.assign.presenter;

import android.app.Activity;

import com.neprofinishedgood.R;
import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.assign.model.AisleInput;
import com.neprofinishedgood.assign.model.AssignedUnAssignedInput;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.LocationData;
import com.neprofinishedgood.move.model.LocationInput;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignPresenter implements IAssignInterFace {
    IAssignView unplannedView;
    Activity activity;

    public AssignPresenter(IAssignView unplannedView, Activity activity) {
        this.unplannedView = unplannedView;
        this.activity = activity;
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
            unplannedView.onFailure(activity.getString(R.string.something_went_wrong_please_try_again));
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
            unplannedView.onLocationFailure(activity.getString(R.string.something_went_wrong_please_try_again));
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
            unplannedView.onAssigneUnassignedFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            unplannedView.onAssigneUnassignedSuccess(body);
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
            unplannedView.onAisleSelectionFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            unplannedView.onAisleSelectionSuccess(body);
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
            unplannedView.onRackSelectionFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            unplannedView.onRackSelectionSuccess(body);
        }
    }
}
