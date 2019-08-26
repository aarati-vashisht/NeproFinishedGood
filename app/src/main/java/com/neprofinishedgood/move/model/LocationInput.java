package com.neprofinishedgood.move.model;

public class LocationInput {
    String LocationId;
    String UserId;
    String WareHouseId;

    public LocationInput(String locationId, String userId, String wareHouseId) {
        LocationId = locationId;
        UserId = userId;
        WareHouseId = wareHouseId;
    }
}
