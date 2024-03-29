package com.neprofinishedgood.base.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UniversalResponse {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("TransferId")
    @Expose
    private String transferId;

    @SerializedName("StillageNotSH")
    @Expose
    private String stillageNotSH;

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

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    public String getStillageNotSH() {
        return stillageNotSH;
    }

    public void setStillageNotSH(String stillageNotSH) {
        this.stillageNotSH = stillageNotSH;
    }
}
