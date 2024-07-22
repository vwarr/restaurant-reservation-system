package org.group4.requests;

import io.javalin.http.Context;
import org.group4.ReservationSystem;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateOrderRequest(String customerId, String restaurantId, LocalDate reservationDate, LocalTime reservationTime, String menuItemName, int quantity) {

    public CreateOrderRequest {
        if (customerId.isBlank()) {
            throw new IllegalArgumentException("ERROR: Customer ID cannot be blank");
        }
        if (restaurantId.isBlank()) {
            throw new IllegalArgumentException("ERROR: Restaurant ID cannot be blank");
        }
        if (menuItemName.isBlank()) {
            throw new IllegalArgumentException("ERROR: Menu item name cannot be blank");
        }
        if (!ReservationSystem.getInstance().doesCustomerExist(customerId)) {
            throw new IllegalArgumentException("ERROR: Customer does not exist");
        }

        if (!ReservationSystem.getInstance().doesRestaurantExist(restaurantId)) {
            throw new IllegalArgumentException("ERROR: Restaurant does not exist");
        }

        if(!ReservationSystem.getInstance().isMenuItemAbsent(menuItemName)) {
            throw new IllegalArgumentException("ERROR: Menu item does not exist");
        }

        if(ReservationSystem.getInstance().getRestaurant(restaurantId).getRestaurantMenuItems().get(menuItemName) == null) {
            throw new IllegalArgumentException("ERROR: Menu item does not exist in restaurant");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("ERROR: Quantity must be greater than 0");
        }
        int totalFunds = ReservationSystem.getInstance().getCustomer(customerId).getCredits() +
                ReservationSystem.getInstance().getCustomer(customerId).getFunds();
        int cost = ReservationSystem.getInstance().getRestaurant(restaurantId).getRestaurantMenuItems().get(ReservationSystem.getInstance().getMenuItem(menuItemName)).getPrice() * quantity;
    }

    public CreateOrderRequest(Context context) {
        this(
                context.formParamAsClass("customerId", String.class).get(),
                context.formParamAsClass("restaurantId", String.class).get(),
                LocalDate.parse(context.formParam("reservationDate")),
                LocalTime.parse(context.formParam("reservationTime")),
                context.formParamAsClass("menuItemName", String.class).get(),
                context.formParamAsClass("quantity", Integer.class).get()
        );

    }

    public CreateOrderRequest(String[] tokens) {
        this(
                tokens[1],
                tokens[2],
                LocalDate.parse(tokens[3]),
                LocalTime.parse(tokens[4]),
                tokens[5],
                Integer.parseInt(tokens[6])
        );
    }
}