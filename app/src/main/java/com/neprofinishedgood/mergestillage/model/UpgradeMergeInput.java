package com.neprofinishedgood.mergestillage.model;

public class UpgradeMergeInput {
    String StickerNo;
    String UserId;
    String ActivityID;
    public UpgradeMergeInput(String stickerNo, String userId, String mergeStickers, String totalMergeQty, String ActivityID) {
        StickerNo = stickerNo;
        UserId = userId;
        MergeStickers = mergeStickers;
        TotalMergeQty = totalMergeQty;
        this.ActivityID = ActivityID;
    }

    String MergeStickers;
    String TotalMergeQty;
}
