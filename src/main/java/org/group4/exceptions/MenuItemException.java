package org.group4.exceptions;

/**
 * When you are dealing with a nonexistent item, handle that in the input loop
 * These are just logical exceptions that might get thrown once input goes down the drain
 */
public class MenuItemException {

    /**
     * When the item has already been added to the restaurant
     */
    public static class AlreadyAdded extends Exception {
        public AlreadyAdded() {
            super("ERROR: item has already been added to this restaurant, try again");
        }
    }

    /**
     * When you try to calculate popularity on a menu item
     */
    public static class NeverAdded extends Exception {
        public NeverAdded() {
            super("ERROR: item was never added to a restaurant");
        }
    }

}
