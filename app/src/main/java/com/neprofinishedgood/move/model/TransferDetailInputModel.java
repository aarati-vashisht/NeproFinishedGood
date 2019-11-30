package com.neprofinishedgood.move.model;

public class TransferDetailInputModel {

    String UserId, TATHID, ActivityId ;

    public TransferDetailInputModel(String userId, String TATHID, String activityId) {
        UserId = userId;
        this.TATHID = TATHID;
        ActivityId = activityId;
    }
}
