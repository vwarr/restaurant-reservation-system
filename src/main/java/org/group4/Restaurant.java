package org.group4;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.group4.Reservation.RESERVATION_DURATION;

public class Restaurant {
    private final String id;
    private final String name;
    private final Address address;
    private int rating;
    private boolean top10;
    private final int seatingCapacity;
    private final Map<Reservation.Identifier, Reservation> reservations = new HashMap<>();

    public Restaurant(String uniqueId, String name, Address address, int rating, boolean top10, int seatingCapacity) {
        this.id = (uniqueId == null) ? UUID.randomUUID().toString() : uniqueId;
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.top10 = top10;
        this.seatingCapacity = seatingCapacity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public Address getAddress() {
        return address;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isTop10() {
        return top10;
    }

    public void setTop10(boolean top10) {
        this.top10 = top10;
    }

    public Reservation makeReservation(Customer customer, int partySize, LocalDateTime reservationDateTime, int credits)
            throws ReservationException.FullyBooked, ReservationException.Conflict {
        if (partySize < 1 || reservationDateTime == null || customer == null) {
            throw new IllegalArgumentException("Invalid parameters");
        }

        if (checkSpace(reservationDateTime) < partySize) {
            throw new ReservationException.FullyBooked();
        }
        if (customer.isReservationConflict(reservationDateTime)) {
            throw new ReservationException.Conflict();
        }
        Reservation reservation = new Reservation(customer, partySize, reservationDateTime, credits);
        reservations.put(reservation.getIdentifier(), reservation);
        customer.addReservation(reservation);
        return reservation;
    }

    public StringBuilder onCustomerArrival(Customer customer, LocalDate reservationDate, LocalTime arrivalTime, LocalTime reservationTime) {
        ArrivalStatus status = getArrivalStatus(customer, reservationDate, arrivalTime, reservationTime);
        StringBuilder builder = new StringBuilder(IOMessages.getArrivalStatusMessage(status, customer, this));

        if (status == ArrivalStatus.EARLY) {
            return builder;
        }

        if (status == ArrivalStatus.ON_TIME) {
            Reservation.Identifier identifier = Reservation.Identifier.of(customer, LocalDateTime.of(reservationDate, reservationTime));
            Reservation reservation = reservations.get(identifier);
            reservation.setStatus(ReservationStatus.SUCCESS);
            customer.incrementCredits(reservation.getCredits());
            return builder;
        }

        int partySize = 1; // for walk-ins initially

        // Late reservations require some preprocessing to work well
        if (status == ArrivalStatus.LATE) {
            Reservation.Identifier identifier = Reservation.Identifier.of(customer, LocalDateTime.of(reservationDate, reservationTime));
            Reservation reservation = reservations.get(identifier);
            reservation.setStatus(ReservationStatus.MISSED);
            reservations.remove(reservation.getIdentifier());

            customer.incrementMissedReservations();
            // If customer misses three reservations reset missed reservation counter and reset credits.
            if (customer.getMissedReservations() == 3) {
                customer.setMissedReservations(0);
                customer.setCredits(0);
                builder.append(IOMessages.getResetMessage(customer));
            }
            partySize = reservation.getPartySize();
        }

        // Attempt to seat walk in customer, whether they are late or not
        try {
            makeReservation(customer, partySize, LocalDateTime.of(reservationDate, arrivalTime), 0);
            builder.append(IOMessages.getSeatsAvailableMessage(customer));
        } catch (ReservationException.FullyBooked | ReservationException.Conflict e) {
            // We catch both exceptions because in the current state of the tests, there is
            // no differentiation made between no seats for walk-ins and late
            builder.append(IOMessages.SEATS_UNAVAILABLE);
        }

        return builder;
    }

    public ArrivalStatus getArrivalStatus(Customer customer, LocalDate reservationDate, LocalTime arrivalTime,
                                          LocalTime reservationTime) {
        if (reservationTime == null) {
            return ArrivalStatus.WALK_IN;
        }
        // Handle case when customer doesn't have a reservation despite saying they do?
        LocalDateTime reservationDateTime = LocalDateTime.of(reservationDate, reservationTime);
        Reservation reservation = reservations.get(new Reservation.Identifier(customer, reservationDateTime));
        if (arrivalTime.isAfter(reservationTime.plusMinutes(15))) {
            return ArrivalStatus.LATE;
        } else if (arrivalTime.isBefore(reservationTime.minusMinutes(30))) {
            return ArrivalStatus.EARLY;
        }
        return ArrivalStatus.ON_TIME;
    }

    public int checkSpace(LocalDateTime arrivalTime) {
        int usedSeats = 0;
        for (Reservation reservation : reservations.values()) {
            if (reservation.getDateTime().isEqual(arrivalTime) ||
                    (reservation.getDateTime().isBefore(arrivalTime) &&
                            reservation.getEndTime().isAfter(arrivalTime))) {
                usedSeats += reservation.getPartySize();
            }
        }
        return seatingCapacity - usedSeats;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", rating=" + rating +
                ", top10=" + top10 +
                ", seatingCapacity=" + seatingCapacity +
                '}';
    }

    public static class Builder {
        private String id;
        private String name = "Unnamed Restaurant";
        private Address address = new Address("123", "Unnamed Street", 12345);
        private int rating = 0;
        private boolean top10 = false;
        private int seatingCapacity = 1000; // we are assuming if you don't use it, you have a lot of space

        public Builder(String id) {
            this.id = id;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder address(String street, String city, int zip) {
            this.address = new Address(street, city, zip);
            return this;
        }

        public Builder rating(int rating) {
            this.rating = rating;
            return this;
        }

        public Builder seatingCapacity(int seatingCapacity) {
            this.seatingCapacity = seatingCapacity;
            return this;
        }

        public Builder top10(boolean top10) {
            this.top10 = top10;
            return this;
        }

        public Restaurant build() {
            return new Restaurant(id, name, address, rating, top10, seatingCapacity);
        }
    }
}
