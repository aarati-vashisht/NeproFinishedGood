package com.neprofinishedgood.productionjournal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoutingListDatum {

    @SerializedName("OperationName")
    @Expose
    private String operationName;

    @SerializedName("Resource")
    @Expose
    private String resource;

    @SerializedName("ResourceType")
    @Expose
    private String resourceType;

    @SerializedName("OperationId")
    @Expose
    private String operationId;

    @SerializedName("Priority")
    @Expose
    private String priority;

    public RoutingListDatum(String operationName, String resource, String resourceType, String operationId, String priority) {
        this.operationName = operationName;
        this.resource = resource;
        this.resourceType = resourceType;
        this.operationId = operationId;
        this.priority = priority;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }
}