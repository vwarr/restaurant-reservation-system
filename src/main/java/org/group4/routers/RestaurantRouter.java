package org.group4.routers;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import org.group4.ReservationSystemData;
import org.group4.Restaurant;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;


public class RestaurantRouter {

    public RestaurantRouter(Javalin app) {
        app.get("/restaurants", this::getRestaurants);
        app.get("/restaurants/create", this::getCreateRestaurantView);
        app.post("/restaurants/create", this::createRestaurant);
    }

    void getRestaurants(@NotNull Context context) {
        context.render("viewRestaurants.peb",
                Collections.singletonMap("restaurants", ReservationSystemData.getInstance().getRestaurants()));
    }

    void getCreateRestaurantView(@NotNull Context context) {
        context.render("createRestaurant.peb", new HashMap<>());
    }

    void createRestaurant(@NotNull Context context) {
        String id = context.formParamAsClass("id", String.class).get();
        String name = context.formParamAsClass("name", String.class).get();
        String street = context.formParamAsClass("street", String.class).get();
        String state = context.formParamAsClass("state", String.class).get();
        String zip = context.formParamAsClass("zip", String.class).get();
        Integer capacity = context.formParamAsClass("capacity", Integer.class).get();

        Restaurant restaurant = new Restaurant.Builder(id)
                .name(name)
                .address(street, state, zip)
                .seatingCapacity(capacity)
                .build();

        ReservationSystemData.getInstance().addRestaurant(restaurant);
        context.redirect("/restaurants");
    }
}
