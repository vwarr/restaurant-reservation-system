package org.group4.exceptions;

/**
 * When you are dealing with a nonexistent item, handle that in the input loop
 * These are just logical exceptions that might get thrown once input goes down the drain
 */
public class OrderFoodException {

    /**
     * When the item is not in the restaurant
     */
    public static class NotInRestaurant extends Exception {
        public NotInRestaurant() {
            super("ERROR: item is not in the restaurant, try again");
        }
    }

    /**
     * When the customer has insufficient credits
     */
    public static class InsufficientCredits extends Exception {
        public InsufficientCredits() {
            super("ERROR: insufficient credits");
        }
    }

}
