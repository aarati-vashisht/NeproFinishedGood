package com.neprofinishedgood.qualitycheck.model;

public class RejectedCompleteInput {
    String StickerNo;
    String UserId;
    String Shift;
    String Reason;

    public RejectedCompleteInput(String stickerNo, String userId, String reason, String shift) {
        StickerNo = stickerNo;
        UserId = userId;
        Reason = reason;
        Shift = shift;
    }

}
