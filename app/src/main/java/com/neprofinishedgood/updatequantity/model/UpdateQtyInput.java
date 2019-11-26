package com.neprofinishedgood.updatequantity.model;

public class UpdateQtyInput {
    String StickerNo, Quantity, Reason, UserId, Variance, AutoRoute, AutoPick;

    public UpdateQtyInput(String stickerNo, String quantity, String reason, String userId, String variance, String autoPick, String autoRoute) {
        StickerNo = stickerNo;
        Quantity = quantity;
        Reason = reason;
        UserId = userId;
        Variance = variance;
        AutoPick = autoPick;
        AutoRoute = autoRoute;
    }
}
