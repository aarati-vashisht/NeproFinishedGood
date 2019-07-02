package com.neprofinishedgood.base.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UniversalSpinnerModel {

    @SerializedName("universalSpinners")
    @Expose
    private List<UniversalSpinner> universalSpinners = null;

    public List<UniversalSpinner> getUniversalSpinners() {
        return universalSpinners;
    }

    public void setUniversalSpinners(List<UniversalSpinner> universalSpinners) {
        this.universalSpinners = universalSpinners;
    }

}

