package com.neprofinishedgood.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSiteInfo {

    @SerializedName("Site")
    @Expose
    private String site;
    @SerializedName("WareHouse")
    @Expose
    private String wareHouse;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getWareHouse() {
        return wareHouse;
    }

    public void setWareHouse(String wareHouse) {
        this.wareHouse = wareHouse;
    }

}