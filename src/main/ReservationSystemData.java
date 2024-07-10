package main;

import java.util.Collection;
import java.util.HashMap;

public class ReservationSystemData {
    private static ReservationSystemData instance;

    private final HashMap<String, Restaurant> restaurants = new HashMap<>();
    private final HashMap<String, Customer> customers = new HashMap<>();
    private final HashMap<String, MenuItem> menuItems = new HashMap<>();
    private final HashMap<String, Owner> owners = new HashMap<>();

    private ReservationSystemData() {

    }

    public static ReservationSystemData getInstance() {
        if (instance == null) {
            instance = new ReservationSystemData();
        }
        return instance;
    }

    public Owner getOwner(String ownerId) {
        return owners.get(ownerId);
    }

    public boolean doesOwnerExist(String ownerId) {
        return owners.containsKey(ownerId);
    }

    public void addOwner(Owner owner) {
        owners.put(owner.getId(), owner);
    }

    public Collection<Owner> getOwners() {
        return owners.values();
    }

    public Restaurant getRestaurant(String restaurantId) {
        return restaurants.get(restaurantId);
    }

    public Collection<Restaurant> getRestaurants() {
        return restaurants.values();
    }

    public void addRestaurant(Restaurant restaurant) {
        restaurants.put(restaurant.getId(), restaurant);
    }

    public Customer getCustomer(String customerId) {
        return customers.get(customerId);
    }

    public Collection<Customer> getCustomers() {
        return customers.values();
    }

    public void addCustomer(Customer customer) {
        customers.put(customer.getId(), customer);
    }

    public void addMenuItem(MenuItem menuItem) {
        menuItems.put(menuItem.getName(), menuItem);
    }

    public MenuItem getMenuItem(String menuItemName) {
        return menuItems.get(menuItemName);
    }

    public Collection<MenuItem> getMenuItems() {
        return menuItems.values();
    }

    public boolean isMenuItemAbsent(String menuItemName) {
        return !menuItems.containsKey(menuItemName);
    }

}
