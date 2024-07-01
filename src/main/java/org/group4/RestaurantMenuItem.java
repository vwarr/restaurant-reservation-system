package org.group4;

public class RestaurantMenuItem {
    private final MenuItem parentItem;
    private final int price;

    public RestaurantMenuItem(Restaurant restaurant, MenuItem parentItem, int price) {
        this.parentItem = parentItem;
        this.price = price;
        parentItem.addOfferingRestaurant(restaurant, price);
    }

    public MenuItem getParentItem() {
        return parentItem;
    }

    public int getPrice() {
        return price;
    }
}
