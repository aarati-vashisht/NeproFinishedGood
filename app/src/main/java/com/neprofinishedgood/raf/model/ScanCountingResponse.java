package com.neprofinishedgood.raf.model;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScanCountingResponse {

@SerializedName("StillageList")
@Expose
private List<StillageList> stillageList = null;
@SerializedName("Status")
@Expose
private String status;
@SerializedName("Message")
@Expose
private String message;

public List<StillageList> getStillageList() {
return stillageList;
}

public void setStillageList(List<StillageList> stillageList) {
this.stillageList = stillageList;
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


