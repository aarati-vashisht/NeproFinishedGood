package com.neprofinishedgood.login.model;


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("UserSiteInfo")
    @Expose
    private List<UserSiteInfo> userSiteInfo = null;
    @SerializedName("UserLoginResponse")
    @Expose
    private List<UserLoginResponse> userLoginResponse = null;

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

    public List<UserLoginResponse> getUserLoginResponse() {
        return userLoginResponse;
    }

    public void setUserLoginResponse(List<UserLoginResponse> userLoginResponse) {
        this.userLoginResponse = userLoginResponse;
    }

    public List<UserSiteInfo> getUserSiteInfo() {
        return userSiteInfo;
    }

    public void setUserSiteInfo(List<UserSiteInfo> userSiteInfo) {
        this.userSiteInfo = userSiteInfo;
    }

}

