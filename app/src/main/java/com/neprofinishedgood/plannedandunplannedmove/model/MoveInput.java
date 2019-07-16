package com.neprofinishedgood.plannedandunplannedmove.model;

public class MoveInput {
    String StickerNo;

    public MoveInput(String stickerNo, String userId) {
        StickerNo = stickerNo;
        UserId = userId;
    }

    String UserId;

}
