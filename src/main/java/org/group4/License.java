package org.group4;

import java.util.UUID;

public record License(String ownerId, String restaurantId) {

    @Override
    public String toString() {
        return "License{" +
                "ownerId=" + ownerId +
                ", restaurantId=" + restaurantId +
                '}';
    }
}
