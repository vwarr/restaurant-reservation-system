package org.group4;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class Phase_1_Tests {

    @Test
    void Integration_Test() {
        // create_restaurant,REST001,Olive Garden,Coral Springs,FL,33071,45,false,6
        Restaurant rest001 = new Restaurant.Builder("REST001")
                .name("Olive Garden")
                .address("Coral Springs", "FL", 33071)
                .rating(45)
                .top10(false)
                .seatingCapacity(6)
                .build();

        // create_restaurant,REST002,Cheesecake Factory,Boca Raton,FL,33431,48,false,
        Restaurant rest002 = new Restaurant.Builder("REST002")
                .name("Cheesecake Factory")
                .address("Boca Raton", "FL", 33431)
                .rating(48)
                .top10(false)
                .build();

        // create_restaurant,REST003,Papa Johns,Lauderdale Lakes,FL,33313,50,false,6
        Restaurant rest003 = new Restaurant.Builder("REST003")
                .name("Papa Johns")
                .address("Lauderdale Lakes", "FL", 33313)
                .rating(50)
                .top10(false)
                .seatingCapacity(6)
                .build();

        // create_restaurant,REST004,IL Mulino,Tampa,FL,33019,33,false,6
        Restaurant rest004 = new Restaurant.Builder("REST004")
                .name("IL Mulino")
                .address("Tampa", "FL", 33019)
                .rating(33)
                .top10(false)
                .seatingCapacity(6)
                .build();

        // create_customer,CUST001,Angel,Cabrera,Miami,FL,33122,100.0
        Customer cust001 = new Customer.Builder("CUST001")
                .firstName("Angel")
                .lastName("Cabrera")
                .address("Miami", "FL", 33122)
                .funds(100.0)
                .build();

        // create_customer,CUST002,Mark,Moss,Atlanta,GA,30313,100.0
        Customer cust002 = new Customer.Builder("CUST002")
                .firstName("Mark")
                .lastName("Moss")
                .address("Atlanta", "GA", 30313)
                .funds(100.0)
                .build();

        // create_customer,CUST003,Neel,Ganediwal,Parkland,FL,33067,100.0
        Customer cust003 = new Customer.Builder("CUST003")
                .firstName("Neel")
                .lastName("Ganediwal")
                .address("Parkland", "FL", 33067)
                .funds(100.0)
                .build();

        // create_customer,CUST004,Henry,Owen,Chicago,IL,60629,100.0
        Customer cust004 = new Customer.Builder("CUST004")
                .firstName("Henry")
                .lastName("Owen")
                .address("Chicago", "IL", 60629)
                .funds(100.0)
                .build();

        // make_reservation,CUST001,REST001,4,2024-05-24,19:00,80
        assertDoesNotThrow(
                () -> rest001.makeReservation(cust001, 4, LocalDateTime.parse("2024-05-24T19:00:00"), 80));

        // make_reservation,CUST002,REST002,5,2024-05-23,19:00,100
        assertDoesNotThrow(
                () -> rest002.makeReservation(cust002, 5, LocalDateTime.parse("2024-05-23T19:00:00"), 100));

        // make_reservation,CUST003,REST003,6,2024-05-24,19:00,70
        assertDoesNotThrow(
                () -> rest003.makeReservation(cust003, 6, LocalDateTime.parse("2024-05-24T19:00:00"), 70));

        // make_reservation,CUST004,REST001,4,2024-05-24,19:00,100
        assertThrows(ReservationException.FullyBooked.class,
                () -> rest001.makeReservation(cust004, 4, LocalDateTime.parse("2024-05-24T19:00:00"), 100));

        // make_reservation,CUST001,REST003,4,2024-05-24,17:30,90
        assertThrows(ReservationException.Conflict.class,
                () -> rest003.makeReservation(cust001, 4, LocalDateTime.parse("2024-05-24T17:30:00"), 90));

        // customer_arrival,CUST001,REST001,2024-05-24,18:50,19:00
        assertDoesNotThrow(
                () -> rest001.onCustomerArrival(cust001, LocalDate.parse("2024-05-24"), LocalTime.parse("18:50:00"), LocalTime.parse("19:00:00")));

        assertEquals(80, cust001.getCredits());
        assertEquals(0, cust002.getMissedReservations());

        // customer_arrival,CUST002,REST002,2024-05-23,18:00,19:00
        assertDoesNotThrow(
                () -> rest002.onCustomerArrival(cust002, LocalDate.parse("2024-05-23"), LocalTime.parse("18:00:00"), LocalTime.parse("19:00:00")));

        assertEquals(0, cust002.getCredits());
        assertEquals(0, cust002.getMissedReservations());

        // customer_arrival,CUST003,REST003,2024-05-24,19:30,19:00
        assertDoesNotThrow(
                () -> rest003.onCustomerArrival(cust003, LocalDate.parse("2024-05-24"), LocalTime.parse("19:30:00"), LocalTime.parse("19:00:00")));

        assertEquals(0, cust003.getCredits());
        assertEquals(1, cust003.getMissedReservations());

        // customer_arrival,CUST004,REST004,2024-05-23,19:30,null
        assertDoesNotThrow(
                () -> rest004.onCustomerArrival(cust004, LocalDate.parse("2024-05-23"), LocalTime.parse("19:30:00"), null));

        assertEquals(0, cust004.getCredits());
        assertEquals(0, cust004.getMissedReservations());

        // make_reservation,CUST001,REST003,4,2024-05-25,19:00,70
        assertDoesNotThrow(
                () -> rest003.makeReservation(cust001, 4, LocalDateTime.parse("2024-05-25T19:00:00"), 70));

        // customer_arrival,CUST001,REST003,2024-05-25,19:30,19:00
        assertDoesNotThrow(
                () -> rest003.onCustomerArrival(cust001, LocalDate.parse("2024-05-25"), LocalTime.parse("19:30:00"), LocalTime.parse("19:00:00")));

        assertEquals(80, cust001.getCredits());
        assertEquals(1, cust001.getMissedReservations());

        // make_reservation,CUST001,REST004,4,2024-05-26,19:00,30
        assertDoesNotThrow(
                () -> rest004.makeReservation(cust001, 4, LocalDateTime.parse("2024-05-26T19:00:00"), 30));

        // customer_arrival,CUST001,REST004,2024-05-26,19:30,19:00
        assertDoesNotThrow(
                () -> rest004.onCustomerArrival(cust001, LocalDate.parse("2024-05-26"), LocalTime.parse("19:30:00"), LocalTime.parse("19:00:00")));

        assertEquals(80, cust001.getCredits());
        assertEquals(2, cust001.getMissedReservations());

        // make_reservation,CUST001,REST002,4,2024-05-28,19:00,50
        assertDoesNotThrow(
                () -> rest002.makeReservation(cust001, 4, LocalDateTime.parse("2024-05-28T19:00:00"), 50));

        // customer_arrival,CUST001,REST002,2024-05-28,19:10,19:00
        assertDoesNotThrow(
                () -> rest002.onCustomerArrival(cust001, LocalDate.parse("2024-05-28"), LocalTime.parse("19:10:00"), LocalTime.parse("19:00:00")));

        assertEquals(130, cust001.getCredits());
        assertEquals(2, cust001.getMissedReservations());

        // make_reservation,CUST001,REST002,4,2024-05-27,19:00,50
        assertDoesNotThrow(
                () -> rest002.makeReservation(cust001, 4, LocalDateTime.parse("2024-05-27T19:00:00"), 50));

        // customer_arrival,CUST001,REST002,2024-05-27,19:30,19:00
        assertDoesNotThrow(
                () -> rest002.onCustomerArrival(cust001, LocalDate.parse("2024-05-27"), LocalTime.parse("19:30:00"), LocalTime.parse("19:00:00")));

        assertEquals(0, cust001.getCredits());
        assertEquals(0, cust001.getMissedReservations());
    }
}
