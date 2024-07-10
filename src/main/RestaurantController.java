package main;


import main.Exceptions.OrderFoodException;
import main.Exceptions.MenuItemException;
import main.Exceptions.NoSpaceException;
import main.Exceptions.ReservationException;
import main.InputHandlers.ItemInputHandler;
import main.InputHandlers.PeopleInputHandler;
import main.InputHandlers.RestaurantInputHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class RestaurantController {
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
                    case "create_restaurant" -> { RestaurantInputHandler.handleCreateRestaurant(tokens); }
                    case "create_customer" -> PeopleInputHandler.handleCreateCustomer(tokens);
                    case "make_reservation" -> RestaurantInputHandler.handleMakeReservation(tokens);
                    case "customer_arrival" -> RestaurantInputHandler.handleCustomerArrival(tokens);
                    case "create_owner" -> PeopleInputHandler.handleCreateOwner(tokens);
                    case "add_menu_item" -> ItemInputHandler.handleAddMenuItem(tokens);
                    case "create_menu_item" -> ItemInputHandler.handleCreateMenuItem(tokens);
                    case "order_menu_item" -> ItemInputHandler.handleOrderMenuItem(tokens);
                    case "customer_review" -> PeopleInputHandler.handleCustomerReview(tokens);
                    case "calculate_average_price" -> ItemInputHandler.handleCalculateAveragePrice(tokens);
                    case "view_owners" -> PeopleInputHandler.handleViewOwners(tokens);
                    case "view_all_restaurants" -> RestaurantInputHandler.handleViewAllRestaurants(tokens);
                    case "view_restaurants_owned" -> RestaurantInputHandler.handleViewRestaurantsOwned(tokens);
                    case "view_all_customers" -> PeopleInputHandler.handleViewAllCustomers(tokens);
                    case "view_all_menu_items" -> ItemInputHandler.handleViewAllMenuItems(tokens);
                    case "view_ingredients" -> ItemInputHandler.handleViewIngredients(tokens);
                    case "view_menu_items" -> ItemInputHandler.handleViewMenuItems(tokens);
                    case "calculate_item_popularity" -> ItemInputHandler.handleCalculateItemPopularity(tokens);
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
