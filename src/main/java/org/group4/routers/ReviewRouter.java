package org.group4.routers;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.group4.Reservation;
import org.group4.ReservationSystem;
import org.group4.exceptions.OrderFoodException;
import org.group4.exceptions.ReservationException;
import org.group4.requests.CreateOrderRequest;
import org.group4.requests.CreateReviewRequest;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class ReviewRouter {

    public ReviewRouter(Javalin app) {
//        app.get("/order", this::getRestaurants);
        app.get("/review", this::getOrder);
        app.post("/review/create", this::handleOrder);
    }

    void getOrder(@NotNull Context context) {
        context.render("review.peb");
    }


    void handleOrder(@NotNull Context context) {
        try {
            CreateReviewRequest request = new CreateReviewRequest(context);
            context.redirect("/review");
        } catch (IllegalArgumentException e) {
            context.status(400);
            context.result(e.getMessage());
        }
    }
}