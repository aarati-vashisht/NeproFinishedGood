package com.neprofinishedgood.plannedandunplannedmove.presenter;

import com.neprofinishedgood.api.Api;
import com.neprofinishedgood.api.ApiInterface;
import com.neprofinishedgood.base.model.UniversalResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.AllAssignedDataInput;
import com.neprofinishedgood.plannedandunplannedmove.model.AssignedStillages;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveInput;
import com.neprofinishedgood.plannedandunplannedmove.model.MoveResponse;
import com.neprofinishedgood.plannedandunplannedmove.model.UpdateMoveLocationInput;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IMovePresenter implements IMoveInterface {
    IMoveView iMoveView;

    public IMovePresenter(IMoveView iLoginView) {
        this.iMoveView = iLoginView;
    }




    @Override
    public void callMoveServcie(UpdateMoveLocationInput updateMoveLocationInput) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<UniversalResponse> call = apiInterface.updateMovingStatus(updateMoveLocationInput);
        call.enqueue(new Callback<UniversalResponse>() {
            @Override
            public void onResponse(Call<UniversalResponse> call, Response<UniversalResponse> response) {
                getUpdateMoveResponse(response.body());
            }

            @Override
            public void onFailure(Call<UniversalResponse> call, Throwable t) {
                getUpdateMoveResponse(null);

            }
        });
    }

    @Override
    public void getUpdateMoveResponse(UniversalResponse body) {
        if (body == null) {
            iMoveView.onUpdateMoveFailure();
        } else {
            iMoveView.onUpdateMoveSuccess(body);
        }
    }


}
