package org.group4;

public record License(String ownerId, String restaurantId, String name) {

    @Override
    public String toString() {
        return "License{" +
                "name=" + name +
                "ownerId=" + ownerId +
                ", restaurantId=" + restaurantId +
                '}';
    }
    public String getownerID() {
        return ownerId;
    }
}
