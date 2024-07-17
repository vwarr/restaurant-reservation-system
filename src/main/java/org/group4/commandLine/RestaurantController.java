package org.group4.commandLine;

import org.group4.*;
import org.group4.exceptions.NoSpaceException;
import org.group4.exceptions.ReservationException;
import org.group4.requests.CreateRestaurantRequest;
import org.group4.requests.CustomerArrivalRequest;
import org.group4.requests.ReservationRequest;

import java.time.LocalDate;
import java.time.LocalTime;

public class RestaurantController {

    public static void handleCreateRestaurant(String[] tokens) {
        try {
            CreateRestaurantRequest request = new CreateRestaurantRequest(tokens);
            Restaurant restaurant = ReservationSystem.getInstance().createRestaurant(request);
            System.out.printf("Restaurant created: %s (%s) - %s, %s %s\n",
                    restaurant.getId(), restaurant.getName(), restaurant.getAddress().streetName(), restaurant.getAddress().stateAbbreviation(), restaurant.getAddress().zipCode());
            System.out.printf("%s (%s %s) owns %s (%s)\n",
                    restaurant.getOwner().getId(), restaurant.getOwner().getFirstName(), restaurant.getOwner().getLastName(), restaurant.getId(), restaurant.getName());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void handleMakeReservation(String[] tokens) {
        try {
            // Validate that stuff actually exists
            ReservationRequest request = new ReservationRequest(tokens);
            System.out.printf("Reservation requested for %s%n", request.customerId());

            Reservation reservation = ReservationSystem.getInstance().createReservation(request);
            Customer customer = reservation.getCustomer();
            Restaurant restaurant = ReservationSystem.getInstance().getRestaurant(request.restaurantId());
            System.out.println("Reservation confirmed");
            System.out.printf("Reservation made for %s (%s %s) at %s%n", customer.getId(), customer.getFirstName(), customer.getLastName(), restaurant.getName());
        } catch(ReservationException.Conflict | ReservationException.FullyBooked | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void handleCustomerArrival(String[] tokens) {
        // This is terrible practice, but we know customer MUST be valid if a no space exception is thrown
        Customer customer = null;
        try {
            CustomerArrivalRequest request = new CustomerArrivalRequest(tokens);
            customer = ReservationSystem.getInstance().getCustomer(request.customerId());

            ReservationSystem.getInstance().registerCustomerArrival(request);

            System.out.print(IOMessages.getCustomerInfoMessage(customer));
        } catch(NoSpaceException e) {
            // Although kinda scuffed to catch this message here it makes writing tests easier
            System.out.print(e.getMessage());
            // NEVER do this with a value that can be null
            System.out.print(IOMessages.getCustomerInfoMessage(customer));
        } catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void handleViewAllRestaurants(String[] tokens) {
        for (Restaurant r : ReservationSystem.getInstance().getRestaurants()) {
            System.out.printf("%s (%s)%n", r.getId(), r.getName());
        }
    }

    public static void handleViewRestaurantsOwned(String[] tokens) {
        // Sort Input
        String ownerId = tokens[1];

        if (!ReservationSystem.getInstance().doesOwnerExist(ownerId)) {
            System.out.println("ERROR: Owner does not exist");
            return;
        }

        // Handle input
        Owner owner = ReservationSystem.getInstance().getOwner(ownerId);
        for (Restaurant r : owner.getOwnedRestaurants().values()) {
            System.out.printf("%s (%s): Unified License - %s%n", r.getId(), r.getName(), r.getLicenseId());
        }
    }
}
