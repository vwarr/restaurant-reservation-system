package org.group4.commandLine;

import main.*;
import main.Exceptions.NoSpaceException;
import main.Exceptions.ReservationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class RestaurantController {

    public static void handleCreateRestaurant(String[] tokens) {
        // Sort Input
        String restaurantId = tokens[1];
        String restaurantName = tokens[2];
        String streetName = tokens[3];
        String state = tokens[4];
        String zipCode = tokens[5];
        int seatingCapacity = Integer.parseInt(tokens[6]);
        String ownerId = tokens[7];
        String licenseId = tokens[8];

        // Handle input
        Owner owner = ReservationSystemData.getInstance().getOwner(ownerId);
        if (owner == null) {
            // idk why but printing "ERROR: " before the statement causes the loop to terminate
            System.out.println("ERROR: owner doesn't exist");
            return;
        }
        Restaurant restaurant = new Restaurant.Builder(restaurantId)
                .name(restaurantName)
                .seatingCapacity(seatingCapacity)
                .address(streetName, state, zipCode)
                .owner(owner)
                .top10(false)
                .rating(-1)
                .licenseId(licenseId)
                .build();

        owner.addOwnedRestaurant(restaurant);
        owner.addLicense(restaurantId, licenseId);

        ReservationSystemData.getInstance().addRestaurant(restaurant);

        System.out.printf("Restaurant created: %s (%s) - %s, %s %s\n", restaurant.getId(), restaurant.getName(), streetName, state,
                zipCode);
        System.out.printf("%s (%s %s) owns %s (%s)\n", owner.getId(), owner.getFirstName(), owner.getLastName(),
                restaurant.getId(), restaurant.getName());
    }

    public static void handleMakeReservation(String[] tokens) {
        // Sort input
        String customerId = tokens[1];
        String restaurantId = tokens[2];
        int partySize = Integer.parseInt(tokens[3]);
        // Turns the date (example: 2024-05-24) and the time (ex: 19:00) to an ISO datetime: "2024-05-24T19:00:00"
        LocalDateTime dateTime = LocalDateTime.parse("%sT%s:00".formatted(tokens[4], tokens[5]));
        int credits = Integer.parseInt(tokens[6]);

        // Handle Input
        Customer customer = ReservationSystemData.getInstance().getCustomer(customerId);
        Restaurant restaurant = ReservationSystemData.getInstance().getRestaurant(restaurantId);
        try {
            Reservation res = restaurant.makeReservation(customer, partySize, dateTime, credits);
            System.out.printf("Reservation requested for %s", customer.getId());
            System.out.print("\nReservation confirmed");
            System.out.printf("\nReservation made for %s (%s %s) at %s\n", customer.getId(), customer.getFirstName(), customer.getLastName(), restaurant.getName());
        } catch(ReservationException.Conflict rce) {
            System.out.printf("Reservation requested for %s", customer.getId());
            System.out.print("\nReservation request denied, customer already has reservation with another restaurant within 2 hours of the requested time\n");
        } catch(ReservationException.FullyBooked nse) {
            System.out.printf("Reservation requested for %s %s", customer.getFirstName(), customer.getLastName());
            System.out.print("\nReservation request denied, table has another active reservation within 2 hours of the requested time\n");
        }
    }

    public static void handleCustomerArrival(String[] tokens) {
        // Sort Input
        String customerId = tokens[1];
        String restaurantId = tokens[2];
        LocalDate reservationDate = LocalDate.parse(tokens[3]);
        LocalTime arrivalTime = LocalTime.parse(tokens[4]);
        LocalTime reservationTime = tokens[5].equals("null") ? null : LocalTime.parse(tokens[5]);

        // Handle Input
        Customer customer = ReservationSystemData.getInstance().getCustomer(customerId);
        Restaurant restaurant = ReservationSystemData.getInstance().getRestaurant(restaurantId);
        try {
            restaurant.onCustomerArrival(customer, reservationDate, arrivalTime, reservationTime);
        } catch(NoSpaceException e) {
            // Although kinda scuffed to catch this message here it makes writing tests easier
            System.out.print(IOMessages.getNoSeatsMessage());
        }
        System.out.print(IOMessages.getCustomerInfoMessage(customer));
    }

    public static void handleViewAllRestaurants(String[] tokens) {
        for (Restaurant r : ReservationSystemData.getInstance().getRestaurants()) {
            System.out.printf("%s (%s)%n", r.getId(), r.getName());
        }
    }

    public static void handleViewRestaurantsOwned(String[] tokens) {
        // Sort Input
        String ownerId = tokens[1];

        // Handle input
        Owner owner = ReservationSystemData.getInstance().getOwner(ownerId);
        if (owner != null) {
            for (Restaurant r : owner.getOwnedRestaurants().values()) {
                System.out.printf("%s (%s): Unified License - %s%n", r.getId(), r.getName(), r.getLicenseId());
            }
        }
    }
}
