package org.group4.requests;

import io.javalin.http.Context;
import org.group4.ReservationSystem;

public record ReservationRequest(String customerId, String restaurantId, int partySize, String dateTime, int credits) {

    public ReservationRequest {
        if (partySize <= 1) {
            throw new IllegalArgumentException("ERROR: Party size must be greater than 0");
        }

        if (credits < 0) {
            throw new IllegalArgumentException("ERROR: Credits must be greater than or equal to 0");
        }

        if (!ReservationSystem.getInstance().doesRestaurantExist(restaurantId)) {
            throw new IllegalArgumentException("ERROR: Restaurant does not exist");
        }

        if (!ReservationSystem.getInstance().doesCustomerExist(customerId)) {
            throw new IllegalArgumentException("ERROR: Customer does not exist");
        }

    }

    public ReservationRequest(String[] tokens) {
        this(
                tokens[1],
                tokens[2],
                Integer.parseInt(tokens[3]),
                "%sT%s:00".formatted(tokens[4], tokens[5]),
                Integer.parseInt(tokens[6])
        );
    }

    public ReservationRequest(Context context) {
        this(
                context.formParamAsClass("customerId", String.class).get(),
                context.formParamAsClass("restaurantId", String.class).get(),
                context.formParamAsClass("partySize", Integer.class).get(),
                "%sT%s:00".formatted(context.formParam("date"), context.formParam("time")),
                context.formParamAsClass("credits", Integer.class).get()
        );
    }
}
