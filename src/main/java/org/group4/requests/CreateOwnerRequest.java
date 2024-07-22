package org.group4.requests;

import io.javalin.http.Context;
import org.group4.Address;
import org.group4.InputUtil;
import org.group4.ReservationSystem;

import java.time.LocalDate;

public record CreateOwnerRequest(String ownerId, String firstName, String lastName, Address address,
                                      String restaurantGroup, String startDate) {
    public CreateOwnerRequest {
        if (ownerId.isBlank()) {
            throw new IllegalArgumentException("ERROR: Owner ID cannot be blank");
        }

        if (firstName.isBlank()) {
            throw new IllegalArgumentException("ERROR: First name cannot be blank");
        }

        if (lastName.isBlank()) {
            throw new IllegalArgumentException("ERROR: Last name cannot be blank");
        }

        if (restaurantGroup.isBlank()) {
            throw new IllegalArgumentException("ERROR: Restaurant group cannot be blank");
        }

        if (startDate.isBlank()) {
            throw new IllegalArgumentException("ERROR: Date cannot be blank");
        }
    }

    public CreateOwnerRequest(Context context) {
        this(
                context.formParamAsClass("id", String.class).get(),
                context.formParamAsClass("first name", String.class).get(),
                context.formParamAsClass("last name", String.class).get(),
                InputUtil.formParamAddress(context),
                context.formParamAsClass("restaurantGroup", String.class).get(),
                context.formParamAsClass("startDate", String.class).get()
        );
    }

    public CreateOwnerRequest(String[] tokens) {
        this(
                tokens[1],
                tokens[2],
                tokens[3],
                new Address(tokens[4], tokens[5], tokens[6]),
                tokens[7],
                tokens[8]
        );
    }
}
