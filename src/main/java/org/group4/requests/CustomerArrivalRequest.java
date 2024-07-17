package org.group4.requests;

import io.javalin.http.Context;
import org.group4.InputUtil;
import org.group4.ReservationSystem;

public record CustomerArrivalRequest(String customerId, String restaurantId, String reservationDate, String arrivalTime, String reservationTime) {

    public CustomerArrivalRequest {
        if (!ReservationSystem.getInstance().doesRestaurantExist(restaurantId)) {
            throw new IllegalArgumentException("ERROR: Restaurant does not exist");
        }

        if (!ReservationSystem.getInstance().doesCustomerExist(customerId)) {
            throw new IllegalArgumentException("ERROR: Customer does not exist");
        }

        reservationTime = reservationTime.isBlank() ? null : arrivalTime;
    }

    public CustomerArrivalRequest(String[] tokens) {
        this(
                tokens[1],
                tokens[2],
                tokens[3],
                tokens[4],
                InputUtil.nullify(tokens[5]) // Because for walk ins, reservationTime can be null
        );
    }

    public CustomerArrivalRequest(Context context) {
        this(
                context.formParamAsClass("customerId", String.class).get(),
                context.formParamAsClass("restaurantId", String.class).get(),
                context.formParamAsClass("reservationDate", String.class).get(),
                context.formParamAsClass("arrivalTime", String.class).get(),
                context.formParamAsClass("reservationTime", String.class).get()
        );
    }
}
