package com.neprofinishedgood.workorderstartend.Presenter;

import android.app.Activity;

import com.neprofinishedgood.R;
import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.workorderstartend.model.WorkOrderScanInput;
import com.neprofinishedgood.workorderstartend.model.WorkOrderScanResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IWorkOrderStartEndPresenter implements IWorkOrderStartEndInterface {

    IWorkOrderStartEndView iWorkOrderStartEndView;
    Activity activity;

    public IWorkOrderStartEndPresenter(IWorkOrderStartEndView iWorkOrderStartEndView, Activity activity) {
        this.iWorkOrderStartEndView = iWorkOrderStartEndView;
        this.activity = activity;

    }

    @Override
    public void callScanWorkOrderService(WorkOrderScanInput workOrderScanInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<WorkOrderScanResponse> call = apiInterface.workOrderStartEndScan(workOrderScanInput);
        call.enqueue(new Callback<WorkOrderScanResponse>() {
            @Override
            public void onResponse(Call<WorkOrderScanResponse> call, Response<WorkOrderScanResponse> response) {
                getScanWorkOrderResponse(response.body());
            }

            @Override
            public void onFailure(Call<WorkOrderScanResponse> call, Throwable t) {
                getScanWorkOrderResponse(null);

            }
        });
    }

    @Override
    public void getScanWorkOrderResponse(WorkOrderScanResponse body) {

        if (body == null) {
            iWorkOrderStartEndView.onWorkOrderScanFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iWorkOrderStartEndView.onWorkOrderScanSuccess(body);
        }

    }

    @Override
    public void callWorkOrderStartService(WorkOrderScanInput workOrderScanInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<UniversalResponse> call = apiInterface.workOrderStartService(workOrderScanInput);
        call.enqueue(new Callback<UniversalResponse>() {
            @Override
            public void onResponse(Call<UniversalResponse> call, Response<UniversalResponse> response) {
                getWorkOrderStartResponse(response.body());
            }

            @Override
            public void onFailure(Call<UniversalResponse> call, Throwable t) {
                getWorkOrderStartResponse(null);

            }
        });
    }

    @Override
    public void getWorkOrderStartResponse(UniversalResponse body) {
        if (body == null) {
            iWorkOrderStartEndView.onWorkOrderStartFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iWorkOrderStartEndView.onWorkOrderStartSuccess(body);
        }
    }

    @Override
    public void callWorkOrderEndService(WorkOrderScanInput workOrderScanInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<UniversalResponse> call = apiInterface.workOrderEndService(workOrderScanInput);
        call.enqueue(new Callback<UniversalResponse>() {
            @Override
            public void onResponse(Call<UniversalResponse> call, Response<UniversalResponse> response) {
                getWorkOrderEndResponse(response.body());
            }

            @Override
            public void onFailure(Call<UniversalResponse> call, Throwable t) {
                getWorkOrderEndResponse(null);

            }
        });
    }

    @Override
    public void getWorkOrderEndResponse(UniversalResponse body) {
        if (body == null) {
            iWorkOrderStartEndView.onWorkOrderEndFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iWorkOrderStartEndView.onWorkOrderEndSuccess(body);
        }
    }
}
