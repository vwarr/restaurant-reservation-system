package org.group4;

public class IOMessages {

//                                    System.out.printf("%s %s - Walk-in party", customer.getFirstName(),
//                                            customer.getLastName());
//                                System.out.print("\nNo reservation, however open table so request validated");
//                                System.out.print("\nNo credits rewarded and no misses added");

    static final String WALK_IN = """
            %s %s - Walk-in party
            No credits rewarded and no misses added
            """;

    static final String EARLY = """
            Customer %s (%s %s) has arrived early at %s
            Please come back during the reservation window
            No credits rewarded and no misses added
            """;

   static final String ON_TIME = """
            Customer %s (%s %s) has arrived on time at %s
            %s %s - Successfully completed reservation
            Full credits rewarded
            """;

    static final String LATE = """
            Customer %s (%s %s) has arrived late at %s
            %s %s - Missed reservation
            No credits rewarded and 1 miss added
            """;

    static final String SEATS_AVAILABLE = """
            Seats were available, %s %s seated
            """;

    static String getSeatsAvailableMessage(Customer customer) {
        return SEATS_AVAILABLE.formatted(customer.getFirstName(), customer.getLastName());
    }

    static final String SEATS_UNAVAILABLE = """
            Seats not available - Request denied
            """;

    public static String getArrivalStatusMessage(ArrivalStatus status, Customer customer, Restaurant restaurant) {
        switch (status) {
            case WALK_IN -> {
                return WALK_IN.formatted(customer.getFirstName(), customer.getLastName());
            }
            case EARLY -> {
                return EARLY.formatted(customer.getId(), customer.getFirstName(), customer.getLastName(), restaurant.getName());
            }
            case ON_TIME -> {
                return ON_TIME.formatted(customer.getId(), customer.getFirstName(), customer.getLastName(), restaurant.getName(),
                        customer.getFirstName(), customer.getLastName());
            }
            case LATE -> {
                return LATE.formatted(customer.getId(), customer.getFirstName(), customer.getLastName(), restaurant.getName(),
                        customer.getFirstName(), customer.getLastName());
            }
            default -> {
                return "";
            }
        }
    }

    static final String CREDITS_RESET = """
            Misses: 3
            %s %s - 3 Misses reached, both misses and credits will reset back to 0
            """;

    public static String getResetMessage(Customer customer) {
        return CREDITS_RESET.formatted(customer.getFirstName(), customer.getLastName());
    }

    static final String CUSTOMER_INFO = """
            Credits: %d
            Misses: %d
            """;

    public static String getCustomerInfoMessage(Customer customer) {
        return CUSTOMER_INFO.formatted(customer.getCredits(), customer.getMissedReservations());
    }
}
