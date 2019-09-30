package com.neprofinishedgood.pickandload.model;

public class LoadingPlanInput {
    String LPID;
    String EndPickedReason;

    public LoadingPlanInput(String LPID, String userId, String endPickedReason) {
        this.LPID = LPID;
        UserId = userId;
        EndPickedReason = endPickedReason;
    }

    String UserId;
}
