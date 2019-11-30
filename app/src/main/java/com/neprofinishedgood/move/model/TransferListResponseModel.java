package com.neprofinishedgood.move.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransferListResponseModel {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("TransferList")
    @Expose
    private List<TransferList> transferList = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TransferList> getTransferList() {
        return transferList;
    }

    public void setTransferList(List<TransferList> transferList) {
        this.transferList = transferList;
    }

}