package com.neprofinishedgood.move.presenter;

import android.app.Activity;

import com.neprofinishedgood.R;
import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.AllAssignedDataInput;
import com.neprofinishedgood.move.model.AssignedStillages;
import com.neprofinishedgood.move.model.MoveInput;
import com.neprofinishedgood.move.model.ScanStillageResponse;
import com.neprofinishedgood.move.model.TransferDetailInputModel;
import com.neprofinishedgood.move.model.TransferDetailResponseModel;
import com.neprofinishedgood.move.model.TransferListResponseModel;
import com.neprofinishedgood.move.model.UpdateMoveLocationInput;

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

    @Override
    public void callMoveServcie(UpdateMoveLocationInput updateMoveLocationInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<UniversalResponse> call = apiInterface.updateMovingStatus(updateMoveLocationInput);
        call.enqueue(new Callback<UniversalResponse>() {
            @Override
            public void onResponse(Call<UniversalResponse> call, Response<UniversalResponse> response) {

            }

            @Override
            public void onFailure(Call<UniversalResponse> call, Throwable t) {


            }
        });
    }

    @Override
    public void callGetAssignTransferHeader(AllAssignedDataInput allAssignedDataInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<TransferListResponseModel> call = apiInterface.callGetAssignTransferHeader(allAssignedDataInput);
        call.enqueue(new Callback<TransferListResponseModel>() {
            @Override
            public void onResponse(Call<TransferListResponseModel> call, Response<TransferListResponseModel> response) {
                getAssignTransferHeader(response.body());
            }

            @Override
            public void onFailure(Call<TransferListResponseModel> call, Throwable t) {
                getAssignTransferHeader(null);
            }
        });
    }

    @Override
    public void getAssignTransferHeader(TransferListResponseModel body) {
        if (body == null) {
            iPlannedAndUnPlannedView.onGetTransListHeaderFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iPlannedAndUnPlannedView.onGetTransListHeaderSuccess(body);
        }
    }

    @Override
    public void callGetAssignTransferDetail(TransferDetailInputModel transferDetailInputModel) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<TransferDetailResponseModel> call = apiInterface.callGetAssignTransferDetail(transferDetailInputModel);
        call.enqueue(new Callback<TransferDetailResponseModel>() {
            @Override
            public void onResponse(Call<TransferDetailResponseModel> call, Response<TransferDetailResponseModel> response) {
                getAssignTransferDetail(response.body());
            }

            @Override
            public void onFailure(Call<TransferDetailResponseModel> call, Throwable t) {
                getAssignTransferDetail(null);
            }
        });
    }

    @Override
    public void getAssignTransferDetail(TransferDetailResponseModel body) {
        if (body == null) {
            iPlannedAndUnPlannedView.onGetTransListDetailFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iPlannedAndUnPlannedView.onGetTransListDetailSuccess(body);
        }
    }

}
