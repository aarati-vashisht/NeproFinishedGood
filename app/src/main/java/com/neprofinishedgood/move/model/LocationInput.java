package com.neprofinishedgood.move.model;

public class LocationInput {
    String LocationId;

    public LocationInput(String locationId, String userId) {
        LocationId = locationId;
        UserId = userId;
    }

    String UserId;
}
