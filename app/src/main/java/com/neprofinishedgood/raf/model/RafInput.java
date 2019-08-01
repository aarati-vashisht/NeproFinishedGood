package com.neprofinishedgood.raf.model;

public class RafInput {
    String StickerNo;
    String UserId;
    String Shift;
    String AutoRoute;
    String AutoPick;

    public RafInput(String stickerNo, String userId, String shift, String quantity, String autoPick, String autoRoute) {
        StickerNo = stickerNo;
        UserId = userId;
        Shift = shift;
        Quantity = quantity;
        AutoPick = autoPick;
        AutoRoute = autoRoute;
    }

    String Quantity;
}
