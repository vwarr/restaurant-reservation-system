package org.group4;

import org.group4.exceptions.NoSpaceException;
import org.group4.exceptions.ReservationException;
import org.group4.requests.CreateOwnerRequest;
import org.group4.requests.CreateRestaurantRequest;
import org.group4.requests.CustomerArrivalRequest;
import org.group4.requests.ReservationRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;

/**
 * Responsible for holding the system's data in addition to
 * consolidating all of the boilerplate construction of business objects.
 */
public class ReservationSystem {
    private static ReservationSystem instance;

    private final HashMap<String, Restaurant> restaurants = new HashMap<>();
    private final HashMap<String, Customer> customers = new HashMap<>();
    private final HashMap<String, MenuItem> menuItems = new HashMap<>();
    private final HashMap<String, Owner> owners = new HashMap<>();

    private ReservationSystem() {

    }

    public static ReservationSystem getInstance() {
        if (instance == null) {
            instance = new ReservationSystem();
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

    public Restaurant createRestaurant(CreateRestaurantRequest request) {
        Restaurant restaurant = new Restaurant(
                request.restaurantId(),
                request.restaurantName(),
                request.address(),
                -1,
                false,
                request.seatingCapacity(),
                getOwner(request.ownerId()),
                request.licenseId()
        );

        restaurant.getOwner().addOwnedRestaurant(restaurant);
        restaurant.getOwner().addLicense(restaurant.getId(), restaurant.getLicenseId());

        addRestaurant(restaurant);
        return restaurant;
    }

    public Reservation createReservation(ReservationRequest request)
            throws ReservationException.Conflict, ReservationException.FullyBooked {
        Customer customer = getCustomer(request.customerId());
        Restaurant restaurant = getRestaurant(request.restaurantId());
        return restaurant.makeReservation(customer, request.partySize(), LocalDateTime.parse(request.dateTime()), request.credits());
    }

    public Owner createOwner(CreateOwnerRequest request) {
        Owner owner = new Owner(
                LocalDate.parse(request.startDate()),
                request.ownerId(),
                request.firstName(),
                request.lastName(),
                request.address(),
                request.restaurantGroup()
        );

        addOwner(owner);
        return owner;
    }

    public void registerCustomerArrival(CustomerArrivalRequest request) throws NoSpaceException {
        Customer customer = getCustomer(request.customerId());
        Restaurant restaurant = getRestaurant(request.restaurantId());

        restaurant.onCustomerArrival(customer, LocalDate.parse(request.reservationDate()), LocalTime.parse(request.arrivalTime()), LocalTime.parse(request.reservationTime()));
    }

    public boolean doesRestaurantExist(String restaurantId) {
        return restaurants.containsKey(restaurantId);
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

    public boolean doesCustomerExist(String customerId) {
        return customers.containsKey(customerId);
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
