package com.neprofinishedgood.productionjournal.presenter;

import android.app.Activity;

import com.neprofinishedgood.R;
import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;
import com.neprofinishedgood.productionjournal.model.WorkOrderInput;
import com.neprofinishedgood.productionjournal.model.WorkOrderResponse;
import com.neprofinishedgood.raf.presenter.IRAFView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IProductionJournalPresenter implements IProductionJournalInterface{

    IProductionJournalView iProductionJournalView;
    Activity activity;

    public IProductionJournalPresenter(IProductionJournalView iProductionJournalView, Activity activity) {
        this.iProductionJournalView = iProductionJournalView;
        this.activity = activity;
    }

    @Override
    public void callScanWorkOrderService(WorkOrderInput workOrderInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<WorkOrderResponse> call = apiInterface.workOrderProcessService(workOrderInput);
        call.enqueue(new Callback<WorkOrderResponse>() {
            @Override
            public void onResponse(Call<WorkOrderResponse> call, Response<WorkOrderResponse> response) {
                getWorkOrderResponse(response.body());
            }

            @Override
            public void onFailure(Call<WorkOrderResponse> call, Throwable t) {
                getWorkOrderResponse(null);

            }
        });
    }

    @Override
    public void getWorkOrderResponse(WorkOrderResponse body) {
        if (body == null) {
            iProductionJournalView.onFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iProductionJournalView.onSuccess(body);
        }
    }
}
