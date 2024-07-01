package org.group4;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class RestaurantController {
    private final HashMap<String, Restaurant> restaurants = new HashMap<>();
    private final HashMap<String, Customer> customers = new HashMap<>();
    private final HashMap<String, MenuItem> menuItems = new HashMap<>();
    private final HashMap<String, Owner> owners = new HashMap<>();
    private final HashSet<String> ingredients = new HashSet<>();

    public void commandLoop() {
        Scanner commandLineInput = new Scanner(System.in);
        String wholeInputLine;
        String[] tokens;
        final String DELIMITER = ",";

        while (true) {
            try {
                // Determine the next command and echo it to the monitor for testing purposes
                wholeInputLine = commandLineInput.nextLine();
                tokens = wholeInputLine.split(DELIMITER);
                System.out.println(" echo >> " + wholeInputLine);
                if (tokens[0].indexOf("//") == 0) {
                    // System.out.println(wholeInputLine);
                    // instructions to create simulation resources
                } else if (wholeInputLine.isEmpty()) {
                    continue;
                } else if (tokens[0].equals("create_restaurant")) {
                    handleCreateRestaurant(tokens);
                } else if (tokens[0].equals("create_customer")) {
                    handleCreateCustomer(tokens);
                } else if (tokens[0].equals("make_reservation")) {
                    handleMakeReservation(tokens);
                } else if (tokens[0].equals("customer_arrival")) {
                    handleCustomerArrival(tokens);
                } else if (tokens[0].equals("create_owner")) {
                    handleCreateOwner(tokens);
                } else if (tokens[0].equals("add_menu_item")) {
                    handleAddMenuItem(tokens);
                } else if (tokens[0].equals("create_menu_item")) {
                    handleCreateMenuItem(tokens);
                } else if (tokens[0].equals("order_menu_item")) {
                    handleOrderMenuItem(tokens);
                } else if (tokens[0].equals("customer_review")) {
                    handleCustomerReview(tokens);
                } else if (tokens[0].equals("calculate_average_price")) {
                    handleCalculateAveragePrice(tokens);
                } else if (tokens[0].equals("view_owners")) {
                    handleViewOwners(tokens);
                } else if (tokens[0].equals("view_all_restaurants")) {
                    handleViewAllRestaurants(tokens);
                } else if (tokens[0].equals("view_all_customers")) {
                    handleViewAllCustomers(tokens);
                } else if (tokens[0].equals("view_all_menu_items")) {
                    handleViewAllMenuItems(tokens);
                } else if (tokens[0].equals("view_ingredients")) {
                    handleViewIngredients(tokens);
                } else if (tokens[0].equals("view_menu_items")) {
                    handleViewMenuItems(tokens);
                } else if (tokens[0].equals("calculate_item_popularity")) {
                    handleCalculateItemPopularity(tokens);
                } else if (tokens[0].equals("exit")) {
                    System.out.println("stop acknowledged");
                    break;
                } else {
                    System.out.println("command " + tokens[0] + " NOT acknowledged");
                }
                System.out.println("Enter command: ");
            } catch (Exception e) {
                displayMessage("error", "during command loop >> execution");
                e.printStackTrace();
                System.out.println();
            }
        }

        System.out.println("simulation terminated");
        commandLineInput.close();
    }

    private void handleCreateRestaurant(String[] tokens) throws OwnerException.OwnerDoesNotExist {
        Address address = new Address(tokens[3], tokens[4], Integer.parseInt(tokens[5]));
        Owner owner = owners.get(tokens[6]);
        if (owner == null) {
            throw new OwnerException.OwnerDoesNotExist();
        }
        Restaurant restaurant = new Restaurant(tokens[1], tokens[2], address, Integer.parseInt(tokens[6]),
                Boolean.parseBoolean(tokens[7]), Integer.parseInt(tokens[8]), owner);
        System.out.printf("Restaurant created: %s (%s) - %s, %s %s\n", tokens[1], tokens[2], tokens[3], tokens[4], tokens[5]);
        restaurants.put(restaurant.getId(), restaurant);
    }

    private void handleCreateCustomer(String[] tokens) {
        Address address = new Address(tokens[4], tokens[5], Integer.parseInt(tokens[6]));
        Customer customer = new Customer(tokens[1], tokens[2], tokens[3], address,
                Double.parseDouble(tokens[7]));
        System.out.printf("Customer added: %s - %s %s\n", tokens[1], tokens[2], tokens[3]);
        customers.put(customer.getId(), customer);
    }

    private void handleMakeReservation(String[] tokens) {
        Customer customer = customers.get(tokens[1]);
        Restaurant restaurant = restaurants.get(tokens[2]);
        // Turns the date (example: 2024-05-24) and the time (ex: 19:00) to an ISO datetime: "2024-05-24T19:00:00"
        LocalDateTime dateTime = LocalDateTime.parse("%sT%s:00".formatted(tokens[4], tokens[5]));
        try {
            Reservation res = restaurant.makeReservation(customer, Integer.parseInt(tokens[3]), dateTime,
                    Integer.parseInt(tokens[6]));
            System.out.printf("Reservation requested for %s %s", customer.getFirstName(), customer.getLastName());
            System.out.print("\nReservation confirmed");
            System.out.printf("\nReservation made for %s (%s %s) at %s\n", customer.getId(), customer.getFirstName(), customer.getLastName(), restaurant.getName());
        } catch(ReservationException.Conflict rce) {
            System.out.printf("Reservation requested for %s %s", customer.getFirstName(), customer.getLastName());
            System.out.print("\nReservation request denied, customer already has reservation with another restaurant within 2 hours of the requested time\n");
        } catch(ReservationException.FullyBooked nse) {
            System.out.printf("Reservation requested for %s %s", customer.getFirstName(), customer.getLastName());
            System.out.print("\nReservation request denied, table has another active reservation within 2 hours of the requested time\n");
        }
    }

    private void handleCustomerArrival(String[] tokens) {
        Customer customer = customers.get(tokens[1]);
        Restaurant restaurant = restaurants.get(tokens[2]);

        LocalDate reservationDate = LocalDate.parse(tokens[3]);
        LocalTime reservationTime = tokens[5].equals("null") ? null : LocalTime.parse(tokens[5]);
        LocalTime arrivalTime = LocalTime.parse(tokens[4]);

        try {
            restaurant.onCustomerArrival(customer, reservationDate, arrivalTime, reservationTime);
        } catch(NoSpaceException e) {
            // Although kinda scuffed to catch this message here it makes writing tests easier
            System.out.print(IOMessages.getNoSeatsMessage());
        }
        System.out.print(IOMessages.getCustomerInfoMessage(customer));
    }

    private void handleCreateOwner(String[] tokens) {
        // TODO: implement
    }

    private void handleAddMenuItem(String[] tokens) {
        // NOTE: For doesn't exist errors, handle that here
        // that has nothing to do with the menu item class since we can just check for
        // the items's existence in the hashmap that is housed here
        // TODO: implement
    }

    private void handleCreateMenuItem(String[] tokens) {
        // TODO: implement
    }

    private void handleOrderMenuItem(String[] tokens) {
        // TODO: implement
    }

    private void handleCustomerReview(String[] tokens) {
        // TODO: implement
    }

    private void handleCalculateAveragePrice(String[] tokens) {
        // TODO: implement
    }

    private void handleViewOwners(String[] tokens) {
        // TODO: implement
    }

    private void handleViewAllRestaurants(String[] tokens) {
        // TODO: implement
    }

    private void handleViewAllCustomers(String[] tokens) {
        // TODO: implement
    }

    private void handleViewAllMenuItems(String[] tokens) {
        // TODO: implement
    }

    private void handleViewIngredients(String[] tokens) {
        // TODO: implement
    }

    private void handleViewMenuItems(String[] tokens) {
        // TODO: implement
    }

    private void handleCalculateItemPopularity(String[] tokens) {
        // TODO: implement
    }

    void displayMessage(String status, String text_output) {
        System.out.println(status.toUpperCase() + ": " + text_output.toLowerCase());
    }

}
