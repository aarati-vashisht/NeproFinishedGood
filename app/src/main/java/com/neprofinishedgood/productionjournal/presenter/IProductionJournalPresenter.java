package com.neprofinishedgood.productionjournal.presenter;

import android.app.Activity;

import com.neprofinishedgood.R;
import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.productionjournal.model.ProductionJournalDataInput;
import com.neprofinishedgood.productionjournal.model.WorkOrderInput;
import com.neprofinishedgood.productionjournal.model.WorkOrderResponse;
import com.neprofinishedgood.productionjournal.model.WorkOrderSubmitInput;

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
        Call<WorkOrderResponse> call = apiInterface.getProductionJournalData(workOrderInput);
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

    @Override
    public void callSubmitProductionJournalService(ProductionJournalDataInput productionJournalDataInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<UniversalResponse> call = apiInterface.submitProductionJournalData(productionJournalDataInput);
        call.enqueue(new Callback<UniversalResponse>() {
            @Override
            public void onResponse(Call<UniversalResponse> call, Response<UniversalResponse> response) {
                getSubmitWorkOrderResponse(response.body());
            }

            @Override
            public void onFailure(Call<UniversalResponse> call, Throwable t) {
                getSubmitWorkOrderResponse(null);

            }
        });
    }

    @Override
    public void getSubmitWorkOrderResponse(UniversalResponse body) {
        if (body == null) {
            iProductionJournalView.onSubmitProcessFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iProductionJournalView.onSubmitProcessSuccess(body);
        }
    }
}
