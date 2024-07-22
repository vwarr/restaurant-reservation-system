package org.group4.requests;

import io.javalin.http.Context;
import org.group4.Address;
import org.group4.InputUtil;
import org.group4.ReservationSystem;

public record CreateCustomerRequest(String customerId, String firstName, String lastName,
                                    String city, String state, String zipcode, int funds) {

    public CreateCustomerRequest {
        if (customerId.isBlank()) {
            throw new IllegalArgumentException("ERROR: Customer ID cannot be blank");
        }
        if (firstName.isBlank()) {
            throw new IllegalArgumentException("ERROR: First name cannot be blank");
        }
        if (lastName.isBlank()) {
            lastName = null;
        }
        if (city.isBlank()) {
            throw new IllegalArgumentException("ERROR: City cannot be blank");
        }
        if (state.isBlank()) {
            throw new IllegalArgumentException("ERROR: State cannot be blank");
        }
        if (zipcode.isBlank()) {
            throw new IllegalArgumentException("ERROR: Zipcode cannot be blank");
        }
        if (funds <= 0) {
            throw new IllegalArgumentException("ERROR: Funds must be greater than 0");
        }
    }

    public CreateCustomerRequest(Context context) {
        this(
                context.formParamAsClass("customerId", String.class).get(),
                context.formParamAsClass("firstName", String.class).get(),
                context.formParamAsClass("lastName", String.class).get(),
                context.formParamAsClass("city", String.class).get(),
                context.formParamAsClass("state", String.class).get(),
                context.formParamAsClass("zipcode", String.class).get(),
                context.formParamAsClass("funds", Integer.class).get()
        );
    }

    public CreateCustomerRequest(String[] tokens) {
        this(
                tokens[1],
                tokens[2],
                tokens[3],
                tokens[4],
                tokens[5],
                tokens[6],
                Integer.parseInt(tokens[7])
        );
    }
}
