package com.neprofinishedgood.plannedandunplannedmove.presenter;

import android.app.Activity;

import com.neprofinishedgood.R;
import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.plannedandunplannedmove.PlannedAndUnPlannedMoveActivity;
import com.neprofinishedgood.plannedandunplannedmove.model.AllAssignedDataInput;
import com.neprofinishedgood.plannedandunplannedmove.model.AssignedStillages;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IPlannedUnplannedPresenter implements IPLannedUnplannedInterface {
    IPlannedAndUnPlannedView iPlannedAndUnPlannedView;
    Activity activity;

    public IPlannedUnplannedPresenter(IPlannedAndUnPlannedView iPlannedAndUnPlannedView, Activity activity) {
        this.iPlannedAndUnPlannedView = iPlannedAndUnPlannedView;
        this.activity = activity;
    }


    @Override
    public void callGetAllAssignedData(AllAssignedDataInput allAssignedDataInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<AssignedStillages> call = apiInterface.getAllAssignedStillages(allAssignedDataInput);
        call.enqueue(new Callback<AssignedStillages>() {
            @Override
            public void onResponse(Call<AssignedStillages> call, Response<AssignedStillages> response) {
                getAllAssignedData(response.body());
            }

            @Override
            public void onFailure(Call<AssignedStillages> call, Throwable t) {
                getAllAssignedData(null);

            }
        });
    }

    @Override
    public void getAllAssignedData(AssignedStillages body) {
        if (body == null) {
            iPlannedAndUnPlannedView.onAssignedFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iPlannedAndUnPlannedView.onAssignedSuccess(body);
        }
    }

    @Override
    public void getMoveResponse(ScanStillageResponse body) {
        if (body == null) {
            iPlannedAndUnPlannedView.onFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iPlannedAndUnPlannedView.onSuccess(body);
        }
    }

    @Override
    public void callScanStillageService(MoveInput moveInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<ScanStillageResponse> call = apiInterface.stillageMoving(moveInput);
        call.enqueue(new Callback<ScanStillageResponse>() {
            @Override
            public void onResponse(Call<ScanStillageResponse> call, Response<ScanStillageResponse> response) {
                getMoveResponse(response.body());
            }

            @Override
            public void onFailure(Call<ScanStillageResponse> call, Throwable t) {
                getMoveResponse(null);

            }
        });
    }
}
