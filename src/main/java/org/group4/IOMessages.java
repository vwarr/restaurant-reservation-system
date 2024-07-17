package org.group4;

/**
 * Responsible for formatting strings to be used in the output as required by each phase.
 * By doing this we can focus on the Business Logic in other files while still satisfying
 * our telemetry requirements
 */
public class IOMessages {

    private static final String WALK_IN = """
            %s %s - Walk-in party
            No credits rewarded and no misses added
            """;

    private static final String EARLY = """
            Customer %s (%s %s) has arrived early at %s
            Please come back during the reservation window
            No credits rewarded and no misses added
            """;

   private static final String ON_TIME = """
            Customer %s (%s %s) has arrived at %s
            %s %s - Successfully completed reservation
            Full credits rewarded
            """;

   private static final String LATE = """
            Customer %s (%s %s) has arrived late at %s
            %s %s - Missed reservation
            No credits rewarded and 1 miss added
            """;

    private static final String SEATS_AVAILABLE = """
            Seats were available, %s %s seated
            """;

    static String getSeatsAvailableMessage(Customer customer) {
        return SEATS_AVAILABLE.formatted(customer.getFirstName(), customer.getLastName());
    }

    static String getArrivalStatusMessage(ArrivalStatus status, Customer customer, Restaurant restaurant) {
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
