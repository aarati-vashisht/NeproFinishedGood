package com.neprofinishedgood.updatequantity.model;

public class UpdateQtyInput {
    String StickerNo,Quantity,Reason,UserId;

    public UpdateQtyInput(String stickerNo, String quantity, String reason, String userId) {
        StickerNo = stickerNo;
        Quantity = quantity;
        Reason = reason;
        UserId = userId;
    }
}
