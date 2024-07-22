package org.group4.requests;

import io.javalin.http.Context;
import org.group4.ReservationSystem;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public record CreateReviewRequest(String customerId, String restaurantId, LocalDate reservationDate, LocalTime reservationTime, int rating, List<String> tags) {

    public CreateReviewRequest {
        if (customerId.isBlank()) {
            throw new IllegalArgumentException("ERROR: Customer ID cannot be blank");
        }
        if (restaurantId.isBlank()) {
            throw new IllegalArgumentException("ERROR: Restaurant ID cannot be blank");
        }
        if (tags.isEmpty()) {
            throw new IllegalArgumentException("ERROR: Tags cannot be empty");
        }
        if (!ReservationSystem.getInstance().doesCustomerExist(customerId)) {
            throw new IllegalArgumentException("ERROR: Customer does not exist");
        }
        if (!ReservationSystem.getInstance().doesRestaurantExist(restaurantId)) {
            throw new IllegalArgumentException("ERROR: Restaurant does not exist");
        }
        if (rating < 0) {
            throw new IllegalArgumentException("ERROR: Rating cannot be negative");
        }
    }

    public CreateReviewRequest(Context context) {
        this(
                context.formParamAsClass("customerId", String.class).get(),
                context.formParamAsClass("restaurantId", String.class).get(),
                LocalDate.parse(context.formParam("reservationDate")),
                LocalTime.parse(context.formParam("reservationTime")),
                context.formParamAsClass("rating", Integer.class).get(),
                Arrays.stream(context.formParam("tags").split(","))
                        .map(String::trim)
                        .collect(Collectors.toList())
        );
    }

    public CreateReviewRequest(String[] tokens) {
        this(
                tokens[1],
                tokens[2],
                LocalDate.parse(tokens[3]),
                LocalTime.parse(tokens[4]),
                Integer.parseInt(tokens[5]),
                Arrays.stream(tokens[6].split(","))
                        .map(String::trim)
                        .collect(Collectors.toList())
        );
    }
}
