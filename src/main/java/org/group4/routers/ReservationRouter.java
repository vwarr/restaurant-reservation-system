package org.group4.routers;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.group4.Reservation;
import org.group4.ReservationSystem;
import org.group4.exceptions.NoSpaceException;
import org.group4.exceptions.ReservationException;
import org.group4.requests.CustomerArrivalRequest;
import org.group4.requests.ReservationRequest;
import org.jetbrains.annotations.NotNull;

public class ReservationRouter {

    public ReservationRouter(Javalin app) {
        app.get("/reservations",this::getReservation);
        app.post("/reservations/create", this::makeReservation);
        app.post("/reservations/arrive", this::postCustomerArrival);
    }

    void getReservation(@NotNull Context context) {context.render("reservation.peb");}

    void makeReservation(@NotNull Context context) {
        try {
            ReservationRequest request = new ReservationRequest(context);
            Reservation reservation = ReservationSystem.getInstance().createReservation(request);
            context.redirect("/reservations");
        } catch (IllegalArgumentException | ReservationException.Conflict | ReservationException.FullyBooked e) {
            context.status(400);
            context.result(e.getMessage());
        }
    }

    void postCustomerArrival(@NotNull Context context) {
        try {
            CustomerArrivalRequest request = new CustomerArrivalRequest(context);
            ReservationSystem.getInstance().registerCustomerArrival(request);
            context.redirect("/reservations");

        } catch (NoSpaceException | IllegalArgumentException e) {
            context.status(400);
            context.result(e.getMessage());
        }
    }
}
