package org.group4;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.group4.Reservation.RESERVATION_DURATION;

public class Customer {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final Address address;
    private double funds;
    private int credits;
    private int missedReservations;
    private final List<Reservation> reservations = new ArrayList<>();

    public Customer(String uniqueId, String firstName, String lastName, Address address, double funds) {
        this.id = (uniqueId == null) ? UUID.randomUUID().toString() : uniqueId;
        this.firstName = firstName;
        // NOTE: We must consider the case when this is optional, like with Beyonce
        this.lastName = lastName;
        this.address = address;
        this.funds = funds;
        this.missedReservations = 0;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void incrementCredits(int credits) {
        this.credits += credits;
    }

    public int getMissedReservations() {
        return missedReservations;
    }

    public void setMissedReservations(int missedReservations) {
        this.missedReservations = missedReservations;
    }

    public void incrementMissedReservations() {
        this.missedReservations++;
    }

    public double getFunds() {
        return funds;
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    public String getId() {
        return id;
    }

    public Address getAddress() {
        return address;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public boolean isReservationConflict(LocalDateTime startTime) {
        LocalDateTime endTime = startTime.plusHours(RESERVATION_DURATION);
        for (Reservation r : reservations) {
            if (r.getStatus() != ReservationStatus.MISSED && r.getDateTime().isBefore(endTime) && r.getEndTime().isAfter(startTime)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(getId(), customer.getId());
    }

    public static class Builder {
        private String id;
        private String firstName;
        private String lastName;
        private Address address;
        private double funds;
        private int credits;
        private int missedReservations;

        public Builder(String id) {
            this.id = id;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder address(String city, String state, int zip) {
            this.address = new Address(city, state, zip);
            return this;
        }

        public Builder funds(double funds) {
            this.funds = funds;
            return this;
        }

        public Builder credits(int credits) {
            this.credits = credits;
            return this;
        }

        public Builder missedReservations(int missedReservations) {
            this.missedReservations = missedReservations;
            return this;
        }

        public Customer build() {
            return new Customer(id, firstName, lastName, address, funds);
        }
    }
}
