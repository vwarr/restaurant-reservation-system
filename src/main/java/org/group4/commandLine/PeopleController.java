package org.group4.commandLine;

import org.group4.exceptions.ReservationException;
import org.group4.Customer;
import org.group4.Owner;
import org.group4.ReservationSystem;
import org.group4.Restaurant;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class PeopleController {
    public static void handleCreateCustomer(String[] tokens) {
        // Sort Input
        String customerId = tokens[1];
        String firstName = tokens[2];
        String lastName = tokens[3].equals("null") ? null : tokens[3];
        String streetName = tokens[4];
        String state = tokens[5];
        String zipCode = tokens[6];
        int funds = Integer.parseInt(tokens[7]);

        // Handle Input
        Customer customer = new Customer.Builder(customerId)
                .firstName(firstName)
                .lastName(lastName)
                .address(streetName, state, zipCode)
                .funds(funds)
                .build();
        ReservationSystem.getInstance().addCustomer(customer);

        // Output
        System.out.printf("Customer added: %s - %s %s%n", customer.getId(), customer.getFirstName(), customer.getLastName());
    }

    public static void handleCreateOwner(String[] tokens) {
        // Sort Input
        String ownerId = tokens[1];
        String firstName = tokens[2];
        String lastName = tokens[3];
        String streetName = tokens[4];
        String state = tokens[5];
        String zipCode = tokens[6];
        LocalDate startDate = LocalDate.parse(tokens[7]);
        String restaurantGroup = tokens[8];

        // Handle Input
        if (ReservationSystem.getInstance().doesOwnerExist(ownerId)) {
            System.out.println("ERROR: duplicate unique identifier");
            return;
        }

        Owner owner = new Owner.Builder(ownerId)
                .firstName(firstName)
                .lastName(lastName)
                .address(streetName, state, zipCode)
                .startDate(startDate)
                .restaurantGroup(restaurantGroup)
                .build();
        ReservationSystem.getInstance().addOwner(owner);

        // Output
        System.out.printf("Owner added: %s - %s %s\n", owner.getId(), owner.getFirstName(), owner.getLastName());
    }

    public static void handleViewOwners(String[] tokens) {
        String restaurantGroup = tokens[1];
        //Kind of inefficient bc we check over every owner? do we need a restaurant group class?
        for (Owner o : ReservationSystem.getInstance().getOwners()) {
            if (o.getRestaurantGroup().equals(restaurantGroup)) {
                System.out.printf("%s %s%n", o.getFirstName(), o.getLastName());
            }
        }
    }

    public static void handleViewAllCustomers(String[] tokens) {
        for (Customer customer : ReservationSystem.getInstance().getCustomers()) {
            String id = customer.getId();
            String firstName = customer.getFirstName();
            String lastName = customer.getLastName();
            System.out.printf("%s (%s %s)%n", id, firstName, lastName);
        }
    }

    public static void handleCustomerReview(String[] tokens) {
        // Sort Input
        String customerId = tokens[1];
        String restaurantId = tokens[2];
        LocalDate reservationDate = LocalDate.parse(tokens[3]);
        LocalTime reservationTime = LocalTime.parse(tokens[4]);
        int rating = Integer.parseInt(tokens[5]);
        List<String> tags = Arrays.asList(tokens[6].split(":"));

        // Handle Input
        Customer customer = ReservationSystem.getInstance().getCustomer(customerId);
        Restaurant restaurant = ReservationSystem.getInstance().getRestaurant(restaurantId);
        try {
            customer.reviewRestaurant(restaurant, reservationDate, reservationTime, rating, tags);
            // Output
            System.out.printf("%s (%s) rating for this reservation: %d%n", restaurant.getId(), restaurant.getName(), rating);
            System.out.printf("%s (%s) average rating: %d%n", restaurant.getId(), restaurant.getName(), restaurant.getRating());
            System.out.print("Tags: ");
            for (String tag : restaurant.getTags()) {
                System.out.print(tag + ", ");
            }
            System.out.println();
        } catch (ReservationException.Missed e ){
            System.out.println("ERROR: reservation wasn't successfully completed");
        } catch (ReservationException.DoesNotExist e) {
            System.out.println("ERROR: reservation doesn't exist");
        } catch (ReservationException.NotSuccessful e) {
            System.out.println("ERROR: reservation wasn't successfully completed");
        }
    }
}
