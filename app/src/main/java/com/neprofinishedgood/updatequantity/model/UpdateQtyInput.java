package com.neprofinishedgood.updatequantity.model;

public class UpdateQtyInput {
    String StickerNo, Quantity, Reason, UserId, Variance;

    public UpdateQtyInput(String stickerNo, String quantity, String reason, String userId, String variance) {
        StickerNo = stickerNo;
        Quantity = quantity;
        Reason = reason;
        UserId = userId;
        Variance = variance;
    }
}
