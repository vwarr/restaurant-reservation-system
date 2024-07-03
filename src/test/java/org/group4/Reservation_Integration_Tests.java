package org.group4;

import org.group4.Exceptions.NoSpaceException;
import org.group4.Exceptions.ReservationException;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.group4.Reservation.WALK_IN_PARTY_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class Reservation_Integration_Tests {

    @Test
    void Reservation_And_Arrival_HAPPY_PATH() {
        // Arrange
        Restaurant restaurant = new Restaurant.Builder("REST001").seatingCapacity(6).build();
        Customer customer = new Customer.Builder("CUST001").build();

        int initialCredits = customer.getCredits();
        int reservationCredits = 80;

        LocalDate date = LocalDate.parse("2024-05-24");
        LocalTime time = LocalTime.parse("19:00");

        // Act
        Reservation reservation = assertDoesNotThrow(
                () -> restaurant.makeReservation(customer, 4, LocalDateTime.of(date, time), reservationCredits));

        assertDoesNotThrow(() -> restaurant.onCustomerArrival(customer, date, time, time));

        // Assert
        assertEquals(restaurant.getSeatingCapacity() - reservation.getPartySize(),
                restaurant.checkSpace(reservation.getDateTime()), "Restaurant space not updated properly");
        assertEquals(initialCredits + reservationCredits, customer.getCredits(),
                "Customer credits not awarded for on time reservation");
    }

    @Test
    void Reservation_FAIL_No_Space() {
        // Arrange
        Restaurant restaurant = new Restaurant.Builder("R1").seatingCapacity(6).build();
        Customer customer = new Customer.Builder("C1").build();
        Customer customer2 = new Customer.Builder("C2").build();
        LocalDateTime dateTime = LocalDateTime.parse("2024-05-24T19:00:00");

        // Act
        assertDoesNotThrow(
                () -> restaurant.makeReservation(customer, 4, dateTime, 80));

        // Assert
        assertThrows(ReservationException.FullyBooked.class,
                () -> restaurant.makeReservation(customer2, 4, dateTime, 100));
    }

    @Test
    void Reservation_FAIL_Conflict() {
        // Arrange
        Restaurant restaurant = new Restaurant.Builder("R1").seatingCapacity(6).build();
        Restaurant restaurant2 = new Restaurant.Builder("R2").seatingCapacity(6).build();
        Customer customer = new Customer.Builder("C2").build();

        LocalDateTime dateTime = LocalDateTime.parse("2024-05-24T19:00:00");

        // Act
        assertDoesNotThrow(
                () -> restaurant.makeReservation(customer, 4, dateTime, 80));

        // Assert
        assertThrows(ReservationException.Conflict.class,
                () -> restaurant2.makeReservation(customer, 4, dateTime, 90));
    }

    @Test
    void Reservation_SUCCESS_2hrs_later() {
        // Arrange
        Restaurant restaurant = new Restaurant.Builder("R1").seatingCapacity(6).build();
        Restaurant restaurant2 = new Restaurant.Builder("R2").seatingCapacity(6).build();
        Customer customer = new Customer.Builder("C2").build();

        LocalDateTime dateTime = LocalDateTime.parse("2024-05-24T19:00:00");

        // Act
        assertDoesNotThrow(
                () -> restaurant.makeReservation(customer, 4, dateTime, 80));

        // Assert
        assertDoesNotThrow(() -> restaurant2.makeReservation(customer, 4, dateTime.plusHours(2), 90));
    }

    @Test
    void Reservation_SUCCESS_2hrs_before() {
        // Arrange
        Restaurant restaurant = new Restaurant.Builder("R1").seatingCapacity(6).build();
        Restaurant restaurant2 = new Restaurant.Builder("R2").seatingCapacity(6).build();
        Customer customer = new Customer.Builder("C2").build();

        LocalDateTime dateTime = LocalDateTime.parse("2024-05-24T19:00:00");

        // Act
        assertDoesNotThrow(
                () -> restaurant.makeReservation(customer, 4, dateTime, 80));

        // Assert
        assertDoesNotThrow(() -> restaurant2.makeReservation(customer, 4, dateTime.minusHours(2), 90));
    }

    @Test
    void Reservation_FAIL_Invalid_Party_Size() {
        // Arrange
        Restaurant restaurant = new Restaurant.Builder("R1").seatingCapacity(6).build();
        Customer customer = new Customer.Builder("C1").build();
        LocalDateTime dateTime = LocalDateTime.parse("2024-05-24T19:00:00");

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> restaurant.makeReservation(customer, 0, dateTime, 80));
    }

    @Test
    void Arrival_ON_TIME_15min_after() {
        // Arrange
        Restaurant restaurant = new Restaurant.Builder("R1").seatingCapacity(6).build();
        Customer customer = new Customer.Builder("C1").build();

        LocalDateTime dateTime = LocalDateTime.parse("2024-05-24T19:00:00");

        // Act
        assertDoesNotThrow(
                () -> restaurant.makeReservation(customer, 4, dateTime, 80));
        assertDoesNotThrow(
                () -> restaurant.onCustomerArrival(customer, dateTime.toLocalDate(),
                        dateTime.toLocalTime().plusMinutes(15), dateTime.toLocalTime()));

        // Assert
        assertAll(
                () -> assertEquals(0, customer.getMissedReservations(),
                        "Missed reservations not updated after missed arrival"),
                () -> assertEquals(80, customer.getCredits(), "Credits not updated after missed arrival")
        );
    }

    @Test
    void Arrival_ON_TIME_30min_before() {
        // Arrange
        Restaurant restaurant = new Restaurant.Builder("R1").seatingCapacity(6).build();
        Customer customer = new Customer.Builder("C1").build();

        LocalDateTime dateTime = LocalDateTime.parse("2024-05-24T19:00:00");

        // Act
        assertDoesNotThrow(
                () -> restaurant.makeReservation(customer, 4, dateTime, 80));
        assertDoesNotThrow(
                () -> restaurant.onCustomerArrival(customer, dateTime.toLocalDate(),
                        dateTime.toLocalTime().minusMinutes(30), dateTime.toLocalTime()));

        // Assert
        assertAll(
                () -> assertEquals(0, customer.getMissedReservations(),
                        "Missed reservations not updated after missed arrival"),
                () -> assertEquals(80, customer.getCredits(), "Credits not updated after missed arrival")
        );
    }

    @Test
    void Arrival_LATE_Add_Miss() {
        // Arrange
        Restaurant restaurant = new Restaurant.Builder("R1").seatingCapacity(6).build();
        Customer customer = new Customer.Builder("C1").build();

        LocalDateTime dateTime = LocalDateTime.parse("2024-05-24T19:00:00");

        // Act
        assertDoesNotThrow(
                () -> restaurant.makeReservation(customer, 4, dateTime, 80));
        assertDoesNotThrow(
                () -> restaurant.onCustomerArrival(customer, dateTime.toLocalDate(),
                        dateTime.toLocalTime().plusMinutes(30), dateTime.toLocalTime()));

        // Assert
        assertAll(
                () -> assertEquals(1, customer.getMissedReservations(),
                        "Missed reservations not updated after missed arrival"),
                () -> assertEquals(0, customer.getCredits(), "Credits not updated after missed arrival")
        );
    }

    @Test
    void Arrival_LATE_3_Misses_Reset() {
        // Arrange
        Restaurant restaurant = new Restaurant.Builder("R1").seatingCapacity(6).build();
        // Start customer with 2 missed reservations already
        Customer customer = new Customer.Builder("C1").missedReservations(2).build();

        // Make three reservations 3 days apart
        LocalDateTime dt1 = LocalDateTime.parse("2024-05-24T19:00:00");

        // Act
        // Reservation 1
        assertDoesNotThrow(
                () -> restaurant.makeReservation(customer, 4, dt1, 80));
        assertDoesNotThrow(
                () -> restaurant.onCustomerArrival(customer, dt1.toLocalDate(), dt1.toLocalTime().plusMinutes(30),
                        dt1.toLocalTime()));

        // Assert
        assertAll(
                () -> assertEquals(0, customer.getMissedReservations(),
                        "Missed reservations not reset after 3 successful reservations"),
                () -> assertEquals(0, customer.getCredits(), "Credits not reset after 3 misses")
        );
    }

    @Test
    void Arrival_EARLY_No_Change() {
        // Arrange
        Restaurant restaurant = new Restaurant.Builder("R1").seatingCapacity(6).build();
        Customer customer = new Customer.Builder("C1").build();

        LocalDateTime dateTime = LocalDateTime.parse("2024-05-24T19:00:00");

        // Act
        assertDoesNotThrow(
                () -> restaurant.makeReservation(customer, 4, dateTime, 80));
        assertDoesNotThrow(
                () -> restaurant.onCustomerArrival(customer, dateTime.toLocalDate(),
                        dateTime.toLocalTime().minusMinutes(45), dateTime.toLocalTime()));

        // Assert
        assertAll(
                () -> assertEquals(0, customer.getMissedReservations(),
                        "Missed reservations preserved after coming early"),
                () -> assertEquals(0, customer.getCredits(), "Credits changed after coming early")
        );
    }

    @Test
    void Arrival_WALK_IN_Seated() {
        // Arrange
        Restaurant restaurant = new Restaurant.Builder("R1").seatingCapacity(6).build();
        Customer customer = new Customer.Builder("C1").build();

        LocalDateTime dateTime = LocalDateTime.parse("2024-05-24T19:00:00");

        // Act
        assertDoesNotThrow(
                () -> restaurant.onCustomerArrival(customer, dateTime.toLocalDate(), dateTime.toLocalTime(), null));

        // Assert
        assertEquals(restaurant.getSeatingCapacity() - WALK_IN_PARTY_SIZE,
                restaurant.checkSpace(dateTime), "Restaurant space not updated properly");
    }

    @Test
    void Arrival_WALK_IN_No_Seats() {
        final int MAX_SEATS = 4;

        // Arrange
        Restaurant restaurant = new Restaurant.Builder("R1").seatingCapacity(MAX_SEATS).build();
        Customer customer = new Customer.Builder("C1").build();
        Customer customer2 = new Customer.Builder("C2").build();

        LocalDateTime dateTime = LocalDateTime.parse("2024-05-24T19:00:00");

        // Act
        // Make a reservation at a certain time, then ...
        assertDoesNotThrow(
                () -> restaurant.makeReservation(customer, MAX_SEATS, dateTime, 80));

        // ...try to walk in at the same time
        // Okay, yes, a walk in is literally just another reservation but whatever.
        assertAll(
                () -> assertThrows(ReservationException.FullyBooked.class,
                        () -> restaurant.makeReservation(customer2, MAX_SEATS, dateTime, 0)),
                () -> assertEquals(0, restaurant.checkSpace(dateTime), "Restaurant space not updated properly"));
    }

    @Test
    void Arrival_LATE_No_Seats() {
        final int MAX_SEATS = 4;

        // Arrange
        Restaurant restaurant = new Restaurant.Builder("R1").seatingCapacity(MAX_SEATS).build();
        Customer customer = new Customer.Builder("C1").build();
        Customer customer2 = new Customer.Builder("C2").build();

        LocalDateTime dateTime = LocalDateTime.parse("2024-05-24T19:00:00");

        // Act
        // Make a reservation 2 hours after the guy whos gonna be late
        Reservation res1 = assertDoesNotThrow(
                () -> restaurant.makeReservation(customer, MAX_SEATS, dateTime.plusHours(1), 80));

        // Make the reservation for the eventual late guy
        assertDoesNotThrow(
                () -> restaurant.makeReservation(customer2, MAX_SEATS, dateTime, 80));

        // Assert
        assertThrows(NoSpaceException.class,
                () -> restaurant.onCustomerArrival(customer2, dateTime.toLocalDate(), dateTime.toLocalTime().plusMinutes(61), dateTime.toLocalTime()));
    }

}
