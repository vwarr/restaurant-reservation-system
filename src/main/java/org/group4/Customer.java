package org.group4;

class Customer {
    private final String firstName;
    private final String lastName;
    private int credits;
    private int missedReservations;
    private int funds;

    public Customer(String firstName, string lastName, int credits, int missedReservations, int funds) {
        this.firstName = firstName;
        // NOTE: We must consider the case when this is optional, like with Beyonce
        this.lastName = lastName;
        this.credits = credits;
        this.missedReservations = missedReservations;
        this.funds = funds;
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

    public int getMissedReservations() {
        return missedReservations;
    }

    public void setMissedReservations(int missedReservations) {
        this.missedReservations = missedReservations;
    }

    public int getFunds() {
        return funds;
    }

    public void setFunds(int funds) {
        this.funds = funds;
    }

}
