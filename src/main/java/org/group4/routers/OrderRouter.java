package org.group4.routers;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.group4.Reservation;
import org.group4.ReservationSystem;
import org.group4.exceptions.OrderFoodException;
import org.group4.exceptions.ReservationException;
import org.group4.requests.CreateOrderRequest;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class OrderRouter {

    public OrderRouter(Javalin app) {
//        app.get("/order", this::getRestaurants);
        app.get("/order", this::getOrder);
        app.post("/order/create", this::handleOrder);
    }

    void getOrder(@NotNull Context context) {
        context.render("order.peb", Collections.singletonMap("customers", ReservationSystem.getInstance().getCustomers()));
    }


    void handleOrder(@NotNull Context context) {
        try {
            CreateOrderRequest request = new CreateOrderRequest(context);
//            ReservationSystem.getInstance().orderItem(request);
            context.redirect("/order");
        } catch (IllegalArgumentException e) {
            context.status(400);
            context.result(e.getMessage());
        }
    }
}