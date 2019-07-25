package com.neprofinishedgood.pickandload.model;

public class UpdateLoadInput {
    String StickerNo;
    String UserId;
    String LoadingId;

    public UpdateLoadInput(String stickerNo, String userId, String loadingId, String reason, String itemId, String loadQuantity) {
        StickerNo = stickerNo;
        UserId = userId;
        LoadingId = loadingId;
        Reason = reason;
        ItemId = itemId;
        LoadQuantity = loadQuantity;
    }

    String Reason;
    String ItemId;
    String LoadQuantity;

}
