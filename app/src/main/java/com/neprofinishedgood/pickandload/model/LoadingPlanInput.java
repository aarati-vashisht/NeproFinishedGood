package com.neprofinishedgood.pickandload.model;

public class LoadingPlanInput {
    String LPID;

    public LoadingPlanInput(String LPID, String userId) {
        this.LPID = LPID;
        UserId = userId;
    }

    String UserId;
}
