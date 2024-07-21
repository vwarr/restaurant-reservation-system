package org.group4.routers;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.group4.Customer;
import org.group4.MenuItem;
import org.group4.Reservation;
import org.group4.ReservationSystem;
import org.group4.exceptions.MenuItemException;
import org.group4.exceptions.OrderFoodException;
import org.group4.exceptions.ReservationException;
import org.group4.requests.CreateAddMenuItemRequest;
import org.group4.requests.CreateCustomerRequest;
import org.group4.requests.CreateMenuItemRequest;
import org.group4.requests.CreateOrderRequest;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class MenuRouter {

    public MenuRouter(Javalin app) {
        app.get("/menu", this::getMenuItems);
        app.post("/menu/create", this::createMenuItem);
        app.post("/menu/add", this::addMenuItem);
    }

    void getMenuItems(@NotNull Context context) {
        context.render("menu.peb");
    }


    void createMenuItem(@NotNull Context context) {
        try {
            //TODO Make sure that item exists
            CreateMenuItemRequest request = new CreateMenuItemRequest(context);
            MenuItem menuItem = ReservationSystem.getInstance().createMenuItem(request);
            context.redirect("/menu");
        } catch (IllegalArgumentException e) {
            context.status(400);
            context.result(e.getMessage());
        }
    }

    void addMenuItem(@NotNull Context context) {
        try {
            CreateAddMenuItemRequest request = new CreateAddMenuItemRequest(context);
            ReservationSystem.getInstance().addMenuItemRestaurant(request);
            context.redirect("/menu");
        } catch (IllegalArgumentException e) {
            context.status(400);
            context.result(e.getMessage());
        } catch (MenuItemException.AlreadyAdded e) {
            context.result("Menu item already added");
        }
    }
}