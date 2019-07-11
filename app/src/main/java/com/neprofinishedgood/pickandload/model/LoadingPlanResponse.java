package com.neprofinishedgood.pickandload.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoadingPlanResponse {

@SerializedName("loadingDatum")
@Expose
private List<LoadingPlanDatum> lodingData = null;

public List<LoadingPlanDatum> getlodingData() {
return lodingData;
}

public void setlodingData(List<LoadingPlanDatum> lodingData) {
this.lodingData = lodingData;
}

}
