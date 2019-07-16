package com.neprofinishedgood.raf.model;

public class RafInput {
    String StickerNo;
    String UserId;
    String Shift;

    public RafInput(String stickerNo, String userId, String shift, String quantity) {
        StickerNo = stickerNo;
        UserId = userId;
        Shift = shift;
        Quantity = quantity;
    }

    String Quantity;
}
