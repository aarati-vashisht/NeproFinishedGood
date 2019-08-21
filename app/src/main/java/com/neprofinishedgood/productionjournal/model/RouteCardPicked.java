package com.neprofinishedgood.productionjournal.model;

public class RouteCardPicked {

    String Shift, Date, OperationName, OperationId, Resource, ResourceType, Hours, Quantity, Priority;

    public RouteCardPicked(String shift, String date, String operationName, String operationId, String resource, String resourceType, String hours, String quantity, String priority) {
        Shift = shift;
        Date = date;
        OperationName = operationName;
        OperationId = operationId;
        Resource = resource;
        ResourceType = resourceType;
        Hours = hours;
        Quantity = quantity;
        Priority = priority;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }

    public String getShift() {
        return Shift;
    }

    public void setShift(String shift) {
        Shift = shift;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getOperationName() {
        return OperationName;
    }

    public void setOperationName(String operationName) {
        OperationName = operationName;
    }

    public String getOperationId() {
        return OperationId;
    }

    public void setOperationId(String operationId) {
        OperationId = operationId;
    }

    public String getResource() {
        return Resource;
    }

    public void setResource(String resource) {
        Resource = resource;
    }

    public String getResourceType() {
        return ResourceType;
    }

    public void setResourceType(String resourceType) {
        ResourceType = resourceType;
    }

    public String getHours() {
        return Hours;
    }

    public void setHours(String hours) {
        Hours = hours;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }
}
