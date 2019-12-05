package com.neprofinishedgood.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserLoginResponse {

    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("UserPin")
    @Expose
    private String userPin;
    @SerializedName("IsMove")
    @Expose
    private Integer isMove;
    @SerializedName("IsReportAsFinished")
    @Expose
    private Integer isReportAsFinished;
    @SerializedName("IsQualityCheck")
    @Expose
    private Integer isQualityCheck;
    @SerializedName("IsAssignedPlannedAndUnplanned")
    @Expose
    private Integer isAssignedPlannedAndUnplanned;
    @SerializedName("IsPickAndCount")
    @Expose
    private Integer isPickAndCount;
    @SerializedName("IsMergeStillage")
    @Expose
    private Integer isMergeStillage;
    @SerializedName("IsReturnStillage")
    @Expose
    private Integer isReturnStillage;
    @SerializedName("IsRecieveReturnStillage")
    @Expose
    private Integer isRecieveReturnStillage;
    @SerializedName("IsLookUp")
    @Expose
    private Integer isLookUp;
    @SerializedName("IsUpdateQty")
    @Expose
    private Integer isUpdateQty;

    @SerializedName("IsProductionJournal")
    @Expose
    private Integer isProductionJournal;

    @SerializedName("IsWorkOrderStartEnd")
    @Expose
    private Integer isWorkOrderStartEnd;

    @SerializedName("UserName")
    @Expose
    private String userName;

    @SerializedName("EmailId")
    @Expose
    private String emailId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPin() {
        return userPin;
    }

    public void setUserPin(String userPin) {
        this.userPin = userPin;
    }

    public Integer getIsMove() {
        return isMove;
    }

    public void setIsMove(Integer isMove) {
        this.isMove = isMove;
    }

    public Integer getIsReportAsFinished() {
        return isReportAsFinished;
    }

    public void setIsReportAsFinished(Integer isReportAsFinished) {
        this.isReportAsFinished = isReportAsFinished;
    }

    public Integer getIsQualityCheck() {
        return isQualityCheck;
    }

    public void setIsQualityCheck(Integer isQualityCheck) {
        this.isQualityCheck = isQualityCheck;
    }

    public Integer getIsAssignedPlannedAndUnplanned() {
        return isAssignedPlannedAndUnplanned;
    }

    public void setIsAssignedPlannedAndUnplanned(Integer isAssignedPlannedAndUnplanned) {
        this.isAssignedPlannedAndUnplanned = isAssignedPlannedAndUnplanned;
    }

    public Integer getIsPickAndCount() {
        return isPickAndCount;
    }

    public void setIsPickAndCount(Integer isPickAndCount) {
        this.isPickAndCount = isPickAndCount;
    }

    public Integer getIsMergeStillage() {
        return isMergeStillage;
    }

    public void setIsMergeStillage(Integer isMergeStillage) {
        this.isMergeStillage = isMergeStillage;
    }

    public Integer getIsReturnStillage() {
        return isReturnStillage;
    }

    public void setIsReturnStillage(Integer isReturnStillage) {
        this.isReturnStillage = isReturnStillage;
    }

    public Integer getIsRecieveReturnStillage() {
        return isRecieveReturnStillage;
    }

    public void setIsRecieveReturnStillage(Integer isRecieveReturnStillage) {
        this.isRecieveReturnStillage = isRecieveReturnStillage;
    }

    public Integer getIsLookUp() {
        return isLookUp;
    }

    public void setIsLookUp(Integer isLookUp) {
        this.isLookUp = isLookUp;
    }

    public Integer getIsUpdateQty() {
        return isUpdateQty;
    }

    public void setIsUpdateQty(Integer isUpdateQty) {
        this.isUpdateQty = isUpdateQty;
    }

    public Integer getIsProductionJournal() {
        return isProductionJournal;
    }

    public void setIsProductionJournal(Integer isProductionJournal) {
        this.isProductionJournal = isProductionJournal;
    }

    public Integer getIsWorkOrderStartEnd() {
        return isWorkOrderStartEnd;
    }

    public void setIsWorkOrderStartEnd(Integer isWorkOrderStartEnd) {
        this.isWorkOrderStartEnd = isWorkOrderStartEnd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
