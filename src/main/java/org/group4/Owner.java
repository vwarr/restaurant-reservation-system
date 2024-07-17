package org.group4;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.UUID;

public class Owner {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final Address address;
    private final LocalDate startDate;
    private final String restaurantGroup;
    private final HashMap<String, Restaurant> ownedRestaurants = new HashMap<>();
    private HashMap<String, License> licenses = new HashMap<>();

    public Owner(LocalDate startDate, String uniqueId, String firstName, String lastName,
                 Address address, String restaurantGroup) {
        this.id = (uniqueId == null) ? UUID.randomUUID().toString() : uniqueId;
        this.startDate = startDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.restaurantGroup = restaurantGroup;
    }

    public void addOwnedRestaurant(Restaurant restaurant) {
        ownedRestaurants.put(restaurant.getId(), restaurant);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public HashMap<String, License> getLicenses() {
        return licenses;
    }
    public HashMap<String, Restaurant> getOwnedRestaurants() { return ownedRestaurants; }

    public void addLicense(String restaurantId, String name) {
        this.licenses.put(restaurantId, new License(id, restaurantId, name));
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
       if (lastName == null) {
            return "";
        }
        return lastName;
    }

    public Address getAddress() {
        return address;
    }

    public String getRestaurantGroup() {return restaurantGroup; }

    public static class Builder {
        private String id;
        private String firstName = "Unnamed Owner";
        private String lastName = "Guy";
        private Address address = new Address("123", "Unnamed Street", "12345");
        private LocalDate startDate = LocalDate.parse("2024-06-05");
        private HashMap<String, License> licenses = new HashMap<>();

        private String restaurantGroup = "Unnamed Group";

        public Builder(String id) {
            this.id = id;
        }

        public Builder setLicenses(HashMap<String, License> licenses) {
            this.licenses = licenses;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder address(String street, String city, String zip) {
            this.address = new Address(street, city, zip);
            return this;
        }

        public Builder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder restaurantGroup(String restaurantGroup) {
            this.restaurantGroup = restaurantGroup;
            return this;
        }

        public Owner build() {
            return new Owner(startDate, id, firstName, lastName, address, restaurantGroup);
        }
    }
}

