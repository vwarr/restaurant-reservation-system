package main.Exceptions;

import main.Reservation;

public class ReservationException {

    public static class Conflict extends Exception {
        public Conflict() {
            super("Customer has a reservation within %s hours of this time".formatted(Reservation.RESERVATION_DURATION));
        }
    }
    public static class Missed extends Exception {
        public Missed() {super("Customer was late.");}
    }
    public static class FullyBooked extends Exception {
        public FullyBooked() {
            super("Restaurant is fully booked at this time");
        }
    }

    public static class DoesNotExist extends Exception {
        public DoesNotExist() {
            super("ERROR: Reservation does not exist");
        }
    }

    public static class NotSuccessful extends Exception {
        public NotSuccessful() {
            super("ERROR: Reservation was not successfully completed");
        }
    }
}