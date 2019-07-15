package com.neprofinishedgood.qualitycheck.model;

public class RejectedInput {
    String StickerNo;
    String UserId;

    public RejectedInput(String stickerNo, String userId, String quantity, String reason) {
        StickerNo = stickerNo;
        UserId = userId;
        Quantity = quantity;
        Reason = reason;
    }

    String Quantity;
    String Reason;
}
