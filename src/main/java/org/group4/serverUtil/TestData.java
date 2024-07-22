package org.group4.serverUtil;

import org.group4.*;

import java.time.LocalDate;

public class TestData {

    public static void generateCustomers() {
        // create_customer,CUST001,Angel,Cabrera,Miami,FL,33122,100.0
        ReservationSystem.getInstance().addCustomer(
                new Customer.Builder("CUST001")
                        .firstName("Angel")
                        .lastName("Cabrera")
                        .address("Miami", "FL", "33122")
                        .funds(100)
                        .build());

        // create_customer,CUST002,Mark,Moss,Atlanta,GA,30313,100.0
        ReservationSystem.getInstance().addCustomer(
                new Customer.Builder("CUST002")
                        .firstName("Mark")
                        .lastName("Moss")
                        .address("Atlanta", "GA", "30313")
                        .funds(100)
                        .build());

        // create_customer,CUST003,Neel,Ganediwal,Parkland,FL,33067,100.0
        ReservationSystem.getInstance().addCustomer(
                new Customer.Builder("CUST003")
                        .firstName("Neel")
                        .lastName("Ganediwal")
                        .address("Parkland", "FL", "33067")
                        .funds(100)
                        .build());

        // create_customer,CUST004,Henry,Owen,Chicago,IL,60629,100.0
        ReservationSystem.getInstance().addCustomer(
                new Customer.Builder("CUST004")
                        .firstName("Henry")
                        .lastName("Owen")
                        .address("Chicago", "IL", "60629")
                        .funds(100)
                        .build());
    }

    public static void generateOwners() {
        /*
        create_owner, OWN001, Ricardo, Cardenas, Orlando, FL, 32801, 2022-05-30, Restaurant Brands
        create_owner, OWN002, Andrall, Pearson, Louisville, KY, 40213, 2015-07-17, Yum Brands
        create_owner, OWN003, Joshua, Kobza, Toronto, ON, M5X, 2023-05-01, Restaurant Brands
        */
        ReservationSystem.getInstance().addOwner(
            new Owner.Builder("OWN001")
                .firstName("Ricardo")
                .lastName("Cardenas")
                .address("Orlando", "FL", "32801")
                .startDate(LocalDate.parse("2022-05-30"))
                .restaurantGroup("Restaurant Brands")
                .build()
        );

        ReservationSystem.getInstance().addOwner(
            new Owner.Builder("OWN002")
                .firstName("Andrall")
                .lastName("Pearson")
                .address("Louisville", "KY", "40213")
                .startDate(LocalDate.parse("2015-07-17"))
                .restaurantGroup("Yum Brands")
                .build()
        );

        ReservationSystem.getInstance().addOwner(
            new Owner.Builder("OWN003")
                .firstName("Joshua")
                .lastName("Kobza")
                .address("Toronto", "ON", "M5X")
                .startDate(LocalDate.parse("2023-05-01"))
                .restaurantGroup("Restaurant Brands")
                .build()
        );
    }

    public static void generateRestaurants() {
        /*
        create_restaurant, REST001, Mellow Mushroom, Boca Raton, FL, 33431, 8,
OWN001, FS817
create_restaurant, REST002, Little Caesars, Key West, FL, 30289, 7,
OWN001, FS526
create_restaurant, REST003, Pizza Hut, Buckhead, GA, 30625, 7, OWN002,
FS035
create_restaurant, REST004, Dominoâs, Alpharetta, GA, 30504, 7, OWN002,
FS182
create_restaurant, REST005, Papa Johns, San Diego, CA, 94105, 7, OWN003,
FS942
create_restaurant, REST006, Blaze Pizza, San Francisco, CA, 92101, 7,
OWN003, FS752
         */

        ReservationSystem.getInstance().addRestaurant(
            new Restaurant.Builder("REST001")
                .name("Mellow Mushroom")
                .address("Boca Raton", "FL", "33431")
                .seatingCapacity(8)
                .licenseId("FS817")
                .owner(ReservationSystem.getInstance().getOwner("OWN001"))
                .top10(false)
                .rating(-1)
                .build());

        ReservationSystem.getInstance().addRestaurant(
            new Restaurant.Builder("REST002")
                .name("Little Caesars")
                .address("Key West", "FL", "30289")
                .seatingCapacity(7)
                .licenseId("FS526")
                .owner(ReservationSystem.getInstance().getOwner("OWN001"))
                .top10(false)
                .rating(-1)
                .build());

        ReservationSystem.getInstance().addRestaurant(
            new Restaurant.Builder("REST003")
                .name("Pizza Hut")
                .address("Buckhead", "GA", "30625")
                .seatingCapacity(7)
                .licenseId("FS035")
                .owner(ReservationSystem.getInstance().getOwner("OWN002"))
                .top10(false)
                .rating(-1)
                .build());

        ReservationSystem.getInstance().addRestaurant(
            new Restaurant.Builder("REST004")
                .name("Domino's")
                .address("Alpharetta", "GA", "30504")
                .seatingCapacity(7)
                .licenseId("FS182")
                .owner(ReservationSystem.getInstance().getOwner("OWN002"))
                .top10(false)
                .rating(-1)
                .build());

        ReservationSystem.getInstance().addRestaurant(
            new Restaurant.Builder("REST005")
                .name("Papa Johns")
                .address("San Diego", "CA", "94105")
                .seatingCapacity(7)
                .licenseId("FS942")
                .owner(ReservationSystem.getInstance().getOwner("OWN003"))
                .top10(false)
                .rating(-1)
                .build());

        ReservationSystem.getInstance().addRestaurant(
            new Restaurant.Builder("REST006")
                .name("Blaze Pizza")
                .address("San Francisco", "CA", "92101")
                .seatingCapacity(7)
                .licenseId("FS752")
                .owner(ReservationSystem.getInstance().getOwner("OWN003"))
                .top10(false)
                .rating(-1)
                .build());
    }
}
