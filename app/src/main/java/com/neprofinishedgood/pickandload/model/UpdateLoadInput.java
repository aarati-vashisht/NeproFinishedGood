package com.neprofinishedgood.pickandload.model;

public class UpdateLoadInput {
    String StickerNo;
    String UserId;
    String LoadingId;
    String Iscompleted;
    String Reason;
    String ItemId;
    String LoadQuantity;

    public UpdateLoadInput(String stickerNo, String userId, String loadingId, String reason, String itemId, String loadQuantity, String iscompleted) {
        StickerNo = stickerNo;
        UserId = userId;
        LoadingId = loadingId;
        Reason = reason;
        ItemId = itemId;
        LoadQuantity = loadQuantity;
        Iscompleted = iscompleted;
    }
}
