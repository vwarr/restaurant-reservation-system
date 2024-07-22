package org.group4.requests;

import io.javalin.http.Context;
import org.group4.Address;
import org.group4.InputUtil;
import org.group4.ReservationSystem;

import java.time.LocalDate;

public record CreateOwnerRequest(String ownerId, String firstName, String lastName, Address address,
                                      String restaurantGroup, LocalDate startDate) {
    public CreateOwnerRequest {
        if (ownerId.isBlank()) {
            throw new IllegalArgumentException("ERROR: Owner ID cannot be blank");
        }

        if (firstName.isBlank()) {
            throw new IllegalArgumentException("ERROR: First name cannot be blank");
        }
        if (address.streetName().isBlank()) {
            throw new IllegalArgumentException("ERROR: Street name cannot be blank");
        }
        if (address.zipCode().isBlank()) {
            throw new IllegalArgumentException("ERROR: Zip code cannot be blank");
        }
        if (address.stateAbbreviation().isBlank()) {
            throw new IllegalArgumentException("ERROR: State cannot be blank");
        }
        if (restaurantGroup.isBlank()) {
            throw new IllegalArgumentException("ERROR: Restaurant group cannot be blank");
        }

        if (startDate == null) {
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
//                context.formParamAsClass("startDate", String.class).get().
                LocalDate.parse(context.formParam("startDate"))
        );
    }

    public CreateOwnerRequest(String[] tokens) {
        this(
                tokens[1],
                tokens[2],
                tokens[3],
                new Address(tokens[4], tokens[5], tokens[6]),
                tokens[7],
                LocalDate.parse(tokens[8])
        );
    }
}
