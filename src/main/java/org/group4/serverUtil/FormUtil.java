package org.group4.serverUtil;

import io.javalin.http.Context;
import org.group4.Address;

public class FormUtil {

    public static Address formParamAddress(Context context) {
        String street = context.formParamAsClass("street", String.class).get();
        String state = context.formParamAsClass("state", String.class).get();
        String zip = context.formParamAsClass("zip", String.class).get();

        return new Address(street, state, zip);
    }
}
