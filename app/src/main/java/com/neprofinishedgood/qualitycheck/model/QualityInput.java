package com.neprofinishedgood.qualitycheck.model;

public class QualityInput {
    String StickerNo;
    String IsHold;
    String UserId;

    public QualityInput(String stickerNo, String userId, String isHold) {
        StickerNo = stickerNo;
        UserId = userId;
        IsHold = isHold;
    }
}