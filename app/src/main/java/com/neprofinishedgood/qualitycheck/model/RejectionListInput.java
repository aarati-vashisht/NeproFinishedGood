package com.neprofinishedgood.qualitycheck.model;

import java.util.ArrayList;

public class RejectionListInput {

    ArrayList<RejectedInput> RejectionList = new ArrayList<>();
    String IsKg;

    public RejectionListInput(ArrayList<RejectedInput> rejectionList, String isKg) {
        this.RejectionList = rejectionList;
        IsKg = isKg;
    }
}
