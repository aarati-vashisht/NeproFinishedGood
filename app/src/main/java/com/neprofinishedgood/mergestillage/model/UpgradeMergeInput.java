package com.neprofinishedgood.mergestillage.model;

public class UpgradeMergeInput {
    String StickerNo;
    String UserId;

    public UpgradeMergeInput(String stickerNo, String userId, String mergeStickers, String totalMergeQty) {
        StickerNo = stickerNo;
        UserId = userId;
        MergeStickers = mergeStickers;
        TotalMergeQty = totalMergeQty;
    }

    String MergeStickers;
    String TotalMergeQty;
}
