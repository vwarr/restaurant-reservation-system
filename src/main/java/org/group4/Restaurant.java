package org.group4;

import org.group4.Exceptions.MenuItemException;
import org.group4.Exceptions.NoSpaceException;
import org.group4.Exceptions.ReservationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.group4.Reservation.WALK_IN_PARTY_SIZE;

public class Restaurant {
    private final String id;
    private final String name;
    private final Address address;
    private int rating = 0;
    private int numRatings = 0;
    private boolean top10;
    private final int seatingCapacity;
    private final Map<Reservation.Identifier, Reservation> reservations = new HashMap<>();
    private final Map<String, RestaurantMenuItem> restaurantMenuItems = new HashMap<>();
    private final List<String> tags = new ArrayList<>();
    private final Owner owner;

    public Restaurant(String uniqueId, String name, Address address, int rating, boolean top10, int seatingCapacity, Owner owner) {
        this.id = (uniqueId == null) ? UUID.randomUUID().toString() : uniqueId;
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.top10 = top10;
        this.owner = owner;
        this.seatingCapacity = seatingCapacity;
        owner.addOwnedRestaurant(this);
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

    public void addRating(int newRating) {
        this.rating = (this.rating * numRatings + newRating) / numRatings + 1;
        numRatings++;
    }

    public boolean isTop10() {
        return top10;
    }

    public void setTop10(boolean top10) {
        this.top10 = top10;
    }

    public Owner getOwner() {
        return owner;
    }

    public void addMenuItem(MenuItem menuItem, int price) throws MenuItemException.AlreadyAdded {
        RestaurantMenuItem item = new RestaurantMenuItem(this, menuItem, price);
        if (restaurantMenuItems.containsKey(menuItem.getName())) {
            throw new MenuItemException.AlreadyAdded();
        }
        restaurantMenuItems.put(menuItem.getName(), item);
    }

    public Map<String, RestaurantMenuItem> getRestaurantMenuItems() {
        return restaurantMenuItems;
    }

    public List<String> getTags() {
        return tags;
    }

    public void addTag(String tag) {
        tags.add(tag);
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

    /**
     * Overload for the customer arrival method primarily for use in tests as you don't need the string builder
     * @param customer the customer
     * @param reservationDate the date they reserved
     * @param arrivalTime the time of arrival
     * @param reservationTime the supposed time of reservation, null if walk in
     * @throws NoSpaceException if there are no seats for a late or walk in party
     */
    public void onCustomerArrival(Customer customer, LocalDate reservationDate, LocalTime arrivalTime,
                                           LocalTime reservationTime) throws NoSpaceException {
        ArrivalStatus status = getArrivalStatus(customer, reservationDate, arrivalTime, reservationTime);

        System.out.print(IOMessages.getArrivalStatusMessage(status, customer, this));

        if (status == ArrivalStatus.EARLY) {
            return;
        }

        if (status == ArrivalStatus.ON_TIME) {
            Reservation.Identifier identifier = Reservation.Identifier.of(customer, LocalDateTime.of(reservationDate, reservationTime));
            Reservation reservation = reservations.get(identifier);
            reservation.setStatus(ReservationStatus.SUCCESS);
            customer.incrementCredits(reservation.getCredits());
            return;
        }

        int partySize = WALK_IN_PARTY_SIZE; // for walk-ins initially

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
                System.out.print(IOMessages.getResetMessage(customer));
            }
            partySize = reservation.getPartySize();
        }

        // Attempt to seat walk in customer, whether they are late or not
        try {
            makeReservation(customer, partySize, LocalDateTime.of(reservationDate, arrivalTime), 0);
            System.out.print(IOMessages.getSeatsAvailableMessage(customer));
        } catch (ReservationException.FullyBooked | ReservationException.Conflict e) {
            // We catch both exceptions because in the current state of the tests, there is
            // no differentiation made between no seats for walk-ins and late
            throw new NoSpaceException();
        }
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

    public int checkSpace(LocalDateTime arrivalDateTime) {
        int usedSeats = 0;
        for (Reservation r : reservations.values()) {
//            LocalDateTime start1 = r.getDateTime();
//            LocalDateTime end1 = r.getEndTime();
//            LocalDateTime start2 = arrivalDateTime;
//            LocalDateTime end2 = arrivalDateTime.plusHours(RESERVATION_DURATION);
//
//            boolean overlaps = !(end1.isBefore(start2) || start1.isAfter(end2));
            boolean overlaps = r.getDateTime().isBefore(arrivalDateTime)
                    && r.getEndTime().isAfter(arrivalDateTime);
            if (overlaps || r.getDateTime().isEqual(arrivalDateTime)) {
                usedSeats += r.getPartySize();
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
        private final String id;
        private String name = "Unnamed Restaurant";
        private Owner owner = new Owner.Builder("SomeOwner").build();
        private Address address = new Address("123", "Unnamed Street", 12345);
        private int rating = 0;
        private boolean top10 = false;
        private int seatingCapacity = 1000; // we are assuming if you don't use it, you have a lot of space

        public Builder(String id) {
            this.id = id;
        }

        public Builder owner(Owner owner) {
            this.owner = owner;
            return this;
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
            return new Restaurant(id, name, address, rating, top10, seatingCapacity, owner);
        }
    }
}
