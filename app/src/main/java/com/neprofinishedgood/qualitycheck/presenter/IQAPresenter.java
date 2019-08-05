package com.neprofinishedgood.qualitycheck.presenter;

import android.app.Activity;

import com.neprofinishedgood.R;
import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.ScanStillageResponse;
import com.neprofinishedgood.qualitycheck.model.RejectedInput;
import com.neprofinishedgood.qualitycheck.model.ScanLotInput;
import com.neprofinishedgood.raf.model.ScanCountingResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IQAPresenter implements IQAInterface {
    IQAView iqaView;
    Activity activity;

    public IQAPresenter(IQAView iqaView, Activity activity) {
        this.iqaView = iqaView;
        this.activity = activity;
    }


    @Override
    public void callScanStillageService(MoveInput moveInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<ScanStillageResponse> call = apiInterface.rejectedStillageDetails(moveInput);
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
            iqaView.onFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iqaView.onSuccess(body);
        }
    }

    @Override
    public void callUpdateRejectedService(RejectedInput rejectedInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<UniversalResponse> call = apiInterface.updatedRejectedStillage(rejectedInput);
        call.enqueue(new Callback<UniversalResponse>() {
            @Override
            public void onResponse(Call<UniversalResponse> call, Response<UniversalResponse> response) {
                getUpdateRejectedResponse(response.body());
            }

            @Override
            public void onFailure(Call<UniversalResponse> call, Throwable t) {
                getUpdateRejectedResponse(null);

            }
        });
    }

    @Override
    public void getUpdateRejectedResponse(UniversalResponse body) {
        if (body == null) {
            iqaView.onUpdateRejectedFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iqaView.onUpdateRejectedSuccess(body);
        }
    }

    @Override
    public void callScanLotService(ScanLotInput scanLotInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<UniversalResponse> call = apiInterface.lotScan(scanLotInput);
        call.enqueue(new Callback<UniversalResponse>() {
            @Override
            public void onResponse(Call<UniversalResponse> call, Response<UniversalResponse> response) {
                getScanLotResponse(response.body());
            }

            @Override
            public void onFailure(Call<UniversalResponse> call, Throwable t) {
                getScanLotResponse(null);

            }
        });
    }

    @Override
    public void getScanLotResponse(UniversalResponse body) {
        if (body == null) {
            iqaView.onLotScanFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iqaView.onLotScanSuccess(body);
        }
    }
}
