package com.neprofinishedgood.plannedandunplannedmove.presenter;

import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IMovePresenter implements IMoveInterface {
    IMoveView iMoveView;

    public IMovePresenter(IMoveView iLoginView) {
        this.iMoveView = iLoginView;
    }


    @Override
    public void getMoveResponse(MoveResponse body) {
        if (body == null) {
            iMoveView.onFailure();
        } else {
            iMoveView.onSuccess(body);
        }
    }

    @Override
    public void callMoveService(MoveInput moveInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<MoveResponse> call = apiInterface.stillageMoving(moveInput);
        call.enqueue(new Callback<MoveResponse>() {
            @Override
            public void onResponse(Call<MoveResponse> call, Response<MoveResponse> response) {
                getMoveResponse(response.body());
            }

            @Override
            public void onFailure(Call<MoveResponse> call, Throwable t) {
                getMoveResponse(null);

            }
        });
    }
}
