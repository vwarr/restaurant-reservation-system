package org.group4;

import java.time.LocalDateTime;

class Reservation {
    private final LocalDateTime dateTime;
    private final int credits;
    private final int partySize;
    private ReservationStatus status;

    public Reservation(LocalDateTime dateTime, int credits, int partySize, ReservationStatus status) {
        this.dateTime = dateTime;
        this.credits = credits;
        this.partySize = partySize;
        this.status = status;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getCredits() {
        return credits;
    }

    public int getPartySize() {
        return partySize;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}
