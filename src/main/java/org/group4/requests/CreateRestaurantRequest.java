package org.group4.requests;

import io.javalin.http.Context;
import org.group4.Address;
import org.group4.InputUtil;
import org.group4.ReservationSystem;

public record CreateRestaurantRequest(String restaurantId, String restaurantName, Address address,
                                      int seatingCapacity, String ownerId, String licenseId) {
    public CreateRestaurantRequest {
        if (restaurantId.isBlank()) {
            throw new IllegalArgumentException("ERROR: Restaurant ID cannot be blank");
        }

        if (restaurantName.isBlank()) {
            throw new IllegalArgumentException("ERROR: Restaurant name cannot be blank");
        }

        if (licenseId.isBlank()) {
            throw new IllegalArgumentException("ERROR: License ID cannot be blank");
        }

        if (!ReservationSystem.getInstance().doesOwnerExist(ownerId)) {
            throw new IllegalArgumentException("ERROR: Owner does not exist");
        }

        if (ReservationSystem.getInstance().doesRestaurantExist(restaurantId)) {
            throw new IllegalArgumentException("ERROR: Restaurant already exists");
        }

        if (seatingCapacity <= 0) {
            throw new IllegalArgumentException("ERROR: Seating capacity must be greater than 0");
        }

    }

    public CreateRestaurantRequest(Context context) {
        this(
                context.formParamAsClass("id", String.class).get(),
                context.formParamAsClass("name", String.class).get(),
                InputUtil.formParamAddress(context),
                context.formParamAsClass("capacity", Integer.class).get(),
                context.formParamAsClass("ownerId", String.class).get(),
                context.formParamAsClass("licenseId", String.class).get()
        );
    }

    public CreateRestaurantRequest(String[] tokens) {
        this(
                tokens[1],
                tokens[2],
                new Address(tokens[3], tokens[4], tokens[5]),
                Integer.parseInt(tokens[6]),
                tokens[7],
                tokens[8]
        );
    }
}
