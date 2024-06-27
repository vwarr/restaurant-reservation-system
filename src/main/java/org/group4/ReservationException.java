package org.group4;

public class ReservationException {

    public static class Conflict extends Exception {
        public Conflict() {
            super("Customer has a reservation within %s hours of this time".formatted(Reservation.RESERVATION_DURATION));
        }
    }

    public static class FullyBooked extends Exception {
        public FullyBooked() {
            super("Restaurant is fully booked at this time");
        }
    }
}