package com.neprofinishedgood.qualitycheck.model;

public class RejectedInput {
    String StickerNo;
    String UserId;
    String Shift;
    String Quantity;
    String Reason;

    public RejectedInput(String stickerNo, String userId, String quantity, String reason, String shift) {
        StickerNo = stickerNo;
        UserId = userId;
        Quantity = quantity;
        Reason = reason;
        Shift = shift;
    }

}
