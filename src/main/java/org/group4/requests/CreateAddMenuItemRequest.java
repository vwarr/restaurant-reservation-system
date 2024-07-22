package org.group4.requests;

import io.javalin.http.Context;

public record CreateAddMenuItemRequest(String itemName, String restaurantID, int price) {

    public CreateAddMenuItemRequest {
        if (itemName.isBlank()) {
            throw new IllegalArgumentException("ERROR: itemName is blank");
        }
        if (restaurantID.isBlank()) {
            throw new IllegalArgumentException("ERROR: restaurantID is blank");
        }
        if (price < 0) {
            throw new IllegalArgumentException("ERROR: price is negative");
        }
    }

    public CreateAddMenuItemRequest(Context context) {
        this(
                context.formParamAsClass("itemName", String.class).get(),
                context.formParamAsClass("restaurantID", String.class).get(),
                context.formParamAsClass("price", Integer.class).get()
        );
    }

    public CreateAddMenuItemRequest(String[] tokens) {
        this(
                tokens[1],
                tokens[2],
                Integer.parseInt(tokens[3])
        );
    }
}
