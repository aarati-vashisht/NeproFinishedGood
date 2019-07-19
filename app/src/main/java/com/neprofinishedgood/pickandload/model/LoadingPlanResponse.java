package com.neprofinishedgood.pickandload.model;



import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoadingPlanResponse {

@SerializedName("ScanLoadingPlanList")
@Expose
private List<ScanLoadingPlanList> scanLoadingPlanList = null;
@SerializedName("Status")
@Expose
private String status;
@SerializedName("Message")
@Expose
private String message;

public List<ScanLoadingPlanList> getScanLoadingPlanList() {
return scanLoadingPlanList;
}

public void setScanLoadingPlanList(List<ScanLoadingPlanList> scanLoadingPlanList) {
this.scanLoadingPlanList = scanLoadingPlanList;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

}


