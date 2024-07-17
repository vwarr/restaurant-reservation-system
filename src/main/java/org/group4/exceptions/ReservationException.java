package org.group4.exceptions;

import org.group4.Customer;
import org.group4.Reservation;

public class ReservationException {

    public static class Conflict extends Exception {
        public Conflict() {
            super("Reservation request denied, customer already has reservation with another restaurant within 2 hours of the requested time");
        }
    }
    public static class Missed extends Exception {
        public Missed() {super("Customer was late.");}
    }
    public static class FullyBooked extends Exception {
        public FullyBooked() {
            super("Reservation request denied, table has another active reservation within 2 hours of the requested time");
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