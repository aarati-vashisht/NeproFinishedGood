package com.neprofinishedgood.pickandload.model;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoadingPlanDetails {

@SerializedName("LoadingPlanList1")
@Expose
private List<LoadingPlanList> loadingPlanList1 = null;
@SerializedName("TruckID")
@Expose
private String truckID;
@SerializedName("DriverName")
@Expose
private String driverName;
@SerializedName("GateNo")
@Expose
private Integer gateNo;
@SerializedName("LoadingPlanNo")
@Expose
private String loadingPlanNo;
@SerializedName("Status")
@Expose
private String status;
@SerializedName("Message")
@Expose
private String message;

public List<LoadingPlanList> getLoadingPlanList1() {
return loadingPlanList1;
}

public void setLoadingPlanList1(List<LoadingPlanList> loadingPlanList1) {
this.loadingPlanList1 = loadingPlanList1;
}

public String getTruckID() {
return truckID;
}

public void setTruckID(String truckID) {
this.truckID = truckID;
}

public String getDriverName() {
return driverName;
}

public void setDriverName(String driverName) {
this.driverName = driverName;
}

public Integer getGateNo() {
return gateNo;
}

public void setGateNo(Integer gateNo) {
this.gateNo = gateNo;
}

public String getLoadingPlanNo() {
return loadingPlanNo;
}

public void setLoadingPlanNo(String loadingPlanNo) {
this.loadingPlanNo = loadingPlanNo;
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


