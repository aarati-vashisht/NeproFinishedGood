package com.neprofinishedgood.putaway.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReasonModel {

    @SerializedName("reasons")
    @Expose
    private List<Reason> reasons = null;

    public List<Reason> getReasons() {
        return reasons;
    }

    public void setReasons(List<Reason> reasons) {
        this.reasons = reasons;
    }

}

