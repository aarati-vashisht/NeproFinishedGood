package com.neprofinishedgood.productionjournal.presenter;

import android.app.Activity;

import com.neprofinishedgood.R;
import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.productionjournal.model.ProductionJournalPickinngDataInput;
import com.neprofinishedgood.productionjournal.model.ProductionJournalRouteDataInput;
import com.neprofinishedgood.productionjournal.model.WorkOrderInput;
import com.neprofinishedgood.productionjournal.model.WorkOrderResponse;

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
    public void callSubmitProductionJournalPickingService(ProductionJournalPickinngDataInput productionJournalPickinngDataInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<UniversalResponse> call = apiInterface.submitPickingJournalData(productionJournalPickinngDataInput);
        call.enqueue(new Callback<UniversalResponse>() {
            @Override
            public void onResponse(Call<UniversalResponse> call, Response<UniversalResponse> response) {
                getSubmitPickingResponse(response.body());
            }

            @Override
            public void onFailure(Call<UniversalResponse> call, Throwable t) {
                getSubmitPickingResponse(null);

            }
        });
    }

    @Override
    public void getSubmitPickingResponse(UniversalResponse body) {
        if (body == null) {
            iProductionJournalView.onSubmitPickingProcessFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iProductionJournalView.onSubmitPickingProcessSuccess(body);
        }
    }

    @Override
    public void callSubmitProductionJournalRouteService(ProductionJournalRouteDataInput productionJournalRouteDataInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<UniversalResponse> call = apiInterface.submitRouteJournalData(productionJournalRouteDataInput);
        call.enqueue(new Callback<UniversalResponse>() {
            @Override
            public void onResponse(Call<UniversalResponse> call, Response<UniversalResponse> response) {
                getSubmitRouteResponse(response.body());
            }

            @Override
            public void onFailure(Call<UniversalResponse> call, Throwable t) {
                getSubmitRouteResponse(null);

            }
        });
    }

    @Override
    public void getSubmitRouteResponse(UniversalResponse body) {
        if (body == null) {
            iProductionJournalView.onSubmitRouteProcessFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iProductionJournalView.onSubmitRouteProcessSuccess(body);
        }
    }


}
