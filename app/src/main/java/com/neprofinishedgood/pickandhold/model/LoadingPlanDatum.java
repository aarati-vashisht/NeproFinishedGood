package com.neprofinishedgood.pickandhold.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoadingPlanDatum {


    @SerializedName("customer")
    @Expose
    private String customer;
    @SerializedName("loadingPlan")
    @Expose
    private String loadingPlan;

    @SerializedName("loadingPlanId")
    @Expose
    private String loadingPlanId;

    public String getcustomer() {
        return customer;
    }

    public void setcustomer(String customer) {
        this.customer = customer;
    }


    public String getloadingPlan() {
        return loadingPlan;
    }

    public void setloadingPlan(String loadingPlan) {
        this.loadingPlan = loadingPlan;
    }


    public String getloadingPlanId() {
        return loadingPlanId;
    }

    public void setloadingPlanId(String loadingPlanId) {
        this.loadingPlanId = loadingPlanId;
    }


}


