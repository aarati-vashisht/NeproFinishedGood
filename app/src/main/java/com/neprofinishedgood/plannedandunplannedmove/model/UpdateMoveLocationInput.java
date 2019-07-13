package com.neprofinishedgood.plannedandunplannedmove.model;

public class UpdateMoveLocationInput {
    String StickerNo;
    String MoveLocation;
    String UserId;
    public UpdateMoveLocationInput(String stickerNo, String moveLocation, String userId) {
        StickerNo = stickerNo;
        MoveLocation = moveLocation;
        UserId = userId;
    }
}
