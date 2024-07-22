package org.group4.requests;

import io.javalin.http.Context;
import org.group4.ReservationSystem;

public record CreateMenuItemRequest(String itemName, String ingredients) {

    public CreateMenuItemRequest {
        if (itemName.isBlank()) {
            throw new IllegalArgumentException("ERROR: itemName is blank");
        }
        if (ingredients.isBlank()) {
           throw new IllegalArgumentException("ERROR: ingredients is blank");
        }
        if(ReservationSystem.getInstance().isMenuItemAbsent(itemName)) {
            throw new IllegalArgumentException("ERROR: itemName was not found");
        }
    }

    public CreateMenuItemRequest(Context context) {
        this(
                context.formParamAsClass("itemName", String.class).get(),
                context.formParamAsClass("ingredients", String.class).get()
        );
    }

    public CreateMenuItemRequest(String[] tokens) {
        this(
            tokens[1],
            tokens[2]
        );
    }
}
