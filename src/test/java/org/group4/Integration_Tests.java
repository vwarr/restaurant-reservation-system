package org.group4;

import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class Integration_Tests {


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

        restaurant.onCustomerArrival(customer, date, time, time);

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
}
