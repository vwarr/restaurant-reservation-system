package org.group4.routers;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.group4.*;
import org.group4.exceptions.ReservationException;
import org.group4.requests.CreateRestaurantRequest;
import org.group4.requests.ReservationRequest;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;


public class RestaurantRouter {

    public RestaurantRouter(Javalin app) {
        app.get("/restaurants", this::getRestaurants);
        app.post("/restaurants/create", this::createRestaurant);
    }

    void getRestaurants(@NotNull Context context) {
        context.render("restaurants.peb",
                Collections.singletonMap("restaurants", ReservationSystem.getInstance().getRestaurants()));
    }

    void createRestaurant(@NotNull Context context) {
        try {
            CreateRestaurantRequest request = new CreateRestaurantRequest(context);
            Restaurant restaurant = ReservationSystem.getInstance().createRestaurant(request);
            context.redirect("/restaurants");
        } catch (IllegalArgumentException e) {
            context.status(400);
            context.result(e.getMessage());
        }
    }


}
