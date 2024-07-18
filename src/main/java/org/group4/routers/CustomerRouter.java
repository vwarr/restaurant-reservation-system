package org.group4.routers;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.group4.Customer;
import org.group4.Reservation;
import org.group4.ReservationSystem;
import org.group4.exceptions.OrderFoodException;
import org.group4.exceptions.ReservationException;
import org.group4.requests.CreateCustomerRequest;
import org.group4.requests.CreateOrderRequest;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class CustomerRouter {

    public CustomerRouter(Javalin app) {
        app.get("/customers", this::getCustomers);
        app.post("/customers/create", this::createCustomer);
    }

    void getCustomers(@NotNull Context context) {
        context.render("customers.peb", Collections.singletonMap("customers", ReservationSystem.getInstance().getCustomers()));
    }


    void createCustomer(@NotNull Context context) {
        try {
            CreateCustomerRequest request = new CreateCustomerRequest(context);
            Customer customer = ReservationSystem.getInstance().createCustomer(request);
            context.redirect("/customers");
        } catch (IllegalArgumentException e) {
            context.status(400);
            context.result(e.getMessage());
        }
    }
}