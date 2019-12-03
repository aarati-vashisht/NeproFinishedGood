package com.neprofinishedgood.move.transferpresenter;

import android.app.Activity;

import com.neprofinishedgood.R;
import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.move.model.TransDoneInputModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IMoveTransferPresenter implements IMoveTransferInterface {
    IMoveTransferView iMoveTransferView;
    Activity activity;

    public  IMoveTransferPresenter(IMoveTransferView iMoveTransferView, Activity activity) {
        this.iMoveTransferView = iMoveTransferView;
        this.activity = activity;
    }


    @Override
    public void callPostAssignTransfer(TransDoneInputModel transDoneInputModel) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<UniversalResponse> call = apiInterface.callPostAssignTransfer(transDoneInputModel);
        call.enqueue(new Callback<UniversalResponse>() {
            @Override
            public void onResponse(Call<UniversalResponse> call, Response<UniversalResponse> response) {
                getPostAssignTransfer(response.body());
                }

            @Override
            public void onFailure(Call<UniversalResponse> call, Throwable t) {
                getPostAssignTransfer(null);
            }
        });
    }

    @Override
    public void getPostAssignTransfer(UniversalResponse body) {
        if (body == null) {
            iMoveTransferView.onGetTransDoneFailure(activity.getString(R.string.something_went_wrong_please_try_again));
        } else {
            iMoveTransferView.onGetTransDoneSuccess(body);
        }
    }
}
