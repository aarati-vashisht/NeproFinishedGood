package com.neprofinishedgood.productionjournal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkOrderResponse {

@SerializedName("WorkOrderNo")
@Expose
private String workOrderNo;
@SerializedName("WorkOrderId")
@Expose
private String workOrderId;
@SerializedName("Status")
@Expose
private String status;
@SerializedName("Message")
@Expose
private String message;

public String getWorkOrderNo() {
return workOrderNo;
}

public void setWorkOrderNo(String workOrderNo) {
this.workOrderNo = workOrderNo;
}

public String getWorkOrderId() {
return workOrderId;
}

public void setWorkOrderId(String workOrderId) {
this.workOrderId = workOrderId;
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