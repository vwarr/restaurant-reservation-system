package org.group4.routers;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import org.group4.Address;
import org.group4.ReservationSystemData;
import org.group4.Restaurant;
import org.group4.serverUtil.FormUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;


public class RestaurantRouter {

    public RestaurantRouter(Javalin app) {
        app.get("/restaurants", this::getRestaurants);
        app.post("/restaurants/create", this::createRestaurant);
    }

    void getRestaurants(@NotNull Context context) {
        context.render("restaurants.peb",
                Collections.singletonMap("restaurants", ReservationSystemData.getInstance().getRestaurants()));
    }

    void createRestaurant(@NotNull Context context) {
        String id = context.formParamAsClass("id", String.class).get();
        String name = context.formParamAsClass("name", String.class).get();
        Integer capacity = context.formParamAsClass("capacity", Integer.class).get();
        Address address = FormUtil.formParamAddress(context);

        Restaurant restaurant = new Restaurant.Builder(id)
                .name(name)
                .address(address)
                .seatingCapacity(capacity)
                .build();

        ReservationSystemData.getInstance().addRestaurant(restaurant);
        context.redirect("/restaurants");
    }
}
