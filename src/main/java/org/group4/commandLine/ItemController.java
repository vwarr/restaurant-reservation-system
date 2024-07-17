package org.group4.commandLine;

import org.group4.*;
import org.group4.exceptions.MenuItemException;
import org.group4.exceptions.OrderFoodException;
import org.group4.exceptions.ReservationException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class ItemController {
    public static void handleAddMenuItem(String[] tokens) {
        // Sort Input
        String itemName = tokens[1];
        String restaurantId = tokens[2];
        int price = Integer.parseInt(tokens[3]);

        // Handle input
        if (ReservationSystem.getInstance().isMenuItemAbsent(itemName)) {
            System.out.println("ERROR: Menu item doesn't exist");
            return;
        }

        MenuItem newItem = ReservationSystem.getInstance().getMenuItem(itemName);
        Restaurant rest = ReservationSystem.getInstance().getRestaurant(restaurantId);
        try {
            rest.addMenuItem(newItem, price);
            // Output
            System.out.printf("(%s) Menu item added: %s - $%d%n", rest.getId(), newItem.getName(), price);
        } catch (MenuItemException.AlreadyAdded aa) {
            // Output
            System.out.println("ERROR: item has already been added to this restaurant, try again");
        }
    }

    public static void handleCreateMenuItem(String[] tokens) {
        // Sort Input
        String itemName = tokens[1];
        String[] ingredients = tokens[2].split(":");

        // Handle input
        MenuItem menuItem = new MenuItem(itemName, ingredients);
        ReservationSystem.getInstance().addMenuItem(menuItem);

        // Output
        System.out.printf("%s created%n", itemName);
    }

    public static void handleOrderMenuItem(String[] tokens) {
        // Sort Input
        String customerId = tokens[1];
        String restaurantId = tokens[2];
        LocalDate reservationDate = LocalDate.parse(tokens[3]);
        LocalTime reservationTime = LocalTime.parse(tokens[4]);
        String menuItemName = tokens[5];
        int quantity = Integer.parseInt(tokens[6]);

        // Handle input
        Customer customer = ReservationSystem.getInstance().getCustomer(customerId);
        Restaurant restaurant = ReservationSystem.getInstance().getRestaurant(restaurantId);
        MenuItem menuItem = ReservationSystem.getInstance().getMenuItem(menuItemName);
        try {
            Reservation reservation = restaurant.orderItem(customer, reservationDate, reservationTime, menuItem, quantity);
            // int totalPrice = restaurant.getRestaurantMenuItems().get(menuItem.getName()).getPrice() * quantity;
            // Output
            System.out.println("Menu item successfully ordered");
            System.out.printf("Total Price for ordered amount: %d%n", reservation.getLastOrderPrice());
            System.out.printf("%s bill for current reservation: %d%n", customer.getId(), reservation.getBill());
            System.out.printf("%s remaining funds: %d\n", customer.getId(), customer.getFunds());
            System.out.printf("%s total revenue from all reservations: %d%n", restaurant.getId(), restaurant.getRevenue());
        } catch (ReservationException.DoesNotExist e) {
            // Output
            System.out.printf("Order failed: Reservation does not exist for %s %s at %s%n", customer.getFirstName(), customer.getLastName(), restaurant.getName());
        } catch (OrderFoodException.NotInRestaurant e) {
            // Output
            System.out.println("Order failed: Item is not in the restaurant");
        } catch (OrderFoodException.InsufficientCredits e) {
            // Output
            System.out.println("Order failed: Insufficient credits");
        }
    }

    public static void handleCalculateAveragePrice(String[] tokens) {
        // Sort Input
        String itemName = tokens[1];

        // Handle Input
        if (ReservationSystem.getInstance().isMenuItemAbsent(itemName)) {
            System.out.println("ERROR: item doesn't exist");
            return;
        }

        MenuItem item = ReservationSystem.getInstance().getMenuItem(itemName);
        try {
            double price = item.getAveragePrice();
            // Output
            System.out.printf("Average price for %s: $%.2f\n", item.getName(), price);
        } catch (MenuItemException.NeverAdded e) {
            // Output
            System.out.println("ERROR: item was never added to a restaurant");
        }
    }


    public static void handleViewAllMenuItems(String[] tokens) {
        for (MenuItem menuItem : ReservationSystem.getInstance().getMenuItems()) {
            String itemName = menuItem.getName();
            System.out.println(itemName);
        }
    }

    public static void handleViewIngredients(String[] tokens) {
        // Sort Input
        String itemName = tokens[1];

        // Handle Input
        MenuItem menuItem = ReservationSystem.getInstance().getMenuItem(itemName);
        String[] ingredientArray = menuItem.getIngredients();
        StringBuilder ingredients = new StringBuilder("Ingredients: ");
        for (int i = 0; i < ingredientArray.length; i++) {
            ingredients.append(ingredientArray[i]);
            if (i < ingredientArray.length - 1) {
                ingredients.append(", ");
            }
        }
        // Output
        System.out.println(ingredients);
    }

    public static void handleViewMenuItems(String[] tokens) {
        // Sort Input
        String restaurantId = tokens[1];

        // Handle Input
        Restaurant restaurant = ReservationSystem.getInstance().getRestaurant(restaurantId);
        Map<String, RestaurantMenuItem> items = restaurant.getRestaurantMenuItems();
        for (RestaurantMenuItem item : items.values()) {
            // Output
            System.out.println(item.getParentItem().getName());
        }
    }

    public static void handleCalculateItemPopularity(String[] tokens) {
        // Sort Input
        String itemName = tokens[1];

        // Handle Input
        if (ReservationSystem.getInstance().isMenuItemAbsent(itemName)) {
            System.out.println("ERROR: item doesn't exist");
            return;
        }

        MenuItem item = ReservationSystem.getInstance().getMenuItem(itemName);
        try {
            int popularity = item.calculatePopularity();
            int totalRestaurants = ReservationSystem.getInstance().getRestaurants().size();
            double popularityPercentage = (popularity / (double) totalRestaurants) * 100;
            if (popularityPercentage > 50) {
                System.out.printf("Popular: %s offered at %d out of %d restaurants (%.0f%%)\n", tokens[1], popularity, totalRestaurants, popularityPercentage);
            } else {
                System.out.printf("Not popular: %s offered at %d out of %d restaurants (%.0f%%)\n", tokens[1], popularity, totalRestaurants, popularityPercentage);
            }
        } catch (MenuItemException.NeverAdded e) {
            System.out.println("ERROR: item was never added to a restaurant");
        }
    }
}
