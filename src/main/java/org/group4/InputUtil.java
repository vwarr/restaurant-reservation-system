package org.group4;

import io.javalin.http.Context;

public class InputUtil {

    public static Address formParamAddress(Context context) {
        String street = context.formParamAsClass("street", String.class).get();
        String state = context.formParamAsClass("state", String.class).get();
        String zip = context.formParamAsClass("zip", String.class).get();

        return new Address(street, state, zip);
    }

    public static String nullify(String input) {
        return input.equals("null") ? null : input;
    }
}
