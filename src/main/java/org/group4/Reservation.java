package org.group4;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Reservation {
    private final Customer customer;
    private LocalDateTime dateTime;
    private LocalDateTime endTime;
    private final int credits;
    private int partySize;
    private ReservationStatus status = ReservationStatus.PENDING;
    private final Map<MenuItem, Integer> orderItems = new HashMap<>();

    private int bill;
    private int lastOrderPrice;

    public static final int RESERVATION_DURATION = 2;
    public static final int WALK_IN_PARTY_SIZE = 4;

    public Reservation(Customer customer, int partySize, LocalDateTime dateTime, int credits) {
        this.customer = customer;
        this.partySize = partySize;
        this.dateTime = dateTime;
        this.endTime = dateTime.plusHours(2);
        this.credits = credits;
        this.bill = 0;
        this.lastOrderPrice = 0;
    }

    public int getLastOrderPrice() {
        return lastOrderPrice;
    }

    public void setLastOrderPrice(int lastOrderPrice) {
        this.lastOrderPrice = lastOrderPrice;
    }

    public void addOrderItem(MenuItem item, int quantity) {
        orderItems.put(item, orderItems.getOrDefault(item, 0) + quantity);
    }

    public void updateBill(int cost) {
        this.bill += cost;
    }

    public int getBill() {
        return bill;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        this.endTime = dateTime.plusHours(2);
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public int getCredits() {
        return credits;
    }

    public void setPartySize(int partySize) {
        this.partySize = partySize;
    }

    public int getPartySize() {
        return partySize;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Identifier getIdentifier() {
        return new Identifier(customer, dateTime);
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "customer=" + customer +
                ", dateTime=" + dateTime +
                ", endTime=" + endTime +
                ", credits=" + credits +
                ", partySize=" + partySize +
                '}';
    }

    public static class Identifier {
        private final Customer customer;
        private final LocalDateTime dateTime;

        public Identifier(Customer customer, LocalDateTime dateTime) {
            this.customer = customer;
            this.dateTime = dateTime;
        }

        public static Identifier of(Customer customer, LocalDateTime dateTime) {
            return new Identifier(customer, dateTime);
        }

        @Override
        public int hashCode() {
            return Objects.hash(customer, dateTime);
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Identifier that = (Identifier) o;
            return Objects.equals(customer, that.customer) && Objects.equals(dateTime, that.dateTime);
        }
    }
}
