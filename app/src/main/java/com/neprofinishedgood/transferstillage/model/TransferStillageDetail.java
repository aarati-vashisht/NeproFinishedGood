package com.neprofinishedgood.transferstillage.model;

public class TransferStillageDetail {

    String StillageID;
    String TransID;


    public TransferStillageDetail(String stillageID, String transID) {
        StillageID = stillageID;
        TransID = transID;
    }



    public void setStillageID(String stillageID) {
        StillageID = stillageID;
    }

    public String getStillageID() {
        return StillageID;
    }

    public String getTransID() {
        return TransID;
    }

    public void setTransID(String transID) {
        TransID = transID;
    }
}
