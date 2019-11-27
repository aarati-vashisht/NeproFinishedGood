package com.neprofinishedgood.qualitycheck.model;

public class RejectedInput {
    String StickerNo;
    String UserId;
    String Shift;
    String Quantity;
    String Reason;
    String ReasonName;
    String IsKg;
    String WorkOrderNo;

    public RejectedInput(String stickerNo, String userId, String quantity, String reason, String reasonName, String shift, String isKg, String workOrderNo) {
        StickerNo = stickerNo;
        UserId = userId;
        Quantity = quantity;
        Reason = reason;
        ReasonName = reasonName;
        Shift = shift;
        IsKg = isKg;
        WorkOrderNo = workOrderNo;
    }

    public String getStickerNo() {
        return StickerNo;
    }

    public void setStickerNo(String stickerNo) {
        StickerNo = stickerNo;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getShift() {
        return Shift;
    }

    public void setShift(String shift) {
        Shift = shift;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getReasonName() {
        return ReasonName;
    }

    public void setReasonName(String reasonName) {
        ReasonName = reasonName;
    }

    public String getIsKg() {
        return IsKg;
    }

    public void setIsKg(String isKg) {
        IsKg = isKg;
    }

    public String getWorkOrderNo() {
        return WorkOrderNo;
    }

    public void setWorkOrderNo(String workOrderNo) {
        WorkOrderNo = workOrderNo;
    }
}
