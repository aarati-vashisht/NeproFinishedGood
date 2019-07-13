package com.neprofinishedgood.raf.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StillageResponse {

@SerializedName("stillageDatum")
@Expose
private List<StillageDatum> formData = null;

public List<StillageDatum> getFormData() {
return formData;
}

public void setFormData(List<StillageDatum> formData) {
this.formData = formData;
}

}
