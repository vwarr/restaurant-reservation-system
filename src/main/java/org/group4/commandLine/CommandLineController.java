package org.group4.commandLine;


import java.util.HashSet;
import java.util.Scanner;

public class CommandLineController {
    private final HashSet<String> ingredients = new HashSet<>();

    public void commandLoop() {
        Scanner commandLineInput = new Scanner(System.in);
        String wholeInputLine;
        final String DELIMITER = ", ";

        while (true) {
            try {
                // Determine the next command and echo it to the monitor for testing purposes
                wholeInputLine = commandLineInput.nextLine();
                final String[] tokens = wholeInputLine.split(DELIMITER);
                System.out.println(" echo >> " + wholeInputLine);
                if (tokens[0].indexOf("//") == 0) {
                    // System.out.println(wholeInputLine);
                    // instructions to create simulation resources
                    continue;
                } else if (wholeInputLine.isEmpty()) {
                    continue;
                } else if (tokens[0].equals("exit")) {
                    System.out.println("stop acknowledged");
                    break;
                }

                switch (tokens[0]) {
                    case "create_restaurant" -> { RestaurantController.handleCreateRestaurant(tokens); }
                    case "create_customer" -> PeopleController.handleCreateCustomer(tokens);
                    case "make_reservation" -> RestaurantController.handleMakeReservation(tokens);
                    case "customer_arrival" -> RestaurantController.handleCustomerArrival(tokens);
                    case "create_owner" -> PeopleController.handleCreateOwner(tokens);
                    case "add_menu_item" -> ItemController.handleAddMenuItem(tokens);
                    case "create_menu_item" -> ItemController.handleCreateMenuItem(tokens);
                    case "order_menu_item" -> ItemController.handleOrderMenuItem(tokens);
                    case "customer_review" -> PeopleController.handleCustomerReview(tokens);
                    case "calculate_average_price" -> ItemController.handleCalculateAveragePrice(tokens);
                    case "view_owners" -> PeopleController.handleViewOwners(tokens);
                    case "view_all_restaurants" -> RestaurantController.handleViewAllRestaurants(tokens);
                    case "view_restaurants_owned" -> RestaurantController.handleViewRestaurantsOwned(tokens);
                    case "view_all_customers" -> PeopleController.handleViewAllCustomers(tokens);
                    case "view_all_menu_items" -> ItemController.handleViewAllMenuItems(tokens);
                    case "view_ingredients" -> ItemController.handleViewIngredients(tokens);
                    case "view_menu_items" -> ItemController.handleViewMenuItems(tokens);
                    case "calculate_item_popularity" -> ItemController.handleCalculateItemPopularity(tokens);
                    default -> System.out.println("command " + tokens[0] + " NOT acknowledged");
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

    void displayMessage(String status, String text_output) {
        System.out.println(status.toUpperCase() + ": " + text_output.toLowerCase());
    }

}
