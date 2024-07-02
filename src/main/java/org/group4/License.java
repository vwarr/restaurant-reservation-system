package org.group4;

import java.util.UUID;

public record License(String ownerId, String restaurantId, String name) {

    @Override
    public String toString() {
        return "License{" +
                "name=" + name +
                "ownerId=" + ownerId +
                ", restaurantId=" + restaurantId +
                '}';
    }
}
