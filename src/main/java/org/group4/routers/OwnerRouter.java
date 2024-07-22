package org.group4.routers;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.group4.Reservation;
import org.group4.ReservationSystem;
import org.group4.Owner;
import org.group4.requests.CreateOwnerRequest;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class OwnerRouter {

    public OwnerRouter(Javalin app) {
        app.get("/owners", this::getOwners);
        app.post("/owners/create", this::createOwners);
    }

    void getOwners(@NotNull Context context) {
//        context.render("restaurants.peb",
//                Collections.singletonMap("restaurants", ReservationSystem.getInstance().getRestaurants()));
        context.render("owners.peb", Collections.singletonMap("owners", ReservationSystem.getInstance().getOwners()));
    }

    void createOwners(@NotNull Context context) {
        try {
            CreateOwnerRequest request = new CreateOwnerRequest(context);
            Owner owner = ReservationSystem.getInstance().createOwner(request);
            context.redirect("/owners");
        } catch (IllegalArgumentException e) {
            context.status(400);
            context.result(e.getMessage());
        }
    }
}
